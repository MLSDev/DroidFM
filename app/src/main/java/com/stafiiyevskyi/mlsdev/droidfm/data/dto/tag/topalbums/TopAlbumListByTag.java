package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topalbums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.AttrTopContentByTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TopAlbumListByTag {

    @SerializedName("album")
    @Expose
    private List<TopAlbumByTag> album = new ArrayList<TopAlbumByTag>();
    @SerializedName("@attr")
    @Expose
    private AttrTopContentByTag Attr;

    /**
     * @return The album
     */
    public List<TopAlbumByTag> getAlbum() {
        return album;
    }

    /**
     * @param album The album
     */
    public void setAlbum(List<TopAlbumByTag> album) {
        this.album = album;
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