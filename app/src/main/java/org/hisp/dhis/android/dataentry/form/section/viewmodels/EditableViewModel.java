package org.hisp.dhis.android.dataentry.form.section.viewmodels;

import android.support.annotation.NonNull;

import org.hisp.dhis.android.dataentry.form.section.viewmodels.FormItemViewModel;

public abstract class EditableViewModel extends FormItemViewModel {

    @NonNull
    public abstract Boolean mandatory();
}