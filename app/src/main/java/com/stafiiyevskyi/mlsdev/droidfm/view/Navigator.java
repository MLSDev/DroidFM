package com.stafiiyevskyi.mlsdev.droidfm.view;

/**
 * Created by oleksandr on 20.04.16.
 */
public interface Navigator {

    void navigateToArtistsSearchScreen();

    void navigateToChartsContentScreen();

    void navigateToArtistContentDetailsScreen(String mbid, String artistName, String imageUrl);

    void navigateToTopTracksScreen();

    void navigateToArtistFullDetailsScreen(String mbid);

    void setDrawerToggleEnabled();

    void setDrawerToggleNotEnabled();
}
