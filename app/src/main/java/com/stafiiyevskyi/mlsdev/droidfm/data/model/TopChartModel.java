package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.TopChartArtists;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.TopChartTags;

import rx.Observable;

/**
 * Created by oleksandr on 20.04.16.
 */
public interface TopChartModel {
    Observable<TopChartArtists> getTopChartArtists(int pageNumber);

    Observable<TopChartTags> getTopChartTags(int pageNumber);
}
