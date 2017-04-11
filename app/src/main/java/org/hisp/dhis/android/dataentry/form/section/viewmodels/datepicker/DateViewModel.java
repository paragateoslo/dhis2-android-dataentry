package org.hisp.dhis.android.dataentry.form.section.viewmodels.datepicker;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import org.hisp.dhis.android.dataentry.form.section.viewmodels.EditableViewModel;

@AutoValue
public abstract class DateViewModel extends EditableViewModel {

    @NonNull
    public static DateViewModel create(@NonNull String uid, @NonNull String label, @NonNull String value,
                                       @NonNull Boolean mandatory) {
        return new AutoValue_DateViewModel(uid, label, value, mandatory);
    }
}
