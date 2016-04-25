package com.stafiiyevskyi.mlsdev.droidfm.presenter;

/**
 * Created by oleksandr on 25.04.16.
 */
public interface ArtistDetailScreenPresenter extends Presenter {

    void getArtistInformation(String artistName, String mbid);
}
