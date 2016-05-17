package com.stafiiyevskyi.mlsdev.droidfm.functional;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;
import com.stafiiyevskyi.mlsdev.droidfm.JUnitTestHelper;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.utils.TestDispatcherChartsContent;
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
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by oleksandr on 16.05.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ChartContentFragmentTest {
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
    public void testA_startChartTopArtistsScreen() {
        initMockWithExpectedResponse();
        navigateToChartsContentScreen();

        onView(allOf(withText("Artists"), isDisplayed())).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(allOf(withId(R.id.rv_artists), isDisplayed())).perform(swipeDown());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(allOf(withId(R.id.rv_artists), isDisplayed())).
                perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }

    @Test
    public void testB_startChartTopTracksScreen() {
        initMockWithExpectedResponse();
        navigateToChartsContentScreen();
        onView(allOf(withText("Tracks"), isDisplayed())).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(allOf(withId(R.id.rv_toptracks), isDisplayed())).perform(swipeDown());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(allOf(withId(R.id.rv_toptracks), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }

    @Test
    public void testC_startChartTopTagsScreen() {
        initMockWithExpectedResponse();
        navigateToChartsContentScreen();
        onView(allOf(withText("Tags"), isDisplayed())).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(allOf(withId(R.id.rv_toptags), isDisplayed())).perform(swipeDown());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(allOf(withId(R.id.rv_toptags), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Spoon.screenshot(activityTestRule.getActivity(), TAG);

    }

    @Test
    public void testD_swipePagerTagContent() {
        initMockWithExpectedResponse();
        activityTestRule.getActivity().navigateToTagTopContent("rock");
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(allOf(withId(R.id.vp_content), isDisplayed())).perform(swipeLeft());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(allOf(withId(R.id.vp_content), isDisplayed())).perform(swipeLeft());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }

    private void initMockWithExpectedResponse() {
        mockWebServer.setDispatcher(new TestDispatcherChartsContent());
    }

    private void navigateToChartsContentScreen() {
        activityTestRule.getActivity().navigateToChartsContentScreen();
    }

}
