package org.hisp.dhis.android.dataentry.form;

import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;

import org.hisp.dhis.android.core.program.ProgramModel;
import org.hisp.dhis.android.core.program.ProgramStageModel;
import org.hisp.dhis.android.core.program.ProgramStageSectionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Flowable;

import static hu.akarnokd.rxjava.interop.RxJavaInterop.toV2Flowable;

public class FormRepositoryImpl implements FormRepository {

    private static final String[] TITLE_TABLE_NAMES = new String[]{ProgramModel.TABLE, ProgramStageModel.TABLE};
    private static final Set<String> TITLE_TABLE_SET = new HashSet<>(Arrays.asList(TITLE_TABLE_NAMES));

    private static final String SELECT_TITLE = "SELECT\n" +
            "  Program.displayName,\n" +
            "  ProgramStage.displayName\n" +
            "FROM Program\n" +
            "  JOIN ProgramStage ON Program.uid = ProgramStage.program\n" +
            "WHERE Program.uid = ? AND ProgramStage.uid = ?";

    private static final String SELECT_SECTIONS = "SELECT\n" +
            " uid, displayName FROM ProgramStageSection WHERE programStage = ?";

    private final BriteDatabase briteDatabase;

    public FormRepositoryImpl(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @NonNull
    @Override
    public Flowable<String> title(String programUid, String programStageUid) {
        return toV2Flowable(briteDatabase
                .createQuery(TITLE_TABLE_SET, SELECT_TITLE, programUid, programStageUid)
                .mapToOne(cursor -> cursor.getString(0) + " - " + cursor.getString(1)));
    }

    @NonNull
    @Override
    public Flowable<List<SectionViewModel>> sections(String event, String programStageUid) {

        QueryObservable sectionQuery = briteDatabase
                .createQuery(ProgramStageSectionModel.TABLE, SELECT_SECTIONS, programStageUid);

        // TODO: make this 100% reactive

        if (sectionQuery.count().toBlocking().first() != 0) {
            return toV2Flowable(sectionQuery.
                    mapToList(cursor -> SectionViewModel.create(event, cursor.getString(0), cursor.getString(1),
                            SectionViewModel.Type.SECTION)));
        } else {
            // The ProgramStage has no sections. Return a single SectionViewModel of type PROGRAM_STAGE
            // Label is empty because the FormFragment.TabLayout is hidden
            List<SectionViewModel> singleSectionCollection = new ArrayList<>();
            singleSectionCollection.add(SectionViewModel.create(event, programStageUid, "",
                    SectionViewModel.Type.PROGRAM_STAGE));
            return Flowable.just(singleSectionCollection);
        }
    }
}