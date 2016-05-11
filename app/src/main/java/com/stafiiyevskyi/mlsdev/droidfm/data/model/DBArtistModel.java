package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteArtistDAO;

import java.util.List;

import rx.Observable;

/**
 * Created by oleksandr on 11.05.16.
 */
public interface DBArtistModel {
    Observable<List<FavoriteArtistDAO>> getFavoriteArtists();

    Observable<List<FavoriteArtistDAO>> findArtist(String artistName, String mbid);

    void addFavoriteArtist(FavoriteArtistDAO artist);

    void deleteFromFavorites(FavoriteArtistDAO artist);
}
