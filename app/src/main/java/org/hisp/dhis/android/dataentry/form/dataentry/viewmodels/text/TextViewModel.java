package org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.text;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormViewModel;

@AutoValue
public abstract class TextViewModel extends FormViewModel {

    @NonNull
    public abstract String value();

    @NonNull
    public static TextViewModel create(@NonNull String uid,
                                       @NonNull String label,
                                       @NonNull String value) {
        return new AutoValue_TextViewModel(uid, label, value);
    }
}