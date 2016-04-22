package com.stafiiyevskyi.mlsdev.droidfm.data.dto.album;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 22.04.16.
 */
public class Topalbums {

    @SerializedName("album")
    @Expose
    private List<Album> album = new ArrayList<Album>();
    @SerializedName("@attr")
    @Expose
    private AttrAlbum Attr;

    /**
     * @return The album
     */
    public List<Album> getAlbum() {
        return album;
    }

    /**
     * @param album The album
     */
    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    /**
     * @return The Attr
     */
    public AttrAlbum getAttr() {
        return Attr;
    }

    /**
     * @param Attr The @attr
     */
    public void setAttr(AttrAlbum Attr) {
        this.Attr = Attr;
    }

}