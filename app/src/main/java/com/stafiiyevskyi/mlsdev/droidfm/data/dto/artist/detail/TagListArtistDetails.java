package com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 25.04.16.
 */
public class TagListArtistDetails{

    @SerializedName("tag")
    @Expose
    private List<TagArtistDetails> tag = new ArrayList<TagArtistDetails>();

    /**
     *
     * @return
     * The tag
     */
    public List<TagArtistDetails> getTag() {
        return tag;
    }

    /**
     *
     * @param tag
     * The tag
     */
    public void setTag(List<TagArtistDetails> tag) {
        this.tag = tag;
    }

}