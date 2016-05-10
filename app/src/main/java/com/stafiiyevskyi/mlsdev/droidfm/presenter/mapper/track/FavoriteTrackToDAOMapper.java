package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteTrackDAO;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;

import rx.functions.Func1;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteTrackToDAOMapper implements Func1<FavoriteTrackEntity, FavoriteTrackDAO> {
    @Override
    public FavoriteTrackDAO call(FavoriteTrackEntity favoriteTrack) {
        FavoriteTrackDAO track = new FavoriteTrackDAO();
        track.setTrack_name(favoriteTrack.getTrackName());
        track.setArtist_name(favoriteTrack.getArtistName());
        return track;
    }
}