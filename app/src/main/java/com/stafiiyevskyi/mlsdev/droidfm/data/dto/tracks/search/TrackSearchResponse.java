package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TrackSearchResponse {

    @SerializedName("results")
    @Expose
    private TrackSearchResults results;

    /**
     * @return The results
     */
    public TrackSearchResults getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(TrackSearchResults results) {
        this.results = results;
    }

}