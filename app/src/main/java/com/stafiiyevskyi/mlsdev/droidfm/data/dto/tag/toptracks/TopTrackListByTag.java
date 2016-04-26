package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.toptracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.AttrTopContentByTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TopTrackListByTag {

    @SerializedName("track")
    @Expose
    private List<TopTrackByTag> track = new ArrayList<TopTrackByTag>();
    @SerializedName("@attr")
    @Expose
    private AttrTopContentByTag Attr;

    /**
     * @return The track
     */
    public List<TopTrackByTag> getTrack() {
        return track;
    }

    /**
     * @param track The track
     */
    public void setTrack(List<TopTrackByTag> track) {
        this.track = track;
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



