package com.stafiiyevskyi.mlsdev.droidfm.presenter;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;

import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface AlbumsDetailScreenPresenter extends Presenter {

    void getAlbumsDetails(String artist, String album, String mbid);
}
