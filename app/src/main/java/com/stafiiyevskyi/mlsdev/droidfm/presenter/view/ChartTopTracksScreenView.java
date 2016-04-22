package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.List;

/**
 * Created by oleksandr on 22.04.16.
 */
public interface ChartTopTracksScreenView extends BaseScreenView {
    void showChartTopTracks(List<TopTrackEntity> trackEntities);
}
