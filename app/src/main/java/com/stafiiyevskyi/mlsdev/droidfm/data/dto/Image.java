package com.stafiiyevskyi.mlsdev.droidfm.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 20.04.16.
 */
public class Image {

    @SerializedName("#text")
    @Expose
    private String Text;
    @SerializedName("size")
    @Expose
    private String size;

    /**
     *
     * @return
     * The Text
     */
    public String getText() {
        return Text;
    }

    /**
     *
     * @param Text
     * The #text
     */
    public void setText(String Text) {
        this.Text = Text;
    }

    /**
     *
     * @return
     * The size
     */
    public String getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(String size) {
        this.size = size;
    }

}
