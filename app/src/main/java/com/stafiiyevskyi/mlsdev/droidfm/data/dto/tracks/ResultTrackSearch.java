package com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.AttrResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.OpensearchQuery;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ResultTrackSearch {

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
    @SerializedName("trackmatches")
    @Expose
    private Trackmatches trackmatches;
    @SerializedName("@attr")
    @Expose
    private AttrResponse Attr;

    /**
     * @return The opensearchQuery
     */
    public OpensearchQuery getOpensearchQuery() {
        return opensearchQuery;
    }

    /**
     * @param opensearchQuery The opensearch:Query
     */
    public void setOpensearchQuery(OpensearchQuery opensearchQuery) {
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
    public Trackmatches getTrackmatches() {
        return trackmatches;
    }

    /**
     * @param trackmatches The trackmatches
     */
    public void setTrackmatches(Trackmatches trackmatches) {
        this.trackmatches = trackmatches;
    }

    /**
     * @return The Attr
     */
    public AttrResponse getAttr() {
        return Attr;
    }

    /**
     * @param Attr The @attr
     */
    public void setAttr(AttrResponse Attr) {
        this.Attr = Attr;
    }

}
