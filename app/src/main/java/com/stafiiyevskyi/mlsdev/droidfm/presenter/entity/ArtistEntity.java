package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

import java.util.List;

/**
 * Created by oleksandr on 20.04.16.
 */
public class ArtistEntity {
    private String artistName;
    private String artisMbid;
    private List<ImageEntity> artistImages;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String mArtistName) {
        this.artistName = mArtistName;
    }

    public String getArtisMbid() {
        return artisMbid;
    }

    public void setArtisMbid(String mArtisMbid) {
        this.artisMbid = mArtisMbid;
    }

    public List<ImageEntity> getArtistImages() {
        return artistImages;
    }

    public void setArtistImages(List<ImageEntity> mArtistImages) {
        this.artistImages = mArtistImages;
    }
}
