package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.VkPopularTrackEntity;

import java.util.List;

/**
 * Created by oleksandr on 19.05.16.
 */
public interface VkPopularTracksScreenView extends BaseScreenView {

    void showVkPopularTracks(List<VkPopularTrackEntity> tracks);
}
