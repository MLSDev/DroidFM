package com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 29.04.16.
 */
public class VkTrackItemResponse {

    @SerializedName("genre")
    @Expose
    private int genre;

    @SerializedName("duration")
    @Expose
    private int duration;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("owner_id")
    @Expose
    private int ownerId;

    @SerializedName("artist")
    @Expose
    private String artist;

    @SerializedName("aid")
    @Expose
    private int aid;
    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("lurics_id")
    @Expose
    private String lyricsId;

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(String lyricsId) {
        this.lyricsId = lyricsId;
    }
}