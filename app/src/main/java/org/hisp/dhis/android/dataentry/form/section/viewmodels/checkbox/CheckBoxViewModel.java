package org.hisp.dhis.android.dataentry.form.section.viewmodels.checkbox;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import org.hisp.dhis.android.dataentry.form.section.viewmodels.EditableViewModel;


@AutoValue
public abstract class CheckBoxViewModel extends EditableViewModel {

    @NonNull
    public static CheckBoxViewModel create(@NonNull String uid, @NonNull String label, @NonNull String value,
                                           @NonNull Boolean mandatory) {
        return new AutoValue_CheckBoxViewModel(uid, label, value, mandatory);
    }
}