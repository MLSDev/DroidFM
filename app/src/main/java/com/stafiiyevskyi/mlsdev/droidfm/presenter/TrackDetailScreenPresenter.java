package com.stafiiyevskyi.mlsdev.droidfm.presenter;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackDetailEntity;

/**
 * Created by oleksandr on 27.04.16.
 */
public interface TrackDetailScreenPresenter extends Presenter {

    void getTrackDetails(String artist, String track, String mbid);

    void getTrackStreamUrl(String trackSearch);

    void addTrackToFavorite(FavoriteTrackEntity track);

    void deleteFromFavorite(FavoriteTrackEntity track);

    void isTrackFavorite(TrackDetailEntity trackDetailEntity);

    void getLyrics(String lyricsId);
}
