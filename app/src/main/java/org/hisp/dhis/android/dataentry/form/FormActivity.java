package org.hisp.dhis.android.dataentry.form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.hisp.dhis.android.dataentry.R;

import static org.hisp.dhis.android.dataentry.commons.utils.Preconditions.isNull;

public class FormActivity extends AppCompatActivity {

    private static String ARG_EVENT = "formViewArguments";
    private FormViewArguments formViewArguments;

    public static void startActivity(Activity activity, FormViewArguments formViewArguments) {
        isNull(activity, "activity must not be null");

        Intent intent = new Intent(activity, FormActivity.class);
        intent.putExtra(ARG_EVENT, formViewArguments);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        formViewArguments = getIntent().getParcelableExtra(ARG_EVENT);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, FormFragment.newInstance(formViewArguments))
                .commit();

    }
}