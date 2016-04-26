package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topalbums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.ArtistTopNotFullByTag;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.AttrItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TopAlbumByTag {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("artist")
    @Expose
    private ArtistTopNotFullByTag artist;
    @SerializedName("image")
    @Expose
    private List<Image> image = new ArrayList<Image>();
    @SerializedName("@attr")
    @Expose
    private AttrItem Attr;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The mbid
     */
    public String getMbid() {
        return mbid;
    }

    /**
     * @param mbid The mbid
     */
    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The artist
     */
    public ArtistTopNotFullByTag getArtist() {
        return artist;
    }

    /**
     * @param artist The artist
     */
    public void setArtist(ArtistTopNotFullByTag artist) {
        this.artist = artist;
    }

    /**
     * @return The image
     */
    public List<Image> getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(List<Image> image) {
        this.image = image;
    }

    /**
     * @return The Attr
     */
    public AttrItem getAttr() {
        return Attr;
    }

    /**
     * @param Attr The @attr
     */
    public void setAttr(AttrItem Attr) {
        this.Attr = Attr;
    }

}