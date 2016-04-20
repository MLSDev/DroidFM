package com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 20.04.16.
 */
public class Results {

    @SerializedName("opensearch:Query")
    @Expose
    private OpensearchQuery opensearchQuery;
    @SerializedName("opensearch:totalResults")
    @Expose
    private String opensearchTotalResults;
    @SerializedName("opensearch:startIndex")
    @Expose
    private String opensearchStartIndex;
    @SerializedName("opensearch:itemsPerPage")
    @Expose
    private String opensearchItemsPerPage;
    @SerializedName("artistmatches")
    @Expose
    private Artistmatches artistmatches;
    @SerializedName("@attr")
    @Expose
    private com.stafiiyevskyi.mlsdev.droidfm.data.dto.Attr Attr;

    /**
     *
     * @return
     * The opensearchQuery
     */
    public OpensearchQuery getOpensearchQuery() {
        return opensearchQuery;
    }

    /**
     *
     * @param opensearchQuery
     * The opensearch:Query
     */
    public void setOpensearchQuery(OpensearchQuery opensearchQuery) {
        this.opensearchQuery = opensearchQuery;
    }

    /**
     *
     * @return
     * The opensearchTotalResults
     */
    public String getOpensearchTotalResults() {
        return opensearchTotalResults;
    }

    /**
     *
     * @param opensearchTotalResults
     * The opensearch:totalResults
     */
    public void setOpensearchTotalResults(String opensearchTotalResults) {
        this.opensearchTotalResults = opensearchTotalResults;
    }

    /**
     *
     * @return
     * The opensearchStartIndex
     */
    public String getOpensearchStartIndex() {
        return opensearchStartIndex;
    }

    /**
     *
     * @param opensearchStartIndex
     * The opensearch:startIndex
     */
    public void setOpensearchStartIndex(String opensearchStartIndex) {
        this.opensearchStartIndex = opensearchStartIndex;
    }

    /**
     *
     * @return
     * The opensearchItemsPerPage
     */
    public String getOpensearchItemsPerPage() {
        return opensearchItemsPerPage;
    }

    /**
     *
     * @param opensearchItemsPerPage
     * The opensearch:itemsPerPage
     */
    public void setOpensearchItemsPerPage(String opensearchItemsPerPage) {
        this.opensearchItemsPerPage = opensearchItemsPerPage;
    }

    /**
     *
     * @return
     * The artistmatches
     */
    public Artistmatches getArtistmatches() {
        return artistmatches;
    }

    /**
     *
     * @param artistmatches
     * The artistmatches
     */
    public void setArtistmatches(Artistmatches artistmatches) {
        this.artistmatches = artistmatches;
    }

    /**
     *
     * @return
     * The Attr
     */
    public com.stafiiyevskyi.mlsdev.droidfm.data.dto.Attr getAttr() {
        return Attr;
    }

    /**
     *
     * @param Attr
     * The @attr
     */
    public void setAttr(com.stafiiyevskyi.mlsdev.droidfm.data.dto.Attr Attr) {
        this.Attr = Attr;
    }

}
