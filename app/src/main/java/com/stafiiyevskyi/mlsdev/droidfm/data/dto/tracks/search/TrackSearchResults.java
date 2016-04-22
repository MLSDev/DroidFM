package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TrackSearchResults {

    @SerializedName("opensearch:Query")
    @Expose
    private OpensearchQueryTrack opensearchQuery;
    @SerializedName("opensearch:totalResults")
    @Expose
    private String opensearchTotalResults;
    @SerializedName("opensearch:startIndex")
    @Expose
    private String opensearchStartIndex;
    @SerializedName("opensearch:itemsPerPage")
    @Expose
    private String opensearchItemsPerPage;
    @SerializedName("trackmatches")
    @Expose
    private TrackmatchesSearch trackmatches;
    @SerializedName("@attr")
    @Expose
    private AttrTrackSearch Attr;

    /**
     * @return The opensearchQuery
     */
    public OpensearchQueryTrack getOpensearchQuery() {
        return opensearchQuery;
    }

    /**
     * @param opensearchQuery The opensearch:Query
     */
    public void setOpensearchQuery(OpensearchQueryTrack opensearchQuery) {
        this.opensearchQuery = opensearchQuery;
    }

    /**
     * @return The opensearchTotalResults
     */
    public String getOpensearchTotalResults() {
        return opensearchTotalResults;
    }

    /**
     * @param opensearchTotalResults The opensearch:totalResults
     */
    public void setOpensearchTotalResults(String opensearchTotalResults) {
        this.opensearchTotalResults = opensearchTotalResults;
    }

    /**
     * @return The opensearchStartIndex
     */
    public String getOpensearchStartIndex() {
        return opensearchStartIndex;
    }

    /**
     * @param opensearchStartIndex The opensearch:startIndex
     */
    public void setOpensearchStartIndex(String opensearchStartIndex) {
        this.opensearchStartIndex = opensearchStartIndex;
    }

    /**
     * @return The opensearchItemsPerPage
     */
    public String getOpensearchItemsPerPage() {
        return opensearchItemsPerPage;
    }

    /**
     * @param opensearchItemsPerPage The opensearch:itemsPerPage
     */
    public void setOpensearchItemsPerPage(String opensearchItemsPerPage) {
        this.opensearchItemsPerPage = opensearchItemsPerPage;
    }

    /**
     * @return The trackmatches
     */
    public TrackmatchesSearch getTrackmatches() {
        return trackmatches;
    }

    /**
     * @param trackmatches The trackmatches
     */
    public void setTrackmatches(TrackmatchesSearch trackmatches) {
        this.trackmatches = trackmatches;
    }

    /**
     * @return The Attr
     */
    public AttrTrackSearch getAttr() {
        return Attr;
    }

    /**
     * @param Attr The @attr
     */
    public void setAttr(AttrTrackSearch Attr) {
        this.Attr = Attr;
    }

}