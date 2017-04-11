package org.hisp.dhis.android.dataentry.form.section;

import android.support.v4.app.Fragment;

import org.hisp.dhis.android.dataentry.form.SectionViewModel;

public class SectionFragment extends Fragment {

    public static Fragment newInstance(SectionViewModel sectionViewModel) {
        
        return new SectionFragment();
    }
}
