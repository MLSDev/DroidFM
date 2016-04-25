package com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 25.04.16.
 */
public class Links {

    @SerializedName("link")
    @Expose
    private Link link;

    /**
     *
     * @return
     * The link
     */
    public Link getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    public void setLink(Link link) {
        this.link = link;
    }

}
