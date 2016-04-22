package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

import java.util.List;

/**
 * Created by oleksandr on 21.04.16.
 */
public class AlbumEntity {
    private String mName;
    private int mPlaycount;
    private String mMbid;
    private String mArtistName;
    private List<ImageEntity> mImage;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getPlaycount() {
        return mPlaycount;
    }

    public void setPlaycount(int playcount) {
        this.mPlaycount = playcount;
    }

    public String getMbid() {
        return mMbid;
    }

    public void setMbid(String mMbid) {
        this.mMbid = mMbid;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    public List<ImageEntity> getImage() {
        return mImage;
    }

    public void setImage(List<ImageEntity> mImage) {
        this.mImage = mImage;
    }
}
