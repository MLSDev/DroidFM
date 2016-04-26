package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail.TrackDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search.TrackSearchResponse;

import rx.Observable;

/**
 * Created by oleksandr on 25.04.16.
 */
public interface TrackModel {

    Observable<TrackSearchResponse> searchTrack(String artistName, String trackName, int page);

    Observable<TrackDetailResponse> getTrackDetail(String mbid);

}
