package com.stafiiyevskyi.mlsdev.droidfm.presenter;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;

/**
 * Created by oleksandr on 11.05.16.
 */
public interface ArtistContentDetailsScreenPresenter extends Presenter {
    void addArtistToFavorite(FavoriteArtistEntity artist);

    void deleteFromFavorite(FavoriteArtistEntity artist);

    void isArtistFavorite(FavoriteArtistEntity artist);
}
