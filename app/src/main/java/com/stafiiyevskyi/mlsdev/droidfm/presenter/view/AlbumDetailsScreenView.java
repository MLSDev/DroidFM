package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.app.player.TrackPlayerEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumsDetailEntity;

import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface AlbumDetailsScreenView extends BaseScreenView {

    void showAlbumsDetails(AlbumsDetailEntity album);
}
