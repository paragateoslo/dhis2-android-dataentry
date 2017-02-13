/*
 * Copyright (c) 2017, University of Oslo
 *
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.hisp.dhis.android.dataentry.home;

import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;

import org.hisp.dhis.android.dataentry.R;
import org.hisp.dhis.android.dataentry.espresso.OrientationChangeAction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.close;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hisp.dhis.android.dataentry.espresso.CustomViewMatchers.withToolbarTitle;

class HomeRobot {

    HomeRobot() {
        // explicit public constructor
    }

    HomeRobot openSlidingPanel() {
        onView(withId(R.id.drawer_layout))
                .perform(open(GravityCompat.START));
        return this;
    }

    HomeRobot closeSlidingPanel() {
        onView(withId(R.id.drawer_layout))
                .perform(close(GravityCompat.START));
        return this;
    }

    HomeRobot checkUserInitials(@NonNull String initials) {
        onView(withId(R.id.textview_username_initials))
                .check(matches(withText(initials)));
        return this;
    }

    HomeRobot checkUsername(@NonNull String username) {
        onView(withId(R.id.textview_username))
                .check(matches(withText(username)));
        return this;
    }

    HomeRobot checkUserInfo(@NonNull String userInfo) {
        onView(withId(R.id.textview_user_info))
                .check(matches(withText(userInfo)));
        return this;
    }

    HomeRobot checkToolbarTitle(@NonNull String title) {
        onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));
        return this;
    }

    HomeRobot formsMenuItemIsSelected() {
        onView(withId(R.id.navigation_view))
                .perform(navigateTo(R.id.drawer_item_forms))
                .check(matches(isSelected()));
        return this;
    }

    HomeRobot rotateToPortrait() {
        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait());
        return this;
    }

    HomeRobot rotateToLandscape() {
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape());
        return this;
    }
}
