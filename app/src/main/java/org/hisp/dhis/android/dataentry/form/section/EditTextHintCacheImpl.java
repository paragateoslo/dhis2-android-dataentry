package org.hisp.dhis.android.dataentry.form.section;


import android.content.Context;
import android.util.SparseArray;

import org.hisp.dhis.android.dataentry.R;

public class EditTextHintCacheImpl implements EditTextHintCache {

    private SparseArray<String> hintCache;

    public EditTextHintCacheImpl(Context context) {
        hintCache = new SparseArray<>();
        hintCache.append(R.string.enter_text, context.getString(R.string.enter_text));
        hintCache.append(R.string.enter_long_text, context.getString(R.string.enter_long_text));
        hintCache.append(R.string.enter_number, context.getString(R.string.enter_number));
        hintCache.append(R.string.enter_integer, context.getString(R.string.enter_integer));
        hintCache.append(R.string.enter_negative_integer, context.getString(R.string.enter_negative_integer));
        hintCache.append(
                R.string.enter_positive_integer_or_zero, context.getString(R.string.enter_positive_integer_or_zero));
        hintCache.append(R.string.enter_positive_integer, context.getString(R.string.enter_positive_integer));
        hintCache.append(R.string.enter_date, context.getString(R.string.enter_date));
    }

    @Override
    public String hint(Integer resourceId) {
        return hintCache.get(resourceId);
    }
}
