package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

/**
 * Created by oleksandr on 11.05.16.
 */
public class FavoriteArtistEntity {
    private String mbid;
    private String name;
    private String image;

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
