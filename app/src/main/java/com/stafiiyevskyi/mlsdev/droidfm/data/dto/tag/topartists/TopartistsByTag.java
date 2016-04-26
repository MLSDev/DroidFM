package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topartists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.AttrTopContentByTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TopartistsByTag {

    @SerializedName("artist")
    @Expose
    private List<TopArtistByTag> artist = new ArrayList<TopArtistByTag>();
    @SerializedName("@attr")
    @Expose
    private AttrTopContentByTag Attr;

    /**
     * @return The artist
     */
    public List<TopArtistByTag> getArtist() {
        return artist;
    }

    /**
     * @param artist The artist
     */
    public void setArtist(List<TopArtistByTag> artist) {
        this.artist = artist;
    }

    /**
     * @return The Attr
     */
    public AttrTopContentByTag getAttr() {
        return Attr;
    }

    /**
     * @param Attr The @attr
     */
    public void setAttr(AttrTopContentByTag Attr) {
        this.Attr = Attr;
    }

}
