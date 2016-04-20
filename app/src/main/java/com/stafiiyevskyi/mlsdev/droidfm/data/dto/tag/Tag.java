package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 20.04.16.
 */
public class Tag {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("reach")
    @Expose
    private String reach;
    @SerializedName("taggings")
    @Expose
    private String taggings;
    @SerializedName("streamable")
    @Expose
    private String streamable;
    @SerializedName("wiki")
    @Expose
    private Wiki wiki;

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
     * The reach
     */
    public String getReach() {
        return reach;
    }

    /**
     *
     * @param reach
     * The reach
     */
    public void setReach(String reach) {
        this.reach = reach;
    }

    /**
     *
     * @return
     * The taggings
     */
    public String getTaggings() {
        return taggings;
    }

    /**
     *
     * @param taggings
     * The taggings
     */
    public void setTaggings(String taggings) {
        this.taggings = taggings;
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
     * The wiki
     */
    public Wiki getWiki() {
        return wiki;
    }

    /**
     *
     * @param wiki
     * The wiki
     */
    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }

}
