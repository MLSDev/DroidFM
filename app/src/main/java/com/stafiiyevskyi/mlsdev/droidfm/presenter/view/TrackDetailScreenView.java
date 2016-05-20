package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackDetailEntity;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface TrackDetailScreenView extends BaseScreenView {

    void showTrackDetail(TrackDetailEntity track);

    void showTrackStreamUrl(String url, int trackDuration, String  lyricsId);

    void showTrackIsFavorite(boolean isFavorite);

    void showLyrics(String lyrics);

    void onSuccess();
}
