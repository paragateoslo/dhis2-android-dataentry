package org.hisp.dhis.android.dataentry.form;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;

public interface FormRepository {

    @NonNull
    Flowable<String> title(String programUid, String programStageUid);

    @NonNull
    Flowable<List<DataEntryViewArguments>> sections(String event, String programStageUid);
}
