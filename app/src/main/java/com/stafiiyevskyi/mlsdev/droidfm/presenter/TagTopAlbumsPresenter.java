package com.stafiiyevskyi.mlsdev.droidfm.presenter;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface TagTopAlbumsPresenter extends Presenter {

    void getTopAlbums(String tag, int pageNumber);
}
