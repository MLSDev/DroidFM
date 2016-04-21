package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 21.04.16.
 */
public class Trackmatches {

    @SerializedName("track")
    @Expose
    private List<Track> track = new ArrayList<Track>();

    /**
     *
     * @return
     * The track
     */
    public List<Track> getTrack() {
        return track;
    }

    /**
     *
     * @param track
     * The track
     */
    public void setTrack(List<Track> track) {
        this.track = track;
    }

}
