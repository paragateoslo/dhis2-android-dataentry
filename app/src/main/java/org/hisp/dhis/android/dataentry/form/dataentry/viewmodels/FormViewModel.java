package org.hisp.dhis.android.dataentry.form.dataentry.viewmodels;

import android.support.annotation.NonNull;

public abstract class FormViewModel {

    @NonNull
    public abstract String uid();

    @NonNull
    public abstract String label();

}