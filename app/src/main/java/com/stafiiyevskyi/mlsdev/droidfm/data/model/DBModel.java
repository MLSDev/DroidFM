package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteTrackDAO;

import java.util.List;

import rx.Observable;

/**
 * Created by oleksandr on 06.05.16.
 */
public interface DBModel {

    Observable<List<FavoriteTrackDAO>> getFavoriteTracks();

    void addFavoriteTrack(FavoriteTrackDAO track);
}
