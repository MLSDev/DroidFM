package com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity;

import io.realm.RealmObject;

/**
 * Created by oleksandr on 11.05.16.
 */
public class FavoriteArtistDAO extends RealmObject {

    private String id;
    private String mbid;
    private String name;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
