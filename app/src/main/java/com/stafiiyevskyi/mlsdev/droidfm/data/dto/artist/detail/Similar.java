package com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 25.04.16.
 */
public class Similar {

    @SerializedName("artist")
    @Expose
    private List<SimilarArtist> artist = new ArrayList<SimilarArtist>();

    /**
     *
     * @return
     * The artist
     */
    public List<SimilarArtist> getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     * The artist
     */
    public void setArtist(List<SimilarArtist> artist) {
        this.artist = artist;
    }

}
