package org.hisp.dhis.android.dataentry.form;

import android.support.annotation.NonNull;

import org.hisp.dhis.android.dataentry.commons.schedulers.SchedulerProvider;
import org.hisp.dhis.android.dataentry.commons.ui.View;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static org.hisp.dhis.android.dataentry.commons.utils.Preconditions.isNull;

class FormPresenterImpl implements FormPresenter {

    private final SchedulerProvider schedulerProvider;
    private final FormRepository formRepository;
    private final CompositeDisposable compositeDisposable;

    FormPresenterImpl(SchedulerProvider schedulerProvider, FormRepository formRepository) {
        this.formRepository = formRepository;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(@NonNull View view) {
        isNull(view, "HomeView must not be null");

        if (view instanceof FormView) {
            FormView formView = (FormView) view;

            compositeDisposable.add(formRepository.title(formView.program(), formView.programStage())
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                            formView.renderTitle(),
                            throwable -> Timber.e(throwable)));

            compositeDisposable.add(formRepository.sections(formView.event(), formView.programStage())
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                            formView.renderSectionViewModels(),
                            throwable -> Timber.e(throwable)));
        }
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
    }
}
