package com.stafiiyevskyi.mlsdev.droidfm.data.dao.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by oleksandr on 12.05.16.
 */
public class PlaylistDAO extends RealmObject {
    @Required
    private String id;
    private String name;
    private RealmList<FavoriteTrackDAO> tracks;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<FavoriteTrackDAO> getTracks() {
        return tracks;
    }

    public void setTracks(RealmList<FavoriteTrackDAO> tracks) {
        this.tracks = tracks;
    }
}
