package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteAlbumDAO;

import java.util.List;

import rx.Observable;

/**
 * Created by oleksandr on 10.05.16.
 */
public interface DBAlbumModel {
    Observable<List<FavoriteAlbumDAO>> getFavoriteAlbums();

    Observable<List<FavoriteAlbumDAO>> findAlbum(String artistName, String albumName);

    void addFavoriteAlbum(FavoriteAlbumDAO album);

    void deleteFromFavorites(FavoriteAlbumDAO album);
}
