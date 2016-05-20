package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VkTrackNewResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.lyrics.LyricsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.popular.VkPopularTrackResponse;

import rx.Observable;

/**
 * Created by oleksandr on 29.04.16.
 */
public interface VKTrackModel {

    Observable<VkTrackNewResponse> getVKTrack(String trackSearch);

    Observable<VkPopularTrackResponse> getVkPopularTracks(int genreId, int offset, int count);

    Observable<LyricsResponse> getTrackLyrics(String lyricsId);
}
