package org.hisp.dhis.android.dataentry.form;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * This TabLayout will be hidden if the attached adapter has less than 2 elements
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
        this.viewPager = viewPager;

        AdapterChangeObserver adapterChangeObserver = new AdapterChangeObserver();

        if (viewPager != null && viewPager.getAdapter() != null && viewPager.getAdapter().getCount() < 2) {
            setVisibility(GONE);

            viewPager.getAdapter().registerDataSetObserver(adapterChangeObserver);

            viewPager.addOnAdapterChangeListener((viewPager1, oldAdapter, newAdapter) -> {
                if (newAdapter != null) {
                    newAdapter.registerDataSetObserver(adapterChangeObserver);
                }
            });
        } else {
            setVisibility(VISIBLE);
        }

        super.setupWithViewPager(viewPager);
    }

    private class AdapterChangeObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            if (viewPager != null && viewPager.getAdapter() != null && viewPager.getAdapter().getCount() == 1) {
                setVisibility(GONE);
            }
        }

        @Override
        public void onInvalidated() {
            if (viewPager != null && viewPager.getAdapter() != null && viewPager.getAdapter().getCount() == 1) {
                setVisibility(GONE);
            }
        }
    }
}