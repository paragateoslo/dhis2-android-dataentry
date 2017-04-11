package org.hisp.dhis.android.dataentry.form.section.viewmodels;

import android.support.annotation.NonNull;

public abstract class FormItemViewModel {

    @NonNull
    public abstract String uid();

    @NonNull
    public abstract String label();

    @NonNull
    public abstract CharSequence value();

    public enum Type {
        EDITTEXT, CHECKBOX, COORDINATES, RADIO_BUTTONS, DATE, FILTER, TEXT
    }
}