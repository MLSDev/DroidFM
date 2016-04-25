package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistDetailEntity;

/**
 * Created by oleksandr on 25.04.16.
 */
public interface ArtistDetailScreenView extends BaseScreenView {

    void showArtistDetailInformation(ArtistDetailEntity artist);
}
