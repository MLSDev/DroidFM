package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topalbums.TagTopAlbumsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topartists.TagTopArtistsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.toptracks.TagTopTracksResponse;

import rx.Observable;

/**
 * Created by oleksandr on 26.04.16.
 */
public interface TagModel {

    Observable<TagTopAlbumsResponse> getTagsTopAlbums(String tag, int pageNumber);

    Observable<TagTopArtistsResponse> getTagsTopArtists(String tag, int pageNumber);

    Observable<TagTopTracksResponse> getTagsTopTracks(String tag, int pageNumber);
}
