package org.hisp.dhis.android.dataentry.form;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import static org.hisp.dhis.android.dataentry.form.FormViewArguments.Type.ENROLLMENT;
import static org.hisp.dhis.android.dataentry.form.FormViewArguments.Type.EVENT;

@AutoValue
public abstract class FormViewArguments implements Parcelable {

    // this is the uid for an enrollment or an event
    @NonNull
    public abstract String uid();

    @NonNull
    public abstract String programUid();

    // nullable because enrollments do not have programstages
    @Nullable
    public abstract String programStageUid();

    @NonNull
    public abstract Type type();

    @NonNull
    public static FormViewArguments createForEnrollment(
            @NonNull String enrollmentUid, @NonNull String programUid) {
        return new AutoValue_FormViewArguments(1, enrollmentUid, programUid, null, ENROLLMENT);
    }

    @NonNull
    public static FormViewArguments createForEvent(
            @NonNull String eventUid, @NonNull String programUid, @NonNull String programStageUid) {
        return new AutoValue_FormViewArguments(1, eventUid, programUid, programStageUid, EVENT);
    }

    public enum Type {
        ENROLLMENT, EVENT
    }

}