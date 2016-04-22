package com.stafiiyevskyi.mlsdev.droidfm.presenter;

/**
 * Created by oleksandr on 22.04.16.
 */
public interface ArtistTopAlbumsPresenter extends Presenter {
    void getArtistTopAlbums(String artistName, String artistMbid, int page);
}
