package com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 25.04.16.
 */
public class ArtistInfoResponse {

    @SerializedName("artist")
    @Expose
    private ArtistDetail artist;

    /**
     *
     * @return
     * The artist
     */
    public ArtistDetail getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     * The artist
     */
    public void setArtist(ArtistDetail artist) {
        this.artist = artist;
    }

}
