package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;

import java.util.List;

/**
 * Created by oleksandr on 10.05.16.
 */
public interface FavoriteTrackScreenView extends BaseScreenView {

    void showFavoriteTrack(List<FavoriteTrackEntity> tracks);

    void showSuccess();
}
