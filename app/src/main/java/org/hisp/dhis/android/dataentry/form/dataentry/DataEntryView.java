package org.hisp.dhis.android.dataentry.form.dataentry;

import org.hisp.dhis.android.dataentry.commons.tuples.Pair;
import org.hisp.dhis.android.dataentry.commons.ui.View;
import org.hisp.dhis.android.dataentry.form.DataEntryViewArguments;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormViewModel;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

interface DataEntryView extends View {

    Consumer<List<Pair<FormViewModel, DisposableObserver<Pair<String, String>>>>> renderFormViewModels();

    DataEntryViewArguments sectionViewModel();
}