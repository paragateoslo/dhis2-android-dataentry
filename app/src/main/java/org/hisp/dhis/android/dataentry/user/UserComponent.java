package org.hisp.dhis.android.dataentry.user;

import android.support.annotation.NonNull;

import org.hisp.dhis.android.dataentry.commons.dagger.PerUser;
import org.hisp.dhis.android.dataentry.form.FormComponent;
import org.hisp.dhis.android.dataentry.form.FormModule;
import org.hisp.dhis.android.dataentry.form.dataentry.DataEntryComponent;
import org.hisp.dhis.android.dataentry.form.dataentry.DataEntryModule;
import org.hisp.dhis.android.dataentry.main.MainComponent;
import org.hisp.dhis.android.dataentry.main.MainModule;
import org.hisp.dhis.android.dataentry.main.home.HomeComponent;
import org.hisp.dhis.android.dataentry.main.home.HomeModule;
import org.hisp.dhis.android.dataentry.reports.ReportsComponent;
import org.hisp.dhis.android.dataentry.reports.ReportsModule;

import dagger.Subcomponent;

@PerUser
@Subcomponent(modules = {
        UserModule.class
})
public interface UserComponent {

    @NonNull
    MainComponent plus(@NonNull MainModule mainModule);

    @NonNull
    HomeComponent plus(@NonNull HomeModule homeModule);

    @NonNull
    ReportsComponent plus(@NonNull ReportsModule reportsModule);

    @NonNull
    FormComponent plus(@NonNull FormModule formModule);

    @NonNull
    DataEntryComponent plus(@NonNull DataEntryModule dataEntryModule);
}
