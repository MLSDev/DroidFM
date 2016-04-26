package com.stafiiyevskyi.mlsdev.droidfm.presenter;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface TagTopArtistsPresenter extends Presenter {
    void getTopArtists(String tag, int pageNumber);
}
