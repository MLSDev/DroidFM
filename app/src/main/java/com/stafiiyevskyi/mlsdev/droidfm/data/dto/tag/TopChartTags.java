package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 20.04.16.
 */
public class TopChartTags {

    @SerializedName("tags")
    @Expose
    private Tags tags;

    /**
     *
     * @return
     * The tags
     */
    public Tags getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     * The tags
     */
    public void setTags(Tags tags) {
        this.tags = tags;
    }

}
