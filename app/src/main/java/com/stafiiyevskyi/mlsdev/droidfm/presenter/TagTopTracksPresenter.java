package com.stafiiyevskyi.mlsdev.droidfm.presenter;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface TagTopTracksPresenter extends Presenter {
    void getTopTracks(String tag, int pageNumber);

    void searchTracks(String trackName, int pageNumber);
}
