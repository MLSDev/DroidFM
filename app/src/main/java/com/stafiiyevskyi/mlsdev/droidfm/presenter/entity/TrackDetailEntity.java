package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TrackDetailEntity {

    private String name;
    private String artistName;
    private String content;
    private String published;
    private String duration;
    private String mbid;
    private List<TagWithUrlEntity> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public List<TagWithUrlEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagWithUrlEntity> tags) {
        this.tags = tags;
    }
}
