package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Streamable;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 21.04.16.
 */
public class Track {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("playcount")
    @Expose
    private String playcount;
    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("streamable")
    @Expose
    private Streamable streamable;
    @SerializedName("artist")
    @Expose
    private Artist artist;
    @SerializedName("image")
    @Expose
    private List<Image> image = new ArrayList<Image>();

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     * The duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     *
     * @return
     * The playcount
     */
    public String getPlaycount() {
        return playcount;
    }

    /**
     *
     * @param playcount
     * The playcount
     */
    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

    /**
     *
     * @return
     * The listeners
     */
    public String getListeners() {
        return listeners;
    }

    /**
     *
     * @param listeners
     * The listeners
     */
    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    /**
     *
     * @return
     * The mbid
     */
    public String getMbid() {
        return mbid;
    }

    /**
     *
     * @param mbid
     * The mbid
     */
    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The streamable
     */
    public Streamable getStreamable() {
        return streamable;
    }

    /**
     *
     * @param streamable
     * The streamable
     */
    public void setStreamable(Streamable streamable) {
        this.streamable = streamable;
    }

    /**
     *
     * @return
     * The artist
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     * The artist
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     *
     * @return
     * The image
     */
    public List<Image> getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(List<Image> image) {
        this.image = image;
    }

}
