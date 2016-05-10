package com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity;

import io.realm.RealmObject;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteAlbumDAO extends RealmObject {
    private String id;
    private String album_name;
    private String artist_name;
    private String mbid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }
}
