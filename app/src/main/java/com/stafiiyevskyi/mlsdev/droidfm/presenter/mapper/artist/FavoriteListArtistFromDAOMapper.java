package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteArtistDAO;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 11.05.16.
 */
public class FavoriteListArtistFromDAOMapper implements Func1<List<FavoriteArtistDAO>, List<FavoriteArtistEntity>> {
    @Override
    public List<FavoriteArtistEntity> call(List<FavoriteArtistDAO> favoriteArtistDAOs) {
        return Observable.from(favoriteArtistDAOs).map(new FavoriteArtistFromDAOMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
