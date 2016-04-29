package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VKTrackResponse;

import rx.Observable;

/**
 * Created by oleksandr on 29.04.16.
 */
public interface VKTrackModel {

    Observable<VKTrackResponse> getVKTrack(String trackSearch);
}
