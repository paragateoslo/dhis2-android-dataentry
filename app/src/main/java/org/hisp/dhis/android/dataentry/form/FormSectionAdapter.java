package org.hisp.dhis.android.dataentry.form;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.hisp.dhis.android.dataentry.form.section.SectionFragment;

import java.util.ArrayList;
import java.util.List;

public class FormSectionAdapter extends FragmentStatePagerAdapter {

    private final List<SectionViewModel> sectionViewModels;

    public FormSectionAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.sectionViewModels = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return SectionFragment.newInstance(sectionViewModels.get(position));
    }

    @Override
    public int getCount() {
        return sectionViewModels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sectionViewModels.get(position).label();
    }

    @NonNull
    public List<SectionViewModel> getData() {
        return sectionViewModels;
    }

    public void swapData(List<SectionViewModel> sectionViewModels) {
        // TODO: Use DiffUtil for sections as well?
        this.sectionViewModels.clear();
        this.sectionViewModels.addAll(sectionViewModels);
        notifyDataSetChanged();
    }
}
