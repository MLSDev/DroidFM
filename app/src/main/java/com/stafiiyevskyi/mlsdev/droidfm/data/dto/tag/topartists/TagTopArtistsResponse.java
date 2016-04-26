package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topartists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopArtistsResponse {

    @SerializedName("topartists")
    @Expose
    private TopartistsByTag topartists;

    /**
     *
     * @return
     * The topartists
     */
    public TopartistsByTag getTopartists() {
        return topartists;
    }

    /**
     *
     * @param topartists
     * The topartists
     */
    public void setTopartists(TopartistsByTag topartists) {
        this.topartists = topartists;
    }

}
