package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteAlbumEntity {
    private String mbid;
    private String albumName;
    private String artistName;

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
