package org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.datepicker;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import org.hisp.dhis.android.dataentry.R;
import org.hisp.dhis.android.dataentry.commons.tuples.Pair;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

final class DatePickerViewHolder extends RecyclerView.ViewHolder {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    // TextViews
    @BindView(R.id.textview_row_label)
    TextView textViewLabel;

    @BindView(R.id.row_date_picker_edit_text)
    EditText editText;

    private DateViewModel viewModel;
    private final FragmentManager fragmentManager;
    private Calendar calendar;

    public DatePickerViewHolder(View itemView, FragmentManager fragmentManager) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        this.fragmentManager = fragmentManager;
        calendar = Calendar.getInstance();

        RxTextView.afterTextChangeEvents(editText).map(textViewAfterTextChangeEvent ->
                Pair.create(viewModel.uid(), textViewAfterTextChangeEvent.editable().toString()))
                .subscribe(viewModel.onValueChangeObserver());
    }

    public void update(DateViewModel viewModel) {
        this.viewModel = viewModel;
        textViewLabel.setText(viewModel.label());
        editText.setText(simpleDateFormat.format(viewModel.value());
    }

    private void setDate(DatePicker datePicker, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String newValue = simpleDateFormat.format(calendar.getTime());
        editText.setText(newValue);
    }

    @OnClick({R.id.row_date_picker_edit_text, R.id.row_date_picker_button_pick})
    void showDatePicker() {
        DatePickerDialogFragment datePicker = DatePickerDialogFragment.newInstance(false);
        datePicker.setOnDateSetListener(this::setDate);
        datePicker.show(fragmentManager);
    }

    @OnClick(R.id.button_clear)
    void clearDate() {
        editText.setText("");
    }

    @OnClick(R.id.row_date_picker_button_today)
    void setTodaysDate() {
        calendar = Calendar.getInstance(); // refresh calendar in case day has shifted since instantiation
        setDate(null, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }
}