package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteAlbumEntity;

import java.util.List;

/**
 * Created by oleksandr on 10.05.16.
 */
public interface FavoriteAlbumScreenView extends BaseScreenView {
    void showFavoriteAlbums(List<FavoriteAlbumEntity> albums);

    void showSuccess();
}
