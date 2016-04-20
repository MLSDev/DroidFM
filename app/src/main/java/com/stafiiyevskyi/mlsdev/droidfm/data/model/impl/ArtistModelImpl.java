package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMService;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.SearchArtist;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistModel;

import rx.Observable;

/**
 * Created by oleksandr on 20.04.16.
 */
public class ArtistModelImpl implements ArtistModel {

    private LastFMService service;

    public ArtistModelImpl() {
        this.service = LastFMRestClient.getService();
    }

    @Override
    public Observable<SearchArtist> searchArtistyByName(String name) {
        return service.searchArtist(name,LastFMRestClient.getAdditionalQuery());
    }
}
