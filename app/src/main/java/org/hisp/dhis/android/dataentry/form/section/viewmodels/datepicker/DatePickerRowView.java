package org.hisp.dhis.android.dataentry.form.section.viewmodels.datepicker;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.hisp.dhis.android.dataentry.R;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.FormItemViewModel;
import org.hisp.dhis.android.dataentry.form.section.viewmodels.RowView;

import static org.hisp.dhis.android.dataentry.commons.utils.Preconditions.isNull;

public class DatePickerRowView implements RowView {

    // we need fragment manager to display DatePickerDialogFragment
    private final FragmentManager fragmentManager;

    public DatePickerRowView(FragmentManager fragmentManager) {
        this.fragmentManager = isNull(fragmentManager, "fragmentManager must not be null");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new DatePickerViewHolder(inflater.inflate(
                R.layout.recyclerview_row_datepicker, parent, false), fragmentManager);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, FormItemViewModel viewModel) {
        if (viewModel instanceof DateViewModel && viewHolder instanceof DatePickerViewHolder) {
            DateViewModel model = (DateViewModel) viewModel;
            ((DatePickerViewHolder) viewHolder).update(model);
        }
    }
}
