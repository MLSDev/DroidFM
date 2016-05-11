package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteArtistDAO;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;

import rx.functions.Func1;

/**
 * Created by oleksandr on 11.05.16.
 */
public class FavoriteArtistToDAOMapper implements Func1<FavoriteArtistEntity, FavoriteArtistDAO> {
    @Override
    public FavoriteArtistDAO call(FavoriteArtistEntity favoriteArtistEntity) {
        FavoriteArtistDAO artist = new FavoriteArtistDAO();
        artist.setImage(favoriteArtistEntity.getImage());
        artist.setMbid(favoriteArtistEntity.getMbid());
        artist.setName(favoriteArtistEntity.getName());
        return artist;
    }
}
