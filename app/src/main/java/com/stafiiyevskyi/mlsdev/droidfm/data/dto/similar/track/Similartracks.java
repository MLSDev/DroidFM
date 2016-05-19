package com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 19.05.16.
 */
public class Similartracks {

    @SerializedName("track")
    @Expose
    private List<Track> track = new ArrayList<>();
    @SerializedName("@attr")
    @Expose
    private SimilarTrackAttr Attr;

    /**
     * @return The track
     */
    public List<Track> getTrack() {
        return track;
    }

    /**
     * @param track The track
     */
    public void setTrack(List<Track> track) {
        this.track = track;
    }

    /**
     * @return The Attr
     */
    public SimilarTrackAttr getAttr() {
        return Attr;
    }

    /**
     * @param Attr The @attr
     */
    public void setAttr(SimilarTrackAttr Attr) {
        this.Attr = Attr;
    }

}
