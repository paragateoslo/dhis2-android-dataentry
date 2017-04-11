package org.hisp.dhis.android.dataentry.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hisp.dhis.android.dataentry.R;
import org.hisp.dhis.android.dataentry.commons.ui.BaseFragment;
import org.hisp.dhis.android.dataentry.commons.ui.DividerDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public class HomeFragment extends BaseFragment implements HomeView {

    @Inject
    HomePresenter homePresenter;

    @BindView(R.id.swiperefreshlayout_home)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.home_recyclerview)
    RecyclerView recyclerView;

    private HomeViewModelAdapter homeViewModelAdapter;
    private AlertDialog alertDialog;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();
        setupSwipeRefreshLayout();

        recyclerView.setAdapter(homeViewModelAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerDecoration(
                ContextCompat.getDrawable(getActivity(), R.drawable.divider)));
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.color_primary);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getUserComponent().plus(new HomeModule()).inject(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        homePresenter.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        homePresenter.onAttach(this);
    }

    private void setupAdapter() {

        homeViewModelAdapter = new HomeViewModelAdapter(getActivity());
        homeViewModelAdapter.setOnHomeItemClickListener(homeEntity -> {
            /*if (homeEntity.getType() == HomeViewModel.Type.TRACKED_ENTITY) {
                // todo go to TrackedEntity activity
            } else {
                // go to program activity
            }*/
        });
    }

    @Override
    public Consumer<List<HomeViewModel>> swapData() {
        return new Consumer<List<HomeViewModel>>() {
            @Override
            public void accept(List<HomeViewModel> homeEntities) throws Exception {
                homeViewModelAdapter.swapData(homeEntities);
            }
        };
    }

    @Override
    public void renderError(String message) {
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setPositiveButton(R.string.option_confirm, null);
            alertDialog = builder.create();
        }
        alertDialog.setTitle(getString(R.string.error_generic));
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}