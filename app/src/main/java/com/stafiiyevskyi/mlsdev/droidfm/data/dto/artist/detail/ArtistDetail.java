package com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.Tags;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 25.04.16.
 */
public class ArtistDetail {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private List<Image> image = new ArrayList<Image>();
    @SerializedName("streamable")
    @Expose
    private String streamable;
    @SerializedName("ontour")
    @Expose
    private String ontour;
    @SerializedName("stats")
    @Expose
    private Stats stats;
    @SerializedName("similar")
    @Expose
    private Similar similar;
    @SerializedName("tags")
    @Expose
    private TagListArtistDetails tags;
    @SerializedName("bio")
    @Expose
    private Bio bio;

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

    /**
     *
     * @return
     * The ontour
     */
    public String getOntour() {
        return ontour;
    }

    /**
     *
     * @param ontour
     * The ontour
     */
    public void setOntour(String ontour) {
        this.ontour = ontour;
    }

    /**
     *
     * @return
     * The stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     *
     * @param stats
     * The stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     *
     * @return
     * The similar
     */
    public Similar getSimilar() {
        return similar;
    }

    /**
     *
     * @param similar
     * The similar
     */
    public void setSimilar(Similar similar) {
        this.similar = similar;
    }

    /**
     *
     * @return
     * The tags
     */
    public TagListArtistDetails getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     * The tags
     */
    public void setTags(TagListArtistDetails tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     * The bio
     */
    public Bio getBio() {
        return bio;
    }

    /**
     *
     * @param bio
     * The bio
     */
    public void setBio(Bio bio) {
        this.bio = bio;
    }

}