package com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.lyrics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 20.05.16.
 */
public class Response {

    @SerializedName("lyrics_id")
    @Expose
    private Integer lyricsId;
    @SerializedName("text")
    @Expose
    private String text;

    /**
     *
     * @return
     * The lyricsId
     */
    public Integer getLyricsId() {
        return lyricsId;
    }

    /**
     *
     * @param lyricsId
     * The lyrics_id
     */
    public void setLyricsId(Integer lyricsId) {
        this.lyricsId = lyricsId;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

}