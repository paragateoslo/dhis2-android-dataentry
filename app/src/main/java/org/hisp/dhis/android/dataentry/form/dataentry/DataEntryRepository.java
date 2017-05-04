package org.hisp.dhis.android.dataentry.form.dataentry;

import android.support.annotation.NonNull;

import org.hisp.dhis.android.dataentry.commons.tuples.Pair;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormViewModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.observers.DisposableObserver;

public interface DataEntryRepository {

    @NonNull
    Flowable<List<Pair<FormViewModel, DisposableObserver<Pair<String, String>>>>> formItems(
            DataEntryViewArguments dataEntryViewArguments);
}
