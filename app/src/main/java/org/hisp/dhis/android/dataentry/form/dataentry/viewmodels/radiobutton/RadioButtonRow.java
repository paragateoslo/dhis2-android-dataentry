package org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.radiobutton;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.hisp.dhis.android.dataentry.R;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormItemViewModel;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.Row;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.RowAction;

import io.reactivex.processors.FlowableProcessor;

final class RadioButtonRow implements Row {

    @NonNull
    private final LayoutInflater inflater;

    @NonNull
    private final FlowableProcessor<RowAction> processor;

    RadioButtonRow(@NonNull LayoutInflater inflater,
            @NonNull FlowableProcessor<RowAction> processor) {
        this.inflater = inflater;
        this.processor = processor;
    }

    @NonNull
    @Override
    public ViewHolder onCreate(@NonNull ViewGroup parent) {
        return new RadioButtonViewHolder(parent, inflater.inflate(
                R.layout.recyclerview_row_radiobutton, parent, false), processor);
    }

    @Override
    public void onBind(@NonNull ViewHolder viewHolder, @NonNull FormItemViewModel viewModel) {
        ((RadioButtonViewHolder) viewHolder).update((RadioButtonViewModel) viewModel);
    }
}