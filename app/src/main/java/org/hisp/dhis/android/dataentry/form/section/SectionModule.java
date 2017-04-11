package org.hisp.dhis.android.dataentry.form.section;

import android.content.Context;

import com.squareup.sqlbrite.BriteDatabase;

import org.hisp.dhis.android.dataentry.commons.dagger.PerFragment;
import org.hisp.dhis.android.dataentry.commons.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
@PerFragment
public class SectionModule {

    @Provides
    @PerFragment
    EditTextHintCache editTextHintCache(Context context) {
        return new EditTextHintCacheImpl(context);
    }

    @Provides
    @PerFragment
    SectionPresenter sectionPresenter(SchedulerProvider schedulerProvider,
                                      SectionRepository sectionRepository) {
        return new SectionPresenterImpl(schedulerProvider, sectionRepository);
    }

    @Provides
    @PerFragment
    SectionRepository formRepository(EditTextHintCache editTextHintCache, BriteDatabase briteDatabase) {
        return new DataElementsRepository(editTextHintCache, briteDatabase);
    }
}
