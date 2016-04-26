package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetailResponse;

import rx.Observable;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface AlbumModel {
    Observable<AlbumDetailResponse> getAlbumDetails(String mbid);
}
