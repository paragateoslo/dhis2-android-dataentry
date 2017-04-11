package org.hisp.dhis.android.dataentry.form.section.viewmodels.coordinate;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import org.hisp.dhis.android.dataentry.form.section.viewmodels.EditableViewModel;

@AutoValue
public abstract class CoordinateViewModel extends EditableViewModel {

    @NonNull
    public static CoordinateViewModel create(@NonNull String uid,
                                             @NonNull String label,
                                             @NonNull String value,
                                             @NonNull Boolean mandatory) {
        return new AutoValue_CoordinateViewModel(uid, label, value, mandatory);
    }
}