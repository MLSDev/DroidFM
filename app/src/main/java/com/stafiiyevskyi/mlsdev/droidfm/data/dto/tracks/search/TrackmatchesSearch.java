package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TrackmatchesSearch {

    @SerializedName("track")
    @Expose
    private List<TrackSearch> track = new ArrayList<TrackSearch>();

    /**
     *
     * @return
     * The track
     */
    public List<TrackSearch> getTrack() {
        return track;
    }

    /**
     *
     * @param track
     * The track
     */
    public void setTrack(List<TrackSearch> track) {
        this.track = track;
    }

}
