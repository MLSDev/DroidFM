package com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumDetailResponse {

    @SerializedName("album")
    @Expose
    private AlbumDetail album;

    /**
     * @return The album
     */
    public AlbumDetail getAlbum() {
        return album;
    }

    /**
     * @param album The album
     */
    public void setAlbum(AlbumDetail album) {
        this.album = album;
    }

}