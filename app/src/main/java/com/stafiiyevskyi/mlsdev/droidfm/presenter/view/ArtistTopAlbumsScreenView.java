package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;

import java.util.List;

/**
 * Created by oleksandr on 22.04.16.
 */
public interface ArtistTopAlbumsScreenView extends BaseScreenView {

    void showArtistTopAlbums(List<AlbumEntity> albumEntities);
}
