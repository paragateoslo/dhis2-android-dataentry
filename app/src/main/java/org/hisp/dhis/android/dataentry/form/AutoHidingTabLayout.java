package org.hisp.dhis.android.dataentry.form;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import static org.hisp.dhis.android.dataentry.commons.utils.Preconditions.isNull;

/**
 * A TabLayout that automatically hides when the attached adapter has less than 2 elements
 */
public class AutoHidingTabLayout extends TabLayout {

    @Nullable
    private ViewPager viewPager;

    public AutoHidingTabLayout(Context context) {
        super(context);
    }

    public AutoHidingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {

        isNull(viewPager, "viewPager == null");
        isNull(viewPager.getAdapter(), "viewPager.getAdapter == null. You must set an adapter on the ViewPager before" +
                " setting up the AutoHidingTabLayout");

        this.viewPager = viewPager;

        AdapterChangeObserver adapterChangeObserver = new AdapterChangeObserver();
        viewPager.getAdapter().registerDataSetObserver(adapterChangeObserver);
        viewPager.addOnAdapterChangeListener((viewPager1, oldAdapter, newAdapter) -> {
            if (newAdapter != null) {
                newAdapter.registerDataSetObserver(adapterChangeObserver);
            }
        });

        toggleVisibility();

        super.setupWithViewPager(viewPager);
    }

    private void toggleVisibility() {
        if (viewPager.getAdapter().getCount() < 2) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    private class AdapterChangeObserver extends DataSetObserver {

        @Override
        public void onChanged() {

            if (viewPager == null || viewPager.getAdapter() == null) {
                setVisibility(GONE);
            } else {
                toggleVisibility();
            }
        }
    }
}