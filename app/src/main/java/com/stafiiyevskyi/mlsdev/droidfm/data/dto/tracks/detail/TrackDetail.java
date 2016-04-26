package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.Album;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.ArtistNotFull;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.ItemWiki;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Streamable;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TrackDetail {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("streamable")
    @Expose
    private Streamable streamable;
    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("playcount")
    @Expose
    private String playcount;
    @SerializedName("artist")
    @Expose
    private ArtistNotFull artist;
    @SerializedName("album")
    @Expose
    private Album album;
    @SerializedName("toptags")
    @Expose
    private ToptagsDetail toptags;
    @SerializedName("wiki")
    @Expose
    private ItemWiki wiki;

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
     * The artist
     */
    public ArtistNotFull getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     * The artist
     */
    public void setArtist(ArtistNotFull artist) {
        this.artist = artist;
    }

    /**
     *
     * @return
     * The album
     */
    public Album getAlbum() {
        return album;
    }

    /**
     *
     * @param album
     * The album
     */
    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     *
     * @return
     * The toptags
     */
    public ToptagsDetail getToptags() {
        return toptags;
    }

    /**
     *
     * @param toptags
     * The toptags
     */
    public void setToptags(ToptagsDetail toptags) {
        this.toptags = toptags;
    }

    /**
     *
     * @return
     * The wiki
     */
    public ItemWiki getWiki() {
        return wiki;
    }

    /**
     *
     * @param wiki
     * The wiki
     */
    public void setWiki(ItemWiki wiki) {
        this.wiki = wiki;
    }

}