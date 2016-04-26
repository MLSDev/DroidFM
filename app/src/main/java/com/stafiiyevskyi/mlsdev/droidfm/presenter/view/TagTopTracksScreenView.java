package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface TagTopTracksScreenView extends BaseScreenView {
    void showTopArtists(List<TopTrackEntity> topTrackEntities);
}
