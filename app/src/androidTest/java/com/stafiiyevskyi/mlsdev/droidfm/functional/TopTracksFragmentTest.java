package com.stafiiyevskyi.mlsdev.droidfm.functional;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.utils.CheckValues;
import com.stafiiyevskyi.mlsdev.droidfm.JUnitTestHelper;
import com.stafiiyevskyi.mlsdev.droidfm.utils.TestDispatcher;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by oleksandr on 28.04.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TopTracksFragmentTest {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    static {
        JUnitTestHelper.getInstance().setJUnitRunning();
    }

    private MockWebServer mockWebServer;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        LastFMRestClient.setBaseUrl(mockWebServer.url("/").toString());
    }

    @After
    public void shutDownServer() throws Exception {
        mockWebServer.shutdown();
    }


    @Test
    public void testA_TracksShown() {
        initMockWithExpectedResponse();
        onView(withId(R.id.pb_progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rv_toptracks)).check(matches(hasDescendant(withText(CheckValues.TITLE_FROM_TOP_TRACKS_FIRST_PAGE))));
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }

    @Test
    public void testB_Pagination() {
        initMockWithExpectedResponse();
        onView(withId(R.id.rv_toptracks)).perform(RecyclerViewActions.scrollToPosition(49));
        onView(withId(R.id.rv_toptracks)).perform(RecyclerViewActions.scrollToPosition(CheckValues.TOP_TRACKS_SECOND_PAGE));
        onView(withId(R.id.rv_toptracks)).check(matches(hasDescendant(withText(CheckValues.TITLE_FROM_TOP_TRACKS_SECOND_PAGE))));
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }

    @Test
    public void testC_ClickOnItem() {
        initMockWithExpectedResponse();
        onView(withText(CheckValues.TITLE_FROM_TOP_TRACKS_FIRST_PAGE)).perform(click());
        onView(withId(R.id.ll_content)).check(matches(hasDescendant(withText(CheckValues.TITLE_FROM_TOP_TRACKS_FIRST_PAGE))));
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }


    private void initMockWithExpectedResponse() {
        mockWebServer.setDispatcher(new TestDispatcher());
        launchActivity();
    }

    private void launchActivity() {
        activityTestRule.getActivity().navigateToTopTracksScreen();
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }
}
