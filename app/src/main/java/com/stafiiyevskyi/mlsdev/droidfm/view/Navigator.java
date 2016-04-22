package com.stafiiyevskyi.mlsdev.droidfm.view;

/**
 * Created by oleksandr on 20.04.16.
 */
public interface Navigator {

    void navigateToArtistsSearchScreen();

    void navigateToArtistContentDetailsScreen(String mbid, String artistName);
}
