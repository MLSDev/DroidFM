package com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.AttrResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 20.04.16.
 */
public class Artists {

    @SerializedName("artist")
    @Expose
    private List<Artist> artist = new ArrayList<Artist>();
    @SerializedName("@attr")
    @Expose
    private AttrResponse Attr;

    /**
     *
     * @return
     * The artist
     */
    public List<Artist> getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     * The artist
     */
    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

    /**
     *
     * @return
     * The AttrResponse
     */
    public AttrResponse getAttr() {
        return Attr;
    }

    /**
     *
     * @param Attr
     * The @attr
     */
    public void setAttr(AttrResponse Attr) {
        this.Attr = Attr;
    }

}
