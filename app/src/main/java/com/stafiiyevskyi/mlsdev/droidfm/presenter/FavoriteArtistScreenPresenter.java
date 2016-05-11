package com.stafiiyevskyi.mlsdev.droidfm.presenter;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;

/**
 * Created by oleksandr on 11.05.16.
 */
public interface FavoriteArtistScreenPresenter extends Presenter {
    void getFavoriteArtists();

    void deleteFromFavorites(FavoriteArtistEntity artist);
}
