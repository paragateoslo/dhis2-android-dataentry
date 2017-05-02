package org.hisp.dhis.android.dataentry.form.dataentry;

import android.support.annotation.NonNull;

import org.hisp.dhis.android.dataentry.commons.schedulers.SchedulerProvider;
import org.hisp.dhis.android.dataentry.commons.ui.View;
import org.hisp.dhis.android.dataentry.form.FormView;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static org.hisp.dhis.android.dataentry.commons.utils.Preconditions.isNull;

class DataEntryPresenterImpl implements DataEntryPresenter {

    private final SchedulerProvider schedulerProvider;
    private final DataEntryRepository dataEntryRepository;
    private final CompositeDisposable compositeDisposable;

    DataEntryPresenterImpl(SchedulerProvider schedulerProvider, DataEntryRepository dataEntryRepository) {
        this.dataEntryRepository = dataEntryRepository;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(@NonNull View view) {
        isNull(view, "HomeView must not be null");

        if (view instanceof FormView) {
            DataEntryView dataEntryView = (DataEntryView) view;

            compositeDisposable.add(dataEntryRepository.formItems(dataEntryView.sectionViewModel())
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                            dataEntryView.renderFormViewModels(),
                            Timber::e));
        }
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
    }
}
