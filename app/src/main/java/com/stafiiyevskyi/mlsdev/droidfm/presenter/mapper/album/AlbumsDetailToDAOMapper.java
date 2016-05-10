package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album;

import com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity.FavoriteAlbumDAO;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumsDetailEntity;

import rx.functions.Func1;

/**
 * Created by oleksandr on 10.05.16.
 */
public class AlbumsDetailToDAOMapper implements Func1<AlbumsDetailEntity, FavoriteAlbumDAO> {
    @Override
    public FavoriteAlbumDAO call(AlbumsDetailEntity albumsDetailEntity) {
        FavoriteAlbumDAO album = new FavoriteAlbumDAO();
        album.setArtist_name(albumsDetailEntity.getArtistName());
        album.setAlbum_name(albumsDetailEntity.getName());
        album.setMbid(albumsDetailEntity.getMbid());
        return album;
    }
}
