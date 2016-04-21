package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.ArtistTopTracks;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.SearchTracks;

import rx.Observable;

/**
 * Created by oleksandr on 21.04.16.
 */
public interface ArtistTopTracksModel {

    Observable<ArtistTopTracks> getArtistTopTracks(String artistName, String artistMbid, int page);

    Observable<SearchTracks> searchTrack(String artistName, String trackName, int page);
}
