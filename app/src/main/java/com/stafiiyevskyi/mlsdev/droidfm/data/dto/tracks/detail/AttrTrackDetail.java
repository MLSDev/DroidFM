package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AttrTrackDetail {

    @SerializedName("position")
    @Expose
    private String position;

    /**
     *
     * @return
     * The position
     */
    public String getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * The position
     */
    public void setPosition(String position) {
        this.position = position;
    }

}
