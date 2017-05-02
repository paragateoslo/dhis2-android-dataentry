package org.hisp.dhis.android.dataentry.form.dataentry;

import android.content.Context;

import com.squareup.sqlbrite.BriteDatabase;

import org.hisp.dhis.android.dataentry.commons.dagger.PerFragment;
import org.hisp.dhis.android.dataentry.commons.schedulers.SchedulerProvider;
import org.hisp.dhis.android.dataentry.form.dataentry.viewmodels.FormViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
@PerFragment
public class DataEntryModule {

    @Provides
    @PerFragment
    EditTextHintCache editTextHintCache(Context context) {
        return new EditTextHintCacheImpl(context);
    }

    @Provides
    @PerFragment
    DataEntryPresenter sectionPresenter(SchedulerProvider schedulerProvider,
                                        DataEntryRepository dataEntryRepository) {
        return new DataEntryPresenterImpl(schedulerProvider, dataEntryRepository);
    }

    @Provides
    @PerFragment
    DataEntryRepository formRepository(BriteDatabase briteDatabase, FormViewModelFactory formViewModelFactory) {
        return new DataElementRepository(briteDatabase, formViewModelFactory);
    }
}
