package org.hisp.dhis.android.dataentry.form;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FormViewModel {

    @NonNull
    public abstract String title();

    @NonNull
    public abstract String programUid();

    @Nullable
    public abstract String event();


}
