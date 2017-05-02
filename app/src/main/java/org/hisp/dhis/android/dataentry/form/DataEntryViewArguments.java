package org.hisp.dhis.android.dataentry.form;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class DataEntryViewArguments {

    // uid of Event or Enrollment
    @NonNull
    public abstract String uid();

    @Nullable
    public abstract String sectionUid();

    @NonNull
    public abstract String label();

    @NonNull
    public abstract Type type();

    public static DataEntryViewArguments create(@NonNull String uid, @Nullable String sectionUid, @NonNull String label,
                                                @NonNull Type type) {
        return new AutoValue_DataEntryViewArguments(uid, sectionUid, label, type);
    }

    public enum Type {
        SECTION, PROGRAM_STAGE, ENROLLMENT
    }
}
