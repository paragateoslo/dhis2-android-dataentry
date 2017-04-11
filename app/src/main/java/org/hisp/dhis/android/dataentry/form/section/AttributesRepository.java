package org.hisp.dhis.android.dataentry.form.section;

import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;

import org.hisp.dhis.android.core.user.UserModel;
import org.hisp.dhis.android.dataentry.commons.tuples.Pair;
import org.hisp.dhis.android.dataentry.form.SectionViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.FormItemViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.FormItemViewModelFactory;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.observers.DisposableObserver;

import static hu.akarnokd.rxjava.interop.RxJavaInterop.toV2Flowable;

public class AttributesRepository implements SectionRepository {

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
    private final EditTextHintCache editTextHintCache;
    private SectionViewModel sectionViewModel;

    public AttributesRepository(EditTextHintCache editTextHintCache, BriteDatabase briteDatabase) {
        this.editTextHintCache = editTextHintCache;
        this.briteDatabase = briteDatabase;

        this.userName = fetchUserName(briteDatabase);
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