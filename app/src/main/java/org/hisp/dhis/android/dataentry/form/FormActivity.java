package org.hisp.dhis.android.dataentry.form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.hisp.dhis.android.dataentry.R;

import static org.hisp.dhis.android.dataentry.commons.utils.Preconditions.isNull;

public class FormActivity extends AppCompatActivity {

    private static String ARG_EVENT = "event";
    private static String ARG_PROGRAM = "program";
    private static String ARG_PROGRAM_STAGE = "programStage";
    private String event;
    private String program;
    private String programStage;

    public static void startActivity(Activity activity, String event, String program, String programStage) {
        isNull(activity, "activity must not be null");

        Intent intent = new Intent(activity, FormActivity.class);
        intent.putExtra(ARG_EVENT, event);
        intent.putExtra(ARG_PROGRAM, program);
        intent.putExtra(ARG_PROGRAM_STAGE, programStage);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        event = getIntent().getStringExtra(ARG_EVENT);
        program = getIntent().getStringExtra(ARG_PROGRAM);
        programStage = getIntent().getStringExtra(ARG_PROGRAM_STAGE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, FormFragment.newInstance(event, program, programStage))
                .commit();

    }
}