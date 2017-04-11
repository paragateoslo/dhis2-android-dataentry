package org.hisp.dhis.android.dataentry.form;

import org.hisp.dhis.android.dataentry.commons.ui.View;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface FormView extends View {

    Consumer<List<SectionViewModel>> renderSectionViewModels();

    Consumer<String> renderTitle();

    String event();

    String program();

    String programStage();
}