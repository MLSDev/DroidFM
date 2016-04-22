package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 22.04.16.
 */
public class OpensearchQueryTrack  {

    @SerializedName("#text")
    @Expose
    private String Text;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("startPage")
    @Expose
    private String startPage;

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
     * The role
     */
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     * The role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     *
     * @return
     * The startPage
     */
    public String getStartPage() {
        return startPage;
    }

    /**
     *
     * @param startPage
     * The startPage
     */
    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

}
