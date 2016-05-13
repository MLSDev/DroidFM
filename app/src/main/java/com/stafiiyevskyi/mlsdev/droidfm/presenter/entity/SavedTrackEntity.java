package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

import android.net.Uri;

/**
 * Created by oleksandr on 13.05.16.
 */
public class SavedTrackEntity {
    private String name;
    private String path;
    private Uri uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
