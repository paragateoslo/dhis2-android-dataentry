package org.hisp.dhis.android.dataentry.form.dataentry;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.hisp.dhis.android.core.common.ValueType;
import org.hisp.dhis.android.core.dataelement.DataElementModel;
import org.hisp.dhis.android.core.event.EventModel;
import org.hisp.dhis.android.core.organisationunit.OrganisationUnitModel;
import org.hisp.dhis.android.core.program.ProgramModel;
import org.hisp.dhis.android.core.program.ProgramStageModel;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityDataValueModel;
import org.hisp.dhis.android.dataentry.commons.tuples.Pair;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormViewModel;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormViewModelFactory;
import org.hisp.dhis.android.dataentry.rules.DatabaseRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(AndroidJUnit4.class)
public class DataElementRepositoryIntegrationTests {

    @Rule
    public DatabaseRule databaseRule = new DatabaseRule(Schedulers.trampoline());

    DataEntryRepository dataElementRepository; // the class we are testing

    @Mock
    private FormViewModel formViewModel;

    @Mock
    private FormViewModelFactory formViewModelFactory;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        SQLiteDatabase database = databaseRule.database();

        ContentValues dataElement = new ContentValues();
        dataElement.put(DataElementModel.Columns.UID, "dataElementUid");
        dataElement.put(DataElementModel.Columns.DISPLAY_NAME, "dataElementDisplayName");
        dataElement.put(DataElementModel.Columns.VALUE_TYPE, ValueType.TEXT.name());
        database.insert(DataElementModel.TABLE, null, dataElement);

        ContentValues program = new ContentValues();
        program.put(ProgramModel.Columns.UID, "programUid");
        database.insert(ProgramModel.TABLE, null, program);

        ContentValues programStage = new ContentValues();
        programStage.put(ProgramStageModel.Columns.UID, "programStageUid");
        programStage.put(ProgramStageModel.Columns.PROGRAM, "programUid");
        database.insert(ProgramStageModel.TABLE, null, programStage);

        ContentValues orgUnit = new ContentValues();
        orgUnit.put(OrganisationUnitModel.Columns.UID, "orgUnitUid");
        database.insert(OrganisationUnitModel.TABLE, null, orgUnit);

        ContentValues event = new ContentValues();
        event.put(EventModel.Columns.UID, "eventUid");
        event.put(EventModel.Columns.PROGRAM, "programUid");
        event.put(EventModel.Columns.PROGRAM_STAGE, "programStageUid");
        event.put(EventModel.Columns.ORGANISATION_UNIT, "orgUnitUid");
        database.insert(EventModel.TABLE, null, event);

        ContentValues dataValue = new ContentValues();
        dataValue.put(TrackedEntityDataValueModel.Columns.VALUE, "dataValueValue");
        dataValue.put(TrackedEntityDataValueModel.Columns.DATA_ELEMENT, "dataElementUid");
        dataValue.put(TrackedEntityDataValueModel.Columns.EVENT, "eventUid");
        database.insert(TrackedEntityDataValueModel.TABLE, null, dataValue);

        dataElementRepository = new DataElementRepository(databaseRule.briteDatabase(), formViewModelFactory);

    }

    @Test
    public void formItemsShouldPropagateCorrectResults() throws InterruptedException {

        DataEntryViewArguments dataEntryViewArguments = DataEntryViewArguments.create("eventUid", "sectionUid", "SECTION 1", DataEntryViewArguments.Type.PROGRAM_STAGE);

        TestSubscriber<List<Pair<FormViewModel, DisposableObserver<Pair<String, String>>>>> testObserver =
                dataElementRepository.formItems(dataEntryViewArguments).test();

        testObserver.assertValueCount(1);
        testObserver.assertNoErrors();
        testObserver.assertNotComplete();

        List<Pair<FormViewModel, DisposableObserver<Pair<String, String>>>> formViewModels = testObserver.values().get(0);

        assertThat(formViewModels.size()).isEqualTo(1);
        assertThat(formViewModels.get(0).val0()).isEqualTo(formViewModel);

    }

}
