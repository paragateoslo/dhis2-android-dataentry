package org.hisp.dhis.android.dataentry.form.dataentry.viewmodels;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public interface RowView {

    ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent);

    void onBindViewHolder(ViewHolder viewHolder, FormViewModel viewModel);

    // TODO: Use DiffUtil and provide payloads on viewholder binding
    //void onBindViewHolder(ViewHolder viewHolder, FormViewModel baseFormViewModel, List<Object> payloads);
}