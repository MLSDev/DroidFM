package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;

import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface TagTopArtistScreenView extends BaseScreenView {

    void showTopArtist(List<ArtistEntity> artistEntities);
}
