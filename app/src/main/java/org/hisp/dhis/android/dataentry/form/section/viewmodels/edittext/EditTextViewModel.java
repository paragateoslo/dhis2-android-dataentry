package org.hisp.dhis.android.dataentry.form.section.viewmodels.edittext;

import android.support.annotation.NonNull;
import android.text.InputFilter;

import com.google.auto.value.AutoValue;

import org.hisp.dhis.android.dataentry.form.section.viewmodels.EditableViewModel;

import java.util.List;

import static org.hisp.dhis.android.core.utils.Utils.safeUnmodifiableList;

@AutoValue
public abstract class EditTextViewModel extends EditableViewModel {

    @NonNull
    public abstract Integer inputType();

    @NonNull
    public abstract Integer maxLines();

    @NonNull
    public abstract String hint();

    @NonNull
    public abstract List<InputFilter> inputFilters();

    @NonNull
    public static EditTextViewModel create(@NonNull String uid,
                                           @NonNull String label,
                                           @NonNull String value,
                                           @NonNull Boolean mandatory,
                                           @NonNull Integer inputType,
                                           @NonNull Integer maxLines,
                                           @NonNull String hint,
                                           @NonNull List<InputFilter> inputFilters) {
        return new AutoValue_EditTextViewModel(uid, label, value, mandatory, inputType, maxLines, hint,
                safeUnmodifiableList(inputFilters));
    }
}