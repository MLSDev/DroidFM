package com.stafiiyevskyi.mlsdev.droidfm.data.api;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.SearchArtist;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.TopChartArtists;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.TopChartTags;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.ArtistTopTracks;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.SearchTracks;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.TopChartTracks;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by oleksandr on 20.04.16.
 */
public interface LastFMService {


    // Chart Requests
    @GET("?method=chart.gettopartists")
    Observable<TopChartArtists> getTopChartArtist(@Query("page") int pageNumber
            , @QueryMap Map<String, String> queryAdditional);

    @GET("?method=chart.gettoptags")
    Observable<TopChartTags> getTopChartTags(@Query("page") int pageNumber
            , @QueryMap Map<String, String> queryAdditional);

    @GET("?method=chart.gettoptracks")
    Observable<TopChartTracks> getTopChartTraks(@Query("page") int pageNumber
            , @QueryMap Map<String, String> queryAdditional);


    // Artists Requests
    @GET("?method=artist.search")
    Observable<SearchArtist> searchArtist(@Query("artist") String searchName
            , @Query("page") int page, @QueryMap Map<String, String> queryAdditional);

    @GET("?method=artist.gettoptracks")
    Observable<ArtistTopTracks> getArtistTopTracks(@Query("artist") String artistName
            , @Query("mbid") String mbid, @Query("page") int pageNumber, @QueryMap Map<String, String> queryAdditional);


    // Track Requests
    @GET("?method=track.search")
    Observable<SearchTracks> searchTrack(@Query("artist") String artistName, @Query("track") String trackName
            , @Query("page") int pageNumber, @QueryMap Map<String, String> queryAdditional);

}
