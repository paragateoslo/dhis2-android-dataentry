package org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.datepicker;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.EditableViewModel;

import java.util.Date;

@AutoValue
public abstract class DateViewModel extends EditableViewModel {

    abstract Date value();

    @NonNull
    public static DateViewModel create(@NonNull String uid, @NonNull String label, @NonNull Date value,
                                       @NonNull Boolean mandatory) {
        return new AutoValue_DateViewModel(uid, label, value, mandatory);
    }
}
