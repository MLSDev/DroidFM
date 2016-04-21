package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

import java.util.List;

/**
 * Created by oleksandr on 20.04.16.
 */
public class ArtistEntity {
    private String mArtistName;
    private String mArtisMbid;
    private List<ImageEntity> mArtistImages;

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    public String getArtisMbid() {
        return mArtisMbid;
    }

    public void setArtisMbid(String mArtisMbid) {
        this.mArtisMbid = mArtisMbid;
    }

    public List<ImageEntity> getArtistImages() {
        return mArtistImages;
    }

    public void setArtistImages(List<ImageEntity> mArtistImages) {
        this.mArtistImages = mArtistImages;
    }
}
