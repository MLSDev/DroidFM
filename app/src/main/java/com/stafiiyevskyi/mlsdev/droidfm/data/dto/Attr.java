package com.stafiiyevskyi.mlsdev.droidfm.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 20.04.16.
 */
public class Attr {

    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("perPage")
    @Expose
    private String perPage;
    @SerializedName("totalPages")
    @Expose
    private String totalPages;
    @SerializedName("total")
    @Expose
    private String total;

    /**
     *
     * @return
     * The page
     */
    public String getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The perPage
     */
    public String getPerPage() {
        return perPage;
    }

    /**
     *
     * @param perPage
     * The perPage
     */
    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }

    /**
     *
     * @return
     * The totalPages
     */
    public String getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     * The totalPages
     */
    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    /**
     *
     * @return
     * The total
     */
    public String getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

}