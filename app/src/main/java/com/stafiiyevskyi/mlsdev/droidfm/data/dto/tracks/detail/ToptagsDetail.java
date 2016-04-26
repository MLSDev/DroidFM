package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.ItemsTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public class ToptagsDetail {

    @SerializedName("tag")
    @Expose
    private List<ItemsTag> tag = new ArrayList<ItemsTag>();

    /**
     *
     * @return
     * The tag
     */
    public List<ItemsTag> getTag() {
        return tag;
    }

    /**
     *
     * @param tag
     * The tag
     */
    public void setTag(List<ItemsTag> tag) {
        this.tag = tag;
    }

}
