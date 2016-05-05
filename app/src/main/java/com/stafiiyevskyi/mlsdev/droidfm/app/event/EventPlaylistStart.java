package com.stafiiyevskyi.mlsdev.droidfm.app.event;

import com.stafiiyevskyi.mlsdev.droidfm.app.player.TrackPlayerEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;

import java.util.List;

/**
 * Created by oleksandr on 05.05.16.
 */
public class EventPlaylistStart {
    private List<TrackEntity> mData;

    public List<TrackEntity> getData() {
        return mData;
    }

    public void setData(List<TrackEntity> mData) {
        this.mData = mData;
    }
}
