package org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.checkbox;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.hisp.dhis.android.dataentry.R;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormViewModel;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.RowView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckBoxRowView implements RowView {
    private static final String TRUE = "true";
    private static final String EMPTY_FIELD = "";

    public CheckBoxRowView() {
        // explicit empty constructor
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new CheckBoxRowViewHolder(inflater.inflate(
                R.layout.recyclerview_row_checkbox, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, FormViewModel viewModel) {
        if (viewModel instanceof CheckBoxViewModel && viewHolder instanceof CheckBoxRowViewHolder) {
            CheckBoxViewModel model = (CheckBoxViewModel) viewModel;
            ((CheckBoxRowViewHolder) viewHolder).update(model);
        }
    }

    static class CheckBoxRowViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox_row_checkbox)
        CheckBox checkBox;

        @BindView(R.id.textview_row_label)
        TextView textViewLabel;

        private CheckBoxViewModel viewModel;

        CheckBoxRowViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            /*RxCompoundButton.checkedChanges(checkBox).map(checked ->
                    Pair.create(viewModel.uid(), checked ? TRUE : EMPTY_FIELD))
                    .subscribe(viewModel.onValueChangeObserver()); */

            //RxView.clicks(itemView).subscribe(o -> checkBox.setChecked(!checkBox.isChecked()));
        }

        void update(CheckBoxViewModel viewModel) {
            this.viewModel = viewModel;
            textViewLabel.setText(viewModel.label());

            if (EMPTY_FIELD.equals(viewModel.value())) {
                checkBox.setChecked(false);
            } else if (TRUE.equals(viewModel.value())) {
                checkBox.setChecked(true);
            }
        }
    }
}
