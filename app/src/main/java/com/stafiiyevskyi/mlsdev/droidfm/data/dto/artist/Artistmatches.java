package com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 20.04.16.
 */
public class Artistmatches {

    @SerializedName("artist")
    @Expose
    private List<Artist> artist = new ArrayList<>();

    /**
     *
     * @return
     * The artist
     */
    public List<Artist> getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     * The artist
     */
    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

}
