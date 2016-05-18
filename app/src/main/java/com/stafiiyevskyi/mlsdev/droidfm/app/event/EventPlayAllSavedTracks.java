package com.stafiiyevskyi.mlsdev.droidfm.app.event;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.SavedTrackEntity;

import java.util.List;

/**
 * Created by oleksandr on 18.05.16.
 */
public class EventPlayAllSavedTracks {
    private List<SavedTrackEntity> tracks;

    public List<SavedTrackEntity> getTracks() {
        return tracks;
    }

    public void setTracks(List<SavedTrackEntity> tracks) {
        this.tracks = tracks;
    }
}
