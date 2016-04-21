package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;

import java.util.List;

/**
 * Created by oleksandr on 20.04.16.
 */
public interface ArtistsScreenView extends BaseScreenView {

    void showArtists(List<ArtistEntity> artistEntities);
}
