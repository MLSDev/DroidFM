package com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 18.05.16.
 */

public class AttrSimilarArtist {

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