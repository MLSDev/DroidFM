package com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsTagList {

    @SerializedName("tag")
    @Expose
    private List<AlbumsTag> tag = new ArrayList<AlbumsTag>();

    /**
     *
     * @return
     * The tag
     */
    public List<AlbumsTag> getTag() {
        return tag;
    }

    /**
     *
     * @param tag
     * The tag
     */
    public void setTag(List<AlbumsTag> tag) {
        this.tag = tag;
    }

}
