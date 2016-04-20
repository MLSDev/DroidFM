package com.stafiiyevskyi.mlsdev.droidfm.presenter;

/**
 * Created by oleksandr on 20.04.16.
 */
public interface ArtistsScreenPresenter extends Presenter {

    void searchArtist(String artistName, int page);

    void getTopArtists(int page);
}
