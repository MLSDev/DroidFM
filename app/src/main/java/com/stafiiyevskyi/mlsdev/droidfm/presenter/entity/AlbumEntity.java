package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

import java.util.List;

/**
 * Created by oleksandr on 21.04.16.
 */
public class AlbumEntity {
    private String name;
    private int playcount;
    private String mbid;
    private String artistName;
    private List<ImageEntity> image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mMbid) {
        this.mbid = mMbid;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String mArtistName) {
        this.artistName = mArtistName;
    }

    public List<ImageEntity> getImage() {
        return image;
    }

    public void setImage(List<ImageEntity> mImage) {
        this.image = mImage;
    }
}
