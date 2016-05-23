package com.stafiiyevskyi.mlsdev.droidfm.functional;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;
import com.stafiiyevskyi.mlsdev.droidfm.JUnitTestHelper;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.utils.TestDispatcher;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.ArtistContentDetailsFragment;

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
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by oleksandr on 18.05.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ArtistDetailFragmentTest {
    private static final String TAG = ArtistContentDetailsFragment.class.getSimpleName();
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
    public void test1_OnAlbumClick() {
        initWithExpectedResponse();
        onView(withText("Good Girl Gone Bad")).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }

    @Test
    public void test2_OnTrackClick() {
        initWithExpectedResponse();
        onView(withText("Top Tracks")).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(withText("Umbrella")).perform(click());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }

    @Test
    public void test3_SwipePager(){
        initWithExpectedResponse();
        onView(withId(R.id.main_content)).perform(swipeUp());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
        onView(withId(R.id.vp_content)).perform(swipeLeft());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }

    private void initWithExpectedResponse() {
        mockWebServer.setDispatcher(new TestDispatcher());
//        activityTestRule.getActivity().navigateToArtistContentDetailsScreen("db36a76f-4cdf-43ac-8cd0-5e48092d2bae", "Rihanna", "http://img2-ak.lst.fm/i/u/300x300/f14ad6c0a9b14b45206c962021b767d7.png");
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }
}
