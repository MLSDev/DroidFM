package com.stafiiyevskyi.mlsdev.droidfm.functional;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;
import com.stafiiyevskyi.mlsdev.droidfm.JUnitTestHelper;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.utils.CheckValues;
import com.stafiiyevskyi.mlsdev.droidfm.utils.TestDispatcherChartsConten;
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
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
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
    public void testA_startDefaultScreenTest() {
        initMockWithExpectedResponse();
        onView(withId(R.id.nb_navigation))
                .perform(click());
        onView(allOf(withId(R.id.rv_toptracks), isDisplayed())).
        perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
    }


    private void initMockWithExpectedResponse() {
        activityTestRule.getActivity().navigateToChartsContentScreen();
        mockWebServer.setDispatcher(new TestDispatcherChartsConten());
        Spoon.screenshot(activityTestRule.getActivity(), TAG);
    }

}
