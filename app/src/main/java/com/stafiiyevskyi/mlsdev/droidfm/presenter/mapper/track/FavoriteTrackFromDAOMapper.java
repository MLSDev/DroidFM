package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteTrackDAO;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;

import rx.functions.Func1;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteTrackFromDAOMapper implements Func1<FavoriteTrackDAO, FavoriteTrackEntity> {
    @Override
    public FavoriteTrackEntity call(FavoriteTrackDAO favoriteTrackDAO) {
        FavoriteTrackEntity track = new FavoriteTrackEntity();
        track.setArtistName(favoriteTrackDAO.getArtist_name());
        track.setTrackName(favoriteTrackDAO.getTrack_name());
        return track;
    }
}
