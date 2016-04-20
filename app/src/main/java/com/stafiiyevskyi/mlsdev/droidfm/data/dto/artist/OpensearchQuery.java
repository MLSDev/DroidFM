package com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 20.04.16.
 */
public class OpensearchQuery {

    @SerializedName("#text")
    @Expose
    private String Text;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("searchTerms")
    @Expose
    private String searchTerms;
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
     * The searchTerms
     */
    public String getSearchTerms() {
        return searchTerms;
    }

    /**
     *
     * @param searchTerms
     * The searchTerms
     */
    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
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
