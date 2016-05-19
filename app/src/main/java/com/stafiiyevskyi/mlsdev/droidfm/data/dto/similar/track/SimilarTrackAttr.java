package com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 19.05.16.
 */

public class SimilarTrackAttr {

    @SerializedName("artist")
    @Expose
    private String artist;

    /**
     * @return The artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @param artist The artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

}