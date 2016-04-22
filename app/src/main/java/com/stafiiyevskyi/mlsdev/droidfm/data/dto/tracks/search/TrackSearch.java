package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TrackSearch {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("streamable")
    @Expose
    private String streamable;
    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("image")
    @Expose
    private List<Image> image = new ArrayList<Image>();
    @SerializedName("mbid")
    @Expose
    private String mbid;

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
     * The artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     * The artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
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
    public String getStreamable() {
        return streamable;
    }

    /**
     *
     * @param streamable
     * The streamable
     */
    public void setStreamable(String streamable) {
        this.streamable = streamable;
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

}
