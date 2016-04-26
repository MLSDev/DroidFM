package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.AlbumModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumModelImpl implements AlbumModel {
    @Override
    public Observable<AlbumDetailResponse> getAlbumDetails(String mbid) {
        return LastFMRestClient.getService().getAlbumDetails(mbid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
