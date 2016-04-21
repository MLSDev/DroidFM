package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 21.04.16.
 */
public class TopChartTracks {

    @SerializedName("tracks")
    @Expose
    private Tracks tracks;

    /**
     *
     * @return
     * The tracks
     */
    public Tracks getTracks() {
        return tracks;
    }

    /**
     *
     * @param tracks
     * The tracks
     */
    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

}
