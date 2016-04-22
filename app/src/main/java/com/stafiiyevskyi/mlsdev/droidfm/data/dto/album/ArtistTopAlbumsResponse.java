package com.stafiiyevskyi.mlsdev.droidfm.data.dto.album;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ArtistTopAlbumsResponse {

    @SerializedName("topalbums")
    @Expose
    private Topalbums topalbums;

    /**
     *
     * @return
     * The topalbums
     */
    public Topalbums getTopalbums() {
        return topalbums;
    }

    /**
     *
     * @param topalbums
     * The topalbums
     */
    public void setTopalbums(Topalbums topalbums) {
        this.topalbums = topalbums;
    }

}
