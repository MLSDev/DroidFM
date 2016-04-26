package com.stafiiyevskyi.mlsdev.droidfm.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AttrItem {

    @SerializedName("rank")
    @Expose
    private String rank;

    /**
     *
     * @return
     * The rank
     */
    public String getRank() {
        return rank;
    }

    /**
     *
     * @param rank
     * The rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

}
