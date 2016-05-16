package com.stafiiyevskyi.mlsdev.droidfm.utils;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Created by oleksandr on 16.05.16.
 */
public class TestDispatcherChartsConten extends Dispatcher {
    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        switch (request.getPath()) {
            case "/?method=tag.gettopalbums&tag=rock&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getTopAlbumsTagRock(getContext()));
            case "/?method=chart.gettoptags&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse().setResponseCode(200)
                        .setBody(TestAssets.getChartTopTags(getContext()));
            case "/?method=chart.gettoptracks&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse().setResponseCode(200)
                        .setBody(TestAssets.getChartTopTracks(getContext()));
            case "/?method=chart.gettopartists&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse().setResponseCode(200)
                        .setBody(TestAssets.getChartTopArtist(getContext()));
            default:
                return new MockResponse().setResponseCode(404);
        }
    }
}
