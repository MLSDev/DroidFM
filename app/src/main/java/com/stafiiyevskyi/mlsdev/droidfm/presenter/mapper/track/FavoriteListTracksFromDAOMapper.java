package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteTrackDAO;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteListTracksFromDAOMapper implements Func1<List<FavoriteTrackDAO>, List<FavoriteTrackEntity>> {
    @Override
    public List<FavoriteTrackEntity> call(List<FavoriteTrackDAO> favoriteTrackDAOs) {
        return Observable.from(favoriteTrackDAOs).map(new FavoriteTrackFromDAOMapper()).toList().toBlocking().first();
    }
}
