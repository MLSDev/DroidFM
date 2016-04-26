package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topalbums.TagTopAlbumsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topartists.TagTopArtistsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.toptracks.TagTopTracksResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TagModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagModelImpl implements TagModel {

    @Override
    public Observable<TagTopAlbumsResponse> getTagsTopAlbums(String tag, int pageNumber) {
        return LastFMRestClient.getService().getTagsTopAlbums(tag, pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<TagTopArtistsResponse> getTagsTopArtists(String tag, int pageNumber) {
        return LastFMRestClient.getService().getTagsTopArtists(tag, pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<TagTopTracksResponse> getTagsTopTracks(String tag, int pageNumber) {
        return LastFMRestClient.getService().getTagsTopTracks(tag, pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
