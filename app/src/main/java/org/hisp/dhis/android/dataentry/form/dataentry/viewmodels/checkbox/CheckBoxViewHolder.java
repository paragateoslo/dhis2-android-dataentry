package org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.checkbox;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import org.hisp.dhis.android.dataentry.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.processors.FlowableProcessor;
import rx.exceptions.OnErrorNotImplementedException;

final class CheckBoxViewHolder extends RecyclerView.ViewHolder {
    private static final String TRUE = "true";
    private static final String EMPTY_FIELD = "";

    @BindView(R.id.checkbox_row_checkbox)
    CheckBox checkBox;

    @BindView(R.id.textview_row_label)
    TextView textViewLabel;

    private CheckBoxViewModel viewModel;

    CheckBoxViewHolder(@NonNull ViewGroup parent, @NonNull View itemView,
            @NonNull FlowableProcessor<RowAction> processor) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        RxCompoundButton.checkedChanges(checkBox)
                .skipInitialValue()
                .takeUntil(RxView.detaches(parent))
                .map(isChecked -> RowAction.create(viewModel.uid(), isChecked ? TRUE : EMPTY_FIELD))
                .subscribe(action -> processor.onNext(action), throwable -> {
                    throw new OnErrorNotImplementedException(throwable);
                });
    }

    void update(@NonNull CheckBoxViewModel model) {
        viewModel = model;
        textViewLabel.setText(viewModel.label());
        checkBox.setChecked(viewModel.value());
    }
}