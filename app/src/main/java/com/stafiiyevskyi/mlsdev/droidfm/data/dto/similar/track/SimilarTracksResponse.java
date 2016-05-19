package com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 19.05.16.
 */
public class SimilarTracksResponse {

    @SerializedName("similartracks")
    @Expose
    private Similartracks similartracks;

    /**
     *
     * @return
     * The similartracks
     */
    public Similartracks getSimilartracks() {
        return similartracks;
    }

    /**
     *
     * @param similartracks
     * The similartracks
     */
    public void setSimilartracks(Similartracks similartracks) {
        this.similartracks = similartracks;
    }

}
