package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteAlbumDAO;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteAlbumEntity;

import rx.functions.Func1;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteAlbumFromDAOMapper implements Func1<FavoriteAlbumDAO, FavoriteAlbumEntity> {

    @Override
    public FavoriteAlbumEntity call(FavoriteAlbumDAO favoriteAlbumDAO) {
        FavoriteAlbumEntity albumEntity = new FavoriteAlbumEntity();
        albumEntity.setMbid(favoriteAlbumDAO.getMbid());
        albumEntity.setArtistName(favoriteAlbumDAO.getArtist_name());
        albumEntity.setAlbumName(favoriteAlbumDAO.getAlbum_name());
        return albumEntity;
    }
}
