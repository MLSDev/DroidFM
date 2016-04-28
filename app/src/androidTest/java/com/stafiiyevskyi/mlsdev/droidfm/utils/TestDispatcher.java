package com.stafiiyevskyi.mlsdev.droidfm.utils;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by oleksandr on 28.04.16.
 */
public class TestDispatcher extends Dispatcher {
    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        switch (request.getPath()) {
            case "/?method=chart.gettoptracks&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getTopTracksResponse(getInstrumentation().getContext()));

            case "/?method=chart.gettoptracks&page=2&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getTopTracksPageSecondResponse(getInstrumentation().getContext()));

            case "/?method=track.getinfo&artist=Twenty%20One%20Pilots&track=Stressed%20Out&mbid=&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getTrackDetailResponse(getInstrumentation().getContext()));

            case "/?method=chart.gettopartists&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getTopArtistsResponse(getInstrumentation().getContext()));

            case "/?method=chart.gettopartists&page=2&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getTopArtistsPageSecondResponse(getInstrumentation().getContext()));
            case "/?method=artist.gettopalbums&artist=Rihanna&mbid=db36a76f-4cdf-43ac-8cd0-5e48092d2bae&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getRihannaAlbumsResponse(getInstrumentation().getContext()));

            case "/?method=artist.gettoptracks&artist=Rihanna&mbid=db36a76f-4cdf-43ac-8cd0-5e48092d2bae&page=1&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getRihannaTracksResponse(getInstrumentation().getContext()));

            case "/?method=album.getinfo&artist=Rihanna&album=Good%20Girl%20Gone%20Bad&mbid=e0d582b5-5f0b-4dda-b1c2-3c34b7ecab8c&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getRihannaAlbumDetailResponse(getInstrumentation().getContext()));
            case "/?method=track.getinfo&artist=Rihanna&track=Umbrella&mbid=0b4b1b0c-e057-4d8d-9a47-12a97ff6fb3d&format=json&api_key=c0cca0938e628d1582474f036955fcfa":
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(TestAssets.getRihannaTrackDetailResponse(getInstrumentation().getContext()));
            default:
                return new MockResponse().setResponseCode(404);
        }
    }
}
