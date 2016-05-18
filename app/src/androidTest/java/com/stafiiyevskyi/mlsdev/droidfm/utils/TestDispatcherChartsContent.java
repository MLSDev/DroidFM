package com.stafiiyevskyi.mlsdev.droidfm.utils;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Created by oleksandr on 16.05.16.
 */
public class TestDispatcherChartsContent extends Dispatcher {
    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        switch (request.getPath()) {
            case "/?method=tag.gettopalbums&tag=rock&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getTopAlbumsTagRock(getContext()));
            case "/?method=tag.gettopartists&tag=rock&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getTagRockTopArtists(getContext()));
            case "/?method=tag.gettoptracks&tag=rock&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse().setResponseCode(200)
                        .setBody(TestAssets.getTagRockTopTracks(getContext()));
            case "/?method=chart.gettoptags&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse().setResponseCode(200)
                        .setBody(TestAssets.getChartTopTags(getContext()));
            case "/?method=chart.gettoptracks&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse().setResponseCode(200)
                        .setBody(TestAssets.getChartTopTracks(getContext()));
            case "/?method=chart.gettopartists&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse().setResponseCode(200)
                        .setBody(TestAssets.getChartTopArtist(getContext()));
            case "/?method=artist.gettopalbums&artist=Rihanna&mbid=db36a76f-4cdf-43ac-8cd0-5e48092d2bae&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getRihannaAlbumsResponse(getContext()));
            case "/?method=artist.gettoptracks&artist=Rihanna&mbid=db36a76f-4cdf-43ac-8cd0-5e48092d2bae&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getRihannaTracksResponse(getContext()));
            default:
                return new MockResponse().setResponseCode(404);
        }
    }
}
