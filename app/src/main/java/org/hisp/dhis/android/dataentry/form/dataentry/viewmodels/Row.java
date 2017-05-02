package org.hisp.dhis.android.dataentry.form.dataentry.viewmodels;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.hisp.dhis.android.dataentry.commons.tuples.Pair;

import io.reactivex.observers.DisposableObserver;

public interface Row<VH extends RecyclerView.ViewHolder, VM extends FormViewModel> {

    VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent);

    void onBindViewHolder(VH viewHolder, VM viewModel,
                          DisposableObserver<Pair<String, String>> onValueChangeObserver);

    // TODO: Use DiffUtil and provide payloads on viewholder binding
    //void onBindViewHolder(ViewHolder viewHolder, FormViewModel viewModel, List<Object> payloads);
}