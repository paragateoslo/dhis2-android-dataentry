package org.hisp.dhis.android.dataentry.form.section.viewmodels.optionset;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import org.hisp.dhis.android.dataentry.form.section.viewmodels.EditableViewModel;

@AutoValue
public abstract class OptionSetViewModel extends EditableViewModel {

    @NonNull
    public static OptionSetViewModel create(@NonNull String uid,
                                            @NonNull String label,
                                            @NonNull String value,
                                            @NonNull Boolean mandatory) {
        return new AutoValue_OptionSetViewModel(uid, label, value, mandatory);
    }
}