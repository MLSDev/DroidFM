package com.stafiiyevskyi.mlsdev.droidfm.presenter;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface AlbumsDetailScreenPresenter extends Presenter {

    void getAlbumsDetails(String artist, String album, String mbid);
}
