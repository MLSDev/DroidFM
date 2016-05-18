package com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist;

/**
 * Created by oleksandr on 18.05.16.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;

public class Artist {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("match")
    @Expose
    private String match;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private List<Image> image = new ArrayList<Image>();
    @SerializedName("streamable")
    @Expose
    private String streamable;

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
     * The match
     */
    public String getMatch() {
        return match;
    }

    /**
     *
     * @param match
     * The match
     */
    public void setMatch(String match) {
        this.match = match;
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

}