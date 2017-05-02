package org.hisp.dhis.android.dataentry.form.dataentry;

import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;

import org.hisp.dhis.android.core.user.UserModel;
import org.hisp.dhis.android.dataentry.commons.tuples.Pair;
import org.hisp.dhis.android.dataentry.form.DataEntryViewArguments;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormViewModel;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormViewModelFactory;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.observers.DisposableObserver;

import static hu.akarnokd.rxjava.interop.RxJavaInterop.toV2Flowable;

public class AttributeRepository implements DataEntryRepository {

    private static final String SELECT_FORM_ITEMS = "SELECT " +
            "  TrackedEntityAttributeValue.trackedEntityAttribute, " +
            "  value, " +
            "  TrackedEntityAttribute.displayName, " +
            "  valueType, " +
            "  mandatory, " +
            "  optionSet " +
            "FROM TrackedEntityAttributeValue " +
            "  JOIN TrackedEntityAttribute ON TrackedEntityAttributeValue.trackedEntityAttribute = TrackedEntityAttribute.uid " +
            "  JOIN ProgramTrackedEntityAttribute " +
            "    ON TrackedEntityAttributeValue.trackedEntityAttribute = ProgramTrackedEntityAttribute.trackedEntityAttribute " +
            "WHERE trackedEntityInstance = ?";

    private static Iterable<String> tables = Arrays.asList("ProgramTrackedEntityAttribute",
            "TrackedEntityAttributeValue");

    private static final String UPDATE_DATA_VALUE = "UPDATE TrackedEntityAttributeValue " +
            "SET value = ? " +
            "WHERE trackedEntityAttribute = 'trackedEntityAttribute' " +
            "AND trackedEntityInstance = 'trackedEntityInstance'";

    private static final String SELECT_USER_NAME =
            "SELECT " + UserModel.Columns.DISPLAY_NAME +
                    " FROM " + UserModel.TABLE + " LIMIT 1";

    private final BriteDatabase briteDatabase;
    private final String userName;
    private final FormViewModelFactory formViewModelFactory;
    private DataEntryViewArguments dataEntryViewArguments;

    public AttributeRepository(BriteDatabase briteDatabase, FormViewModelFactory formViewModelFactory) {
        this.briteDatabase = briteDatabase;
        this.formViewModelFactory = formViewModelFactory;

        this.userName = fetchUserName(briteDatabase);
    }

    @NonNull
    @Override
    public Flowable<List<Pair<FormViewModel, DisposableObserver<Pair<String, String>>>>> formItems(
            DataEntryViewArguments dataEntryViewArguments) {
        this.dataEntryViewArguments = dataEntryViewArguments;

        return toV2Flowable(briteDatabase
                .createQuery(tables, SELECT_FORM_ITEMS, dataEntryViewArguments.uid())
                .mapToList(cursor -> Pair.create(formViewModelFactory.fromCursor(cursor),
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
        String attributeUid = uidValuePair.val0();
        String value = uidValuePair.val1();
    }

    private String fetchUserName(BriteDatabase briteDatabase) {
        return briteDatabase.createQuery(UserModel.TABLE, SELECT_USER_NAME).mapToOne(cursor -> {
            cursor.moveToFirst();
            return cursor.getString(0);
        }).toBlocking().first();
    }
}