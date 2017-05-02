package org.hisp.dhis.android.dataentry.form.dataentry;

import android.support.v4.app.Fragment;

import org.hisp.dhis.android.dataentry.form.DataEntryViewArguments;

public class DataEntryFragment extends Fragment {

    public static Fragment newInstance(DataEntryViewArguments dataEntryViewArguments) {
        
        return new DataEntryFragment();
    }
}
