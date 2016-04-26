package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;

import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface TagTopAlbumsScreenView extends BaseScreenView {
    void showTopAlbums(List<AlbumEntity> albumEntities);
}
