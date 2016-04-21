package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;

import java.util.List;

/**
 * Created by oleksandr on 21.04.16.
 */
public interface ArtistTopTracksScreenView extends BaseScreenView {

    void showTracks(List<TrackEntity> tracks);
}
