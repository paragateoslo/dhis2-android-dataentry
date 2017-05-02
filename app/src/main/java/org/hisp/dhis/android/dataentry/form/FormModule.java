package org.hisp.dhis.android.dataentry.form;

import com.squareup.sqlbrite.BriteDatabase;

import org.hisp.dhis.android.dataentry.commons.dagger.PerFragment;
import org.hisp.dhis.android.dataentry.commons.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
@PerFragment
public class FormModule {

    @Provides
    @PerFragment
    FormPresenter formPresenter(SchedulerProvider schedulerProvider,
                                FormRepository formRepository) {
        return new FormPresenterImpl(schedulerProvider, formRepository);
    }

    @Provides
    @PerFragment
    FormRepository formRepository(BriteDatabase briteDatabase) {
        return new FormRepositoryImpl(briteDatabase);
    }
}