package org.hisp.dhis.android.dataentry.form.section;

import android.support.annotation.NonNull;

import org.hisp.dhis.android.dataentry.commons.schedulers.SchedulerProvider;
import org.hisp.dhis.android.dataentry.commons.ui.View;
import org.hisp.dhis.android.dataentry.form.FormView;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static org.hisp.dhis.android.dataentry.commons.utils.Preconditions.isNull;

class SectionPresenterImpl implements SectionPresenter {

    private final SchedulerProvider schedulerProvider;
    private final SectionRepository sectionRepository;
    private final CompositeDisposable compositeDisposable;

    SectionPresenterImpl(SchedulerProvider schedulerProvider, SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(@NonNull View view) {
        isNull(view, "HomeView must not be null");

        if (view instanceof FormView) {
            SectionView sectionView = (SectionView) view;

            compositeDisposable.add(sectionRepository.formItems(sectionView.sectionViewModel())
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                            sectionView.renderFormViewModels(),
                            throwable -> Timber.e(throwable)));
        }
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
    }
}
