package org.hisp.dhis.android.dataentry.form.section;

import org.hisp.dhis.android.dataentry.commons.tuples.Pair;
import org.hisp.dhis.android.dataentry.commons.ui.View;
import org.hisp.dhis.android.dataentry.form.SectionViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.FormItemViewModel;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

interface SectionView extends View {

    Consumer<List<Pair<FormItemViewModel, DisposableObserver<Pair<String, String>>>>> renderFormViewModels();

    SectionViewModel sectionViewModel();
}