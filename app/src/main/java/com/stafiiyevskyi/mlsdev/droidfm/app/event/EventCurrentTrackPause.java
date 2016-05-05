package com.stafiiyevskyi.mlsdev.droidfm.app.event;

import com.stafiiyevskyi.mlsdev.droidfm.app.player.TrackPlayerEntity;

/**
 * Created by oleksandr on 04.05.16.
 */
public class EventCurrentTrackPause {
    private TrackPlayerEntity track;

    public EventCurrentTrackPause(TrackPlayerEntity track) {
        this.track = track;
    }

    public TrackPlayerEntity getTrack() {
        return track;
    }

    public void setTrack(TrackPlayerEntity track) {
        this.track = track;
    }
}
