package com.stafiiyevskyi.mlsdev.droidfm.presenter;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;

/**
 * Created by oleksandr on 10.05.16.
 */
public interface FavoriteTracksScreenPresenter extends Presenter {

    void getFavoritesTrack();

    void deleteFromFavorites(FavoriteTrackEntity track);
}
