package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteAlbumDAO;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteAlbumEntity;

import rx.functions.Func1;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteAlbumToDAOMapper implements Func1<FavoriteAlbumEntity, FavoriteAlbumDAO> {
    @Override
    public FavoriteAlbumDAO call(FavoriteAlbumEntity favoriteAlbumEntity) {
        FavoriteAlbumDAO albumDAO = new FavoriteAlbumDAO();
        albumDAO.setAlbum_name(favoriteAlbumEntity.getAlbumName());
        albumDAO.setArtist_name(favoriteAlbumEntity.getArtistName());
        albumDAO.setMbid(favoriteAlbumEntity.getMbid());
        return albumDAO;
    }
}
