package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ArtistTopTracks {

    @SerializedName("toptracks")
    @Expose
    private Toptracks toptracks;

    /**
     *
     * @return
     * The toptracks
     */
    public Toptracks getToptracks() {
        return toptracks;
    }

    /**
     *
     * @param toptracks
     * The toptracks
     */
    public void setToptracks(Toptracks toptracks) {
        this.toptracks = toptracks;
    }

}
