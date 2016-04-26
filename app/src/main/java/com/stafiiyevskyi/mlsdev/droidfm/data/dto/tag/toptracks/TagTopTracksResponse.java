package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.toptracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopTracksResponse {

    @SerializedName("tracks")
    @Expose
    private TopTrackListByTag tracks;

    /**
     *
     * @return
     * The tracks
     */
    public TopTrackListByTag getTracks() {
        return tracks;
    }

    /**
     *
     * @param tracks
     * The tracks
     */
    public void setTracks(TopTrackListByTag tracks) {
        this.tracks = tracks;
    }

}
