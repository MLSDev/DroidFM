package com.stafiiyevskyi.mlsdev.droidfm;

import android.support.design.widget.NavigationView;
import android.support.test.rule.ActivityTestRule;
import android.view.Gravity;

import com.squareup.spoon.Spoon;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkPositionIndex;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by oleksandr on 27.04.16.
 */
public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testOpenNavigationDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(open());
        onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
        Spoon.screenshot(activityTestRule.getActivity(), MainActivity.class.getSimpleName());
    }

    @Test
    public void testNavigationViewItemsCount() {
        NavigationView navigationView = (NavigationView) activityTestRule.getActivity().findViewById(R.id.nav_view);
        checkNotNull(navigationView, "navigationView is null");
        final int count = navigationView.getMenu().size();
        checkPositionIndex(3, count, count + " size");
    }
}
