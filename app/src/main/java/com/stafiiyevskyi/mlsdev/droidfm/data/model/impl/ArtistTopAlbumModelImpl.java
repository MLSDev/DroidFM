package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.ArtistTopAlbumsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistTopAlbumModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ArtistTopAlbumModelImpl implements ArtistTopAlbumModel {

    @Override
    public Observable<ArtistTopAlbumsResponse> getArtistTopAlbums(String artistName, String artistMbid, int page) {
        return LastFMRestClient.getService()
                .getArtistTopAlbums(artistName, artistMbid, page, LastFMRestClient.getAdditionalQuery())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
