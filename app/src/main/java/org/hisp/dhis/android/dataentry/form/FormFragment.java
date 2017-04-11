package org.hisp.dhis.android.dataentry.form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hisp.dhis.android.dataentry.R;
import org.hisp.dhis.android.dataentry.commons.ui.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public class FormFragment extends BaseFragment implements FormView {

    // Fragment arguments
    private static final String ARG_EVENT = "event";
    private static final String ARG_PROGRAM = "program";
    private static final String ARG_PROGRAM_STAGE = "programStage";
    private static final String TAG = "FormFragment";

    private String event;
    private String program;
    private String programStage;

    // Views
    //@BindView(R.id.coordinatorlayout_form)
    //CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tablayout_data_entry)
    AutoHidingTabLayout tabLayout;

    @BindView(R.id.viewpager_dataentry)
    ViewPager viewPager;

    //@BindView(R.id.fab_complete_event)
    //FloatingActionButton fab;

    private Unbinder unbinder;

    @Inject
    FormPresenter formPresenter;

    private FormSectionAdapter formSectionAdapter;

    public FormFragment() {
        // Required empty public constructor
    }

    public static FormFragment newInstance(String event, String program, String programStage) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT, event);
        args.putString(ARG_PROGRAM, program);
        args.putString(ARG_PROGRAM_STAGE, programStage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = getArguments().getString(ARG_EVENT);
            program = getArguments().getString(ARG_PROGRAM);
            programStage = getArguments().getString(ARG_PROGRAM_STAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        formSectionAdapter = new FormSectionAdapter(getFragmentManager());
        viewPager.setAdapter(formSectionAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // TODO initialize pickers and FAB
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getUserComponent().plus(new FormModule()).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        formPresenter.onAttach(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        formPresenter.onDetach();
    }

    @Override
    public Consumer<List<SectionViewModel>> renderSectionViewModels() {
        return sectionViewModels -> {
            formSectionAdapter.swapData(sectionViewModels);
        };
    }

    @Override
    public Consumer<String> renderTitle() {
        return title -> toolbar.setTitle(title);
    }

    @Override
    public String event() {
        return event;
    }

    @Override
    public String program() {
        return program;
    }

    @Override
    public String programStage() {
        return programStage;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}