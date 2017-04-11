package org.hisp.dhis.android.dataentry.form.section.viewmodels;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.text.InputFilter;
import android.text.InputType;

import org.hisp.dhis.android.core.common.ValueType;
import org.hisp.dhis.android.dataentry.R;
import org.hisp.dhis.android.dataentry.commons.utils.Preconditions;
import org.hisp.dhis.android.dataentry.form.section.EditTextHintCache;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.checkbox.CheckBoxViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.coordinate.CoordinateViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.datepicker.DateViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.edittext.EditTextViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.optionset.OptionSetViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.radiobutton.RadioButtonViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_TEXT;

final public class FormItemViewModelFactory {

    private FormItemViewModelFactory() {
        //no instances
    }

    public static FormItemViewModel fromCursor(Cursor cursor, EditTextHintCache editTextHintCache) {
        return create(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                integerToBoolean(cursor.getInt(3)), ValueType.valueOf(cursor.getString(4)), cursor.getString(5),
                editTextHintCache);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public static FormItemViewModel create(@NonNull String uid, @NonNull String label, @NonNull String value,
                                           @NonNull Boolean mandatory, @NonNull ValueType valueType,
                                           @Nullable String optionSet, @NonNull EditTextHintCache editTextHintCache) {

        Preconditions.isNull(valueType, "Unsupported ValueType: 'NULL' for: " + label + " - " + uid);

        if (isOptionSet(optionSet)) {
            return OptionSetViewModel.create(uid, label, value, mandatory);
        } else if (isEditTextType(valueType)) {
            return createEditTextViewModel(uid, label, value, mandatory, valueType, editTextHintCache);
        } else {
            switch (valueType) {
                case BOOLEAN:
                    return RadioButtonViewModel.create(uid, label, value, mandatory);
                case TRUE_ONLY:
                    return CheckBoxViewModel.create(uid, label, value, mandatory);
                case DATE:
                    return DateViewModel.create(uid, label, value, mandatory);
                case COORDINATE:
                    return CoordinateViewModel.create(uid, label, value, mandatory);
                default:
                    throw new IllegalArgumentException("Unsupported ValueType: '" + valueType.name() +
                            "' for: " + label + " - " + uid);
            /* TODO: implement later
            case LETTER:
                break;
            case EMAIL:
                break;
            case DATETIME:
                break;
            case TIME:
                break;
            case UNIT_INTERVAL:
                break;
            case PERCENTAGE:
                break;
            case TRACKER_ASSOCIATE:
                break;
            case USERNAME:
                break;
            case FILE_RESOURCE:
                break;
            case ORGANISATION_UNIT:
                break;
            case AGE:
                break;
            case URL:
                break; */
            }
        }
    }

    private static boolean isOptionSet(String optionSet) {
        return optionSet != null;
    }

    private static boolean isEditTextType(ValueType valueType) {
        return valueType.isText() || valueType.isInteger() || valueType.isNumeric();
    }

    @NonNull
    private static EditTextViewModel createEditTextViewModel(String uid, String label, String value, Boolean mandatory,
                                                             ValueType valueType, EditTextHintCache editTextHintCache) {
        // default values
        EditTextViewModel formViewModel = null;
        Integer inputType = TYPE_CLASS_TEXT;
        Integer maxLines = 1;
        String hint;
        List<InputFilter> inputFilters = new ArrayList<>(); // todo: use inputfilters when needed

        switch (valueType) {
            case TEXT:
                hint = editTextHintCache.hint(R.string.enter_text);
                formViewModel = EditTextViewModel.create(uid, label, value, mandatory, inputType, maxLines, hint,
                        inputFilters);
                break;
            case LONG_TEXT:
                maxLines = 3;
                hint = editTextHintCache.hint(R.string.enter_long_text);
                formViewModel = EditTextViewModel.create(uid, label, value, mandatory, inputType, maxLines, hint,
                        inputFilters);
                break;
            case NUMBER:
                inputType = InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL |
                        InputType.TYPE_NUMBER_FLAG_SIGNED;
                hint = editTextHintCache.hint(R.string.enter_number);
                formViewModel = EditTextViewModel.create(uid, label, value, mandatory, inputType, maxLines, hint,
                        inputFilters);
                break;
            case INTEGER:
                inputType = InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_SIGNED;
                hint = editTextHintCache.hint(R.string.enter_integer);
                formViewModel = EditTextViewModel.create(uid, label, value, mandatory, inputType, maxLines, hint,
                        inputFilters);
                break;
            case INTEGER_POSITIVE:
                inputType = InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_SIGNED;
                hint = editTextHintCache.hint(R.string.enter_positive_integer);
                formViewModel = EditTextViewModel.create(uid, label, value, mandatory, inputType, maxLines, hint,
                        inputFilters);
                break;
            case INTEGER_NEGATIVE:
                inputType = InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_SIGNED;
                hint = editTextHintCache.hint(R.string.enter_negative_integer);
                formViewModel = EditTextViewModel.create(uid, label, value, mandatory, inputType, maxLines, hint,
                        inputFilters);
                break;
            case INTEGER_ZERO_OR_POSITIVE:
                inputType = InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_SIGNED;
                hint = editTextHintCache.hint(R.string.enter_positive_integer_or_zero);
                formViewModel = EditTextViewModel.create(uid, label, value, mandatory, inputType, maxLines, hint,
                        inputFilters);
                break;
            default:
                break;
        }

        Preconditions.isNull(formViewModel, "Unsupported ValueType: '" + valueType.name() + "'");

        return formViewModel;
    }

    private static Boolean integerToBoolean(int integer) {
        return integer == 1;
    }
}
