package com.stafiiyevskyi.mlsdev.droidfm.presenter;

/**
 * Created by oleksandr on 21.04.16.
 */
public interface ArtistTopTracksScreenPresenter extends Presenter {

    void getArtistTopTracks(String artistName, String artistMbid, int page);

    void searchArtistTracks(String trackName, String artistMbid, int page);

}
