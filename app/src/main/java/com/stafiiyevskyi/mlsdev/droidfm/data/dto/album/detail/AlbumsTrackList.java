package com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsTrackList {

    @SerializedName("track")
    @Expose
    private List<AlbumsTrack> track = new ArrayList<AlbumsTrack>();

    /**
     * @return The track
     */
    public List<AlbumsTrack> getTrack() {
        return track;
    }

    /**
     * @param track The track
     */
    public void setTrack(List<AlbumsTrack> track) {
        this.track = track;
    }

}
