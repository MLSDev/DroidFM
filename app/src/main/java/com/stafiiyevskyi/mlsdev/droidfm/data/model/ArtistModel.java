package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.SearchArtist;

import rx.Observable;

/**
 * Created by oleksandr on 20.04.16.
 */
public interface ArtistModel {

    Observable<SearchArtist> searchArtistByName(String name, int pageNumber);
}
