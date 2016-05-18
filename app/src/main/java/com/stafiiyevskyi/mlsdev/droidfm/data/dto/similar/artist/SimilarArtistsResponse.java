package com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 18.05.16.
 */
public class SimilarArtistsResponse {

    @SerializedName("similarartists")
    @Expose
    private Similarartists similarartists;

    /**
     *
     * @return
     * The similarartists
     */
    public Similarartists getSimilarartists() {
        return similarartists;
    }

    /**
     *
     * @param similarartists
     * The similarartists
     */
    public void setSimilarartists(Similarartists similarartists) {
        this.similarartists = similarartists;
    }

}
