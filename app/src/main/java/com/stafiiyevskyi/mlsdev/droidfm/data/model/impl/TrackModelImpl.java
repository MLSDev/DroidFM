package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail.TrackDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search.TrackSearchResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TrackModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 25.04.16.
 */
public class TrackModelImpl implements TrackModel {
    @Override
    public Observable<TrackSearchResponse> searchTrack(String artistName, String trackName, int page) {
        return LastFMRestClient.getService()
                .searchTrack(artistName, trackName, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<TrackDetailResponse> getTrackDetail(String artist, String track, String mbid) {
        return LastFMRestClient.getService()
                .getTrackDetails(artist, track, mbid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
