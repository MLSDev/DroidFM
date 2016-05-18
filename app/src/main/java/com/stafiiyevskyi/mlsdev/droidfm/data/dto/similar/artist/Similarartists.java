package com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 18.05.16.
 */
public class Similarartists {

    @SerializedName("artist")
    @Expose
    private List<Artist> artist = new ArrayList<>();
    @SerializedName("@attr")
    @Expose
    private AttrSimilarArtist Attr;

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
     * The Attr
     */
    public AttrSimilarArtist getAttr() {
        return Attr;
    }

    /**
     *
     * @param Attr
     * The @attr
     */
    public void setAttr(AttrSimilarArtist Attr) {
        this.Attr = Attr;
    }

}
