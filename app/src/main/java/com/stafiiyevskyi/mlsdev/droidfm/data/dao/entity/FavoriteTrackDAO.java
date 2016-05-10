package com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity;

import io.realm.RealmObject;

/**
 * Created by oleksandr on 06.05.16.
 */
public class FavoriteTrackDAO extends RealmObject {
    private String id;
    private String track_name;
    private String artist_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrack_name() {
        return track_name;
    }

    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }
}
