package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topalbums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopAlbumsResponse {

    @SerializedName("albums")
    @Expose
    private TopAlbumListByTag albums;

    /**
     * @return The albums
     */
    public TopAlbumListByTag getAlbums() {
        return albums;
    }

    /**
     * @param albums The albums
     */
    public void setAlbums(TopAlbumListByTag albums) {
        this.albums = albums;
    }

}
