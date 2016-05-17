package com.stafiiyevskyi.mlsdev.droidfm.functional;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;
import com.stafiiyevskyi.mlsdev.droidfm.JUnitTestHelper;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by oleksandr on 17.05.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class FavoritesContentFragmentTest {
    private static final String TAG = FavoritesContentFragmentTest.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    static {
        JUnitTestHelper.getInstance().setJUnitRunning();
    }

    @Test
    public void test1_swipePager() {
        activityTestRule.getActivity().navigateToFavoritesScreen();
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(allOf(withId(R.id.vp_content), isDisplayed())).perform(swipeLeft());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(allOf(withId(R.id.vp_content), isDisplayed())).perform(swipeLeft());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }

}
