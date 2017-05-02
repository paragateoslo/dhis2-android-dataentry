package org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.edittext;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.hisp.dhis.android.dataentry.R;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormViewModel;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.RowView;

public final class EditTextRowView implements RowView {

    public EditTextRowView() {
        // explicit empty constructor
    }

    @Override
    public ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new EditTextRowViewHolder(inflater.inflate(
                R.layout.recyclerview_row_edittext, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, FormViewModel viewModel) {
        if (viewModel instanceof EditTextViewModel && viewHolder instanceof EditTextRowViewHolder) {
            EditTextViewModel model = (EditTextViewModel) viewModel;
            ((EditTextRowViewHolder) viewHolder).update(model, null);
        }
    }
}