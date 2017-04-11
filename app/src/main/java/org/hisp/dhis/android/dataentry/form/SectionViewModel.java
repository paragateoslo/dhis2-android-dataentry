package org.hisp.dhis.android.dataentry.form;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SectionViewModel {

    // uid of Event or Enrollment
    @NonNull
    public abstract String uid();

    @Nullable
    public abstract String sectionUid();

    @NonNull
    public abstract String label();

    @NonNull
    public abstract Type type();

    public static SectionViewModel create(@NonNull String uid, @Nullable String sectionUid, @NonNull String label,
                                          @NonNull Type type) {
        return new AutoValue_SectionViewModel(uid, sectionUid, label, type);
    }

    public enum Type {
        SECTION, PROGRAM_STAGE, ENROLLMENT
    }
}
