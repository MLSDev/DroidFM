package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMService;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.ArtistTopAlbumsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.SearchArtist;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail.ArtistInfoResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist.SimilarArtistsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.ArtistTopTracks;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 20.04.16.
 */
public class ArtistModelImpl implements ArtistModel {

    private LastFMService service;

    public ArtistModelImpl() {
        this.service = LastFMRestClient.getService();
    }

    @Override
    public Observable<SearchArtist> searchArtistByName(String name, int pageNumber) {
        return service.searchArtist(name, pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ArtistTopTracks> getArtistTopTracks(String artistName, String mbid, int pageNumber) {
        return LastFMRestClient.getService()
                .getArtistTopTracks(artistName, mbid, pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ArtistTopAlbumsResponse> getArtistTopAlbums(String artistName, String mbid, int pageNumber) {
        return LastFMRestClient.getService()
                .getArtistTopAlbums(artistName, mbid, pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ArtistInfoResponse> getArtistInfo(String mbid) {
        return LastFMRestClient.getService()
                .getArtistInfo(mbid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SimilarArtistsResponse> getSimilarArtists(String artistName) {
        return LastFMRestClient.getService()
                .getSimilarArtists(artistName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
