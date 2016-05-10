package com.stafiiyevskyi.mlsdev.droidfm.presenter;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteAlbumEntity;

/**
 * Created by oleksandr on 10.05.16.
 */
public interface FavoriteAlbumsScreenPresenter extends Presenter {

    void getFavoritesAlbums();

    void deleteFromFavorites(FavoriteAlbumEntity album);
}
