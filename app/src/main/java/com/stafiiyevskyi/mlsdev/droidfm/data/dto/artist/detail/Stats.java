package com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 25.04.16.
 */
public class Stats {

    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("playcount")
    @Expose
    private String playcount;

    /**
     *
     * @return
     * The listeners
     */
    public String getListeners() {
        return listeners;
    }

    /**
     *
     * @param listeners
     * The listeners
     */
    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    /**
     *
     * @return
     * The playcount
     */
    public String getPlaycount() {
        return playcount;
    }

    /**
     *
     * @param playcount
     * The playcount
     */
    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

}
