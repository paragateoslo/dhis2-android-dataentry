package org.hisp.dhis.android.dataentry.form;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.hisp.dhis.android.dataentry.form.dataentry.DataEntryFragment;

import java.util.ArrayList;
import java.util.List;

public class FormSectionAdapter extends FragmentStatePagerAdapter {

    private final List<DataEntryViewArguments> dataEntryViewArgumentses;

    public FormSectionAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.dataEntryViewArgumentses = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return DataEntryFragment.newInstance(dataEntryViewArgumentses.get(position));
    }

    @Override
    public int getCount() {
        return dataEntryViewArgumentses.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dataEntryViewArgumentses.get(position).label();
    }

    @NonNull
    public List<DataEntryViewArguments> getData() {
        return dataEntryViewArgumentses;
    }

    public void swapData(List<DataEntryViewArguments> dataEntryViewArgumentses) {
        // TODO: Use DiffUtil for sections as well?
        this.dataEntryViewArgumentses.clear();
        this.dataEntryViewArgumentses.addAll(dataEntryViewArgumentses);
        notifyDataSetChanged();
    }
}
