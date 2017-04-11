package org.hisp.dhis.android.dataentry.form.section;

import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;

import org.hisp.dhis.android.core.trackedentity.TrackedEntityDataValueModel;
import org.hisp.dhis.android.core.user.UserModel;
import org.hisp.dhis.android.dataentry.commons.tuples.Pair;
import org.hisp.dhis.android.dataentry.form.SectionViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.FormItemViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.FormItemViewModelFactory;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.observers.DisposableObserver;

import static hu.akarnokd.rxjava.interop.RxJavaInterop.toV2Flowable;
import static org.hisp.dhis.android.core.trackedentity.TrackedEntityDataValueModel.Columns.EVENT;
import static org.hisp.dhis.android.core.utils.StoreUtils.sqLiteBind;

public class DataElementsRepository implements SectionRepository {

    private static final String SELECT_FORM_ITEMS = "SELECT" +
            "  TrackedEntityDataValue.dataElement," +
            "  DataElement.displayName," +
            "  value," +
            "  valueType," +
            "  compulsory," +
            "  optionSet " +
            "FROM TrackedEntityDataValue" +
            "  JOIN DataElement ON TrackedEntityDataValue.dataElement = DataElement.uid" +
            "  JOIN ProgramStageDataElement ON ProgramStageDataElement.dataElement = DataElement.uid " +
            "WHERE event = ?";

    private static Iterable<String> tables = Arrays.asList("TrackedEntityDataValue", "DataElement",
            "ProgramStageDataElement");

    private static final String UPDATE_DATA_VALUE = "UPDATE " +
            TrackedEntityDataValueModel.TABLE + " (" +
            EVENT + ", " +
            TrackedEntityDataValueModel.Columns.LAST_UPDATED + ", " +
            TrackedEntityDataValueModel.Columns.DATA_ELEMENT + ", " +
            TrackedEntityDataValueModel.Columns.STORED_BY + ", " +
            TrackedEntityDataValueModel.Columns.VALUE +
            ") " + "VALUES (?,?,?,?,?)";

    private static final String SELECT_USER_NAME =
            "SELECT " + UserModel.Columns.DISPLAY_NAME +
                    " FROM " + UserModel.TABLE + " LIMIT 1";

    private final SQLiteStatement updateStatement;

    private final BriteDatabase briteDatabase;
    private final String userName;
    private final EditTextHintCache editTextHintCache;
    private SectionViewModel sectionViewModel;

    public DataElementsRepository(EditTextHintCache editTextHintCache, BriteDatabase briteDatabase) {
        this.editTextHintCache = editTextHintCache;
        this.briteDatabase = briteDatabase;

        this.userName = fetchUserName(briteDatabase);

        this.updateStatement = briteDatabase.getWritableDatabase().compileStatement(UPDATE_DATA_VALUE);
    }

    @NonNull
    @Override
    public Flowable<List<Pair<FormItemViewModel, DisposableObserver<Pair<String, String>>>>> formItems(
            SectionViewModel sectionViewModel) {
        this.sectionViewModel = sectionViewModel;

        return toV2Flowable(briteDatabase
                .createQuery(tables, SELECT_FORM_ITEMS, sectionViewModel.uid())
                .mapToList(cursor -> Pair.create(FormItemViewModelFactory.fromCursor(cursor, editTextHintCache),
                        createValueChangeObserver())));
    }

    @NonNull
    private DisposableObserver<Pair<String, String>> createValueChangeObserver() {

        return new DisposableObserver<Pair<String, String>>() {
            @Override
            public void onNext(Pair<String, String> uidValuePair) {
                storeDataValue(uidValuePair);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void storeDataValue(Pair<String, String> uidValuePair) {
        String dataElementUid = uidValuePair.val0();
        String value = uidValuePair.val1();
        sqLiteBind(updateStatement, 1, sectionViewModel.uid());
        sqLiteBind(updateStatement, 2, Calendar.getInstance().getTime());
        sqLiteBind(updateStatement, 3, dataElementUid);
        sqLiteBind(updateStatement, 4, userName);
        sqLiteBind(updateStatement, 5, value);
        briteDatabase.executeUpdateDelete(TrackedEntityDataValueModel.TABLE, updateStatement);
        updateStatement.clearBindings();
    }

    private String fetchUserName(BriteDatabase briteDatabase) {
        return briteDatabase.createQuery(UserModel.TABLE, SELECT_USER_NAME).mapToOne(cursor -> {
            cursor.moveToFirst();
            return cursor.getString(0);
        }).toBlocking().first();
    }
}