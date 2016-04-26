package com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.AttrItem;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Streamable;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.TracksArtist;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsTrack {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("@attr")
    @Expose
    private AttrItem Attr;
    @SerializedName("streamable")
    @Expose
    private Streamable streamable;
    @SerializedName("artist")
    @Expose
    private TracksArtist artist;

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
     * The Attr
     */
    public AttrItem getAttr() {
        return Attr;
    }

    /**
     *
     * @param Attr
     * The @attr
     */
    public void setAttr(AttrItem Attr) {
        this.Attr = Attr;
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
    public TracksArtist getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     * The artist
     */
    public void setArtist(TracksArtist artist) {
        this.artist = artist;
    }

}
