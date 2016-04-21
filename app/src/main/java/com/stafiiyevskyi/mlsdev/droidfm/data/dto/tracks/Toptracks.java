package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.AttrResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 21.04.16.
 */
public class Toptracks {

    @SerializedName("track")
    @Expose
    private List<ArtistTopTrack> track = new ArrayList<ArtistTopTrack>();
    @SerializedName("@attr")
    @Expose
    private AttrResponse Attr;

    /**
     * @return The track
     */
    public List<ArtistTopTrack> getTrack() {
        return track;
    }

    /**
     * @param track The track
     */
    public void setTrack(List<ArtistTopTrack> track) {
        this.track = track;
    }

    /**
     * @return The Attr
     */
    public AttrResponse getAttr() {
        return Attr;
    }

    /**
     * @param Attr The @attr
     */
    public void setAttr(AttrResponse Attr) {
        this.Attr = Attr;
    }

}
