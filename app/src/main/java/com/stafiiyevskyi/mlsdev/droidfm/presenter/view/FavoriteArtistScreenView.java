package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;

import java.util.List;

/**
 * Created by oleksandr on 11.05.16.
 */
public interface FavoriteArtistScreenView extends BaseScreenView {

    void showFavoriteArtists(List<FavoriteArtistEntity> artists);

    void showSuccess();
}
