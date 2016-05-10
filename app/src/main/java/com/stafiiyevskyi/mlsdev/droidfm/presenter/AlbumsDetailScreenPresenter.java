package com.stafiiyevskyi.mlsdev.droidfm.presenter;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumsDetailEntity;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface AlbumsDetailScreenPresenter extends Presenter {

    void getAlbumsDetails(String artist, String album, String mbid);

    void addAlbumToFavorite(AlbumsDetailEntity album);

    void deleteFromFavorite(AlbumsDetailEntity album);

    void isTrackFavorite(AlbumsDetailEntity album);
}
