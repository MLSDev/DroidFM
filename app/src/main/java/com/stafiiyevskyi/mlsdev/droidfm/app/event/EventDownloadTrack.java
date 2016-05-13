package com.stafiiyevskyi.mlsdev.droidfm.app.event;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackDetailEntity;

/**
 * Created by oleksandr on 13.05.16.
 */
public class EventDownloadTrack {
    private String trackUrl;
    private TrackDetailEntity track;

    public EventDownloadTrack(String trackUrl, TrackDetailEntity track) {
        this.trackUrl = trackUrl;
        this.track = track;
    }

    public String getTrackUrl() {
        return trackUrl;
    }

    public TrackDetailEntity getTrack() {
        return track;
    }
}
