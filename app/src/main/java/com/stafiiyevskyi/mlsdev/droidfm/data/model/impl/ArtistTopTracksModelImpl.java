package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.ArtistTopTracks;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search.TrackSearchResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistTopTracksModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ArtistTopTracksModelImpl implements ArtistTopTracksModel {

    @Override
    public Observable<ArtistTopTracks> getArtistTopTracks(String artistName, String artistMbid, int page) {
        return LastFMRestClient.getService()
                .getArtistTopTracks(artistName, artistMbid, page, LastFMRestClient.getAdditionalQuery())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<TrackSearchResponse> searchTrack(String artistName, String trackName, int page) {
        return LastFMRestClient.getService()
                .searchTrack(artistName, trackName, page, LastFMRestClient.getAdditionalQuery())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
