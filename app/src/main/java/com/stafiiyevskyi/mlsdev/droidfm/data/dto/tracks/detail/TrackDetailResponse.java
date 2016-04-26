package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TrackDetailResponse {

    @SerializedName("track")
    @Expose
    private TrackDetail track;

    /**
     * @return The track
     */
    public TrackDetail getTrack() {
        return track;
    }

    /**
     * @param track The track
     */
    public void setTrack(TrackDetail track) {
        this.track = track;
    }

}
