package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

import java.util.List;

/**
 * Created by oleksandr on 21.04.16.
 */
public class TopTrackEntity {
    private List<ImageEntity> tracksImages;
    private String trackMbid;
    private String name;
    private String artistName;


    public List<ImageEntity> getTracksImages() {
        return tracksImages;
    }

    public void setTracksImages(List<ImageEntity> tracksImages) {
        this.tracksImages = tracksImages;
    }

    public String getTrackMbid() {
        return trackMbid;
    }

    public void setTrackMbid(String trackMbid) {
        this.trackMbid = trackMbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
