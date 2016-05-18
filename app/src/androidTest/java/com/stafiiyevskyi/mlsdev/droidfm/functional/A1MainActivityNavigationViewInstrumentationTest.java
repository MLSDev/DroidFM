package com.stafiiyevskyi.mlsdev.droidfm.functional;

import android.support.design.widget.NavigationView;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import com.squareup.spoon.Spoon;
import com.stafiiyevskyi.mlsdev.droidfm.JUnitTestHelper;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkPositionIndex;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by oleksandr on 27.04.16.
 */
@RunWith(AndroidJUnit4.class)
public class A1MainActivityNavigationViewInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    static {
        JUnitTestHelper.getInstance().setJUnitRunning();
    }

    @Test
    public void testOpenNavigationDrawer() {
        onView(ViewMatchers.withId(R.id.drawer_layout))
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

    @Test
    public void testNavigationViewItemTracksClick() {
        onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(open());
        onView(withText("Tracks")).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), MainActivity.class.getSimpleName());
    }

    @Test
    public void testNavigationViewItemChartClick() {
        onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(open());
        onView(withText("Charts")).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), MainActivity.class.getSimpleName());
    }

    @Test
    public void testNavigationViewItemArtisClick() {
        onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(open());
        onView(withText("Artists")).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), MainActivity.class.getSimpleName());
    }

    @Test
    public void testNavigationViewItemFavoritesClick() {
        onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(open());
        onView(withText("Favorites")).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), MainActivity.class.getSimpleName());
    }

    @Test
    public void testNavigationViewItemSavedClick() {
        onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(open());
        onView(withText("Saved tracks")).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), MainActivity.class.getSimpleName());
    }
}
