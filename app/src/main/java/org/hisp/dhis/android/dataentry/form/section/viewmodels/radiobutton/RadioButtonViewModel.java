package org.hisp.dhis.android.dataentry.form.section.viewmodels.radiobutton;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import org.hisp.dhis.android.dataentry.form.section.viewmodels.EditableViewModel;

@AutoValue
public abstract class RadioButtonViewModel extends EditableViewModel {

    @NonNull
    public static RadioButtonViewModel create(@NonNull String uid,
                                              @NonNull String label,
                                              @NonNull String value,
                                              @NonNull Boolean mandatory) {
        return new AutoValue_RadioButtonViewModel(uid, label, value, mandatory);
    }
}