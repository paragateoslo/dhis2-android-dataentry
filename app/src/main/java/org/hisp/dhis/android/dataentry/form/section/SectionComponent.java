package org.hisp.dhis.android.dataentry.form.section;

import org.hisp.dhis.android.dataentry.commons.dagger.PerFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = SectionModule.class)
public interface SectionComponent {
}