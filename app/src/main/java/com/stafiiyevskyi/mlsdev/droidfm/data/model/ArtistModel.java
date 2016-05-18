package com.stafiiyevskyi.mlsdev.droidfm.data.model;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.ArtistTopAlbumsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.SearchArtist;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail.ArtistInfoResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist.SimilarArtistsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.ArtistTopTracks;

import rx.Observable;

/**
 * Created by oleksandr on 20.04.16.
 */
public interface ArtistModel {

    Observable<SearchArtist> searchArtistByName(String name, int pageNumber);

    Observable<ArtistTopTracks> getArtistTopTracks(String artistName, String mbid, int pageNumber);

    Observable<ArtistTopAlbumsResponse> getArtistTopAlbums(String artistName, String mbid, int pageNumber);

    Observable<ArtistInfoResponse> getArtistInfo(String mbid);

    Observable<SimilarArtistsResponse> getSimilarArtists(String artistName);

}
