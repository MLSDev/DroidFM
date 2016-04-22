package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.ArtistTopAlbumsResponse;

import rx.Observable;

/**
 * Created by oleksandr on 22.04.16.
 */
public interface ArtistTopAlbumModel {

    Observable<ArtistTopAlbumsResponse> getArtistTopAlbums(String artistName, String artistMbid, int page);

}
