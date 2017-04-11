package org.hisp.dhis.android.dataentry.form.section;

import android.support.annotation.NonNull;

import org.hisp.dhis.android.dataentry.commons.tuples.Pair;
import org.hisp.dhis.android.dataentry.form.SectionViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.FormItemViewModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.observers.DisposableObserver;

public interface SectionRepository {

    @NonNull
    Flowable<List<Pair<FormItemViewModel, DisposableObserver<Pair<String, String>>>>> formItems(
            SectionViewModel sectionViewModel);
}
