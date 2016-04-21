package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 21.04.16.
 */
public class SearchTracks {

    @SerializedName("results")
    @Expose
    private ResultTrackSearch results;

    /**
     * @return The results
     */
    public ResultTrackSearch getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(ResultTrackSearch results) {
        this.results = results;
    }

}
