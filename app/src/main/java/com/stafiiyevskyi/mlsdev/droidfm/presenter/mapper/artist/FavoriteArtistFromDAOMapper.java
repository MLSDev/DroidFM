package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteArtistDAO;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;

import rx.functions.Func1;

/**
 * Created by oleksandr on 11.05.16.
 */
public class FavoriteArtistFromDAOMapper implements Func1<FavoriteArtistDAO, FavoriteArtistEntity> {
    @Override
    public FavoriteArtistEntity call(FavoriteArtistDAO favoriteArtistDAO) {
        FavoriteArtistEntity artist = new FavoriteArtistEntity();
        artist.setMbid(favoriteArtistDAO.getMbid());
        artist.setName(favoriteArtistDAO.getName());
        artist.setImage(favoriteArtistDAO.getImage());
        return artist;
    }
}
