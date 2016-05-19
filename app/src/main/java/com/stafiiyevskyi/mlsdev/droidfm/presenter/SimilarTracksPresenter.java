package com.stafiiyevskyi.mlsdev.droidfm.presenter;

/**
 * Created by oleksandr on 19.05.16.
 */
public interface SimilarTracksPresenter extends Presenter {

    void getSimilarTracks(String artist, String track, int page);
}
