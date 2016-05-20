package com.stafiiyevskyi.mlsdev.droidfm.data.api;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.ArtistTopAlbumsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.SearchArtist;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.TopChartArtists;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.detail.ArtistInfoResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist.SimilarArtistsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.track.SimilarTracksResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.TopChartTags;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topalbums.TagTopAlbumsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topartists.TagTopArtistsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.toptracks.TagTopTracksResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.ArtistTopTracks;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.TopChartTracks;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail.TrackDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search.TrackSearchResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VkTrackNewResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.lyrics.LyricsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.popular.VkPopularTrackResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by oleksandr on 20.04.16.
 */
public interface LastFMService {


    // Chart Requests /////////////////////////////////////////////////////
    @GET("?method=chart.gettopartists")
    Observable<TopChartArtists> getTopChartArtist(@Query("page") int pageNumber);

    @GET("?method=chart.gettoptags")
    Observable<TopChartTags> getTopChartTags(@Query("page") int pageNumber);

    @GET("?method=chart.gettoptracks")
    Observable<TopChartTracks> getTopChartTraks(@Query("page") int pageNumber);


    // Artists Requests////////////////////////////////////////////////////
    @GET("?method=artist.search")
    Observable<SearchArtist> searchArtist(@Query("artist") String searchName
            , @Query("page") int page);

    @GET("?method=artist.gettoptracks")
    Observable<ArtistTopTracks> getArtistTopTracks(@Query("artist") String artistName
            , @Query("mbid") String mbid, @Query("page") int pageNumber);

    @GET("?method=artist.gettopalbums")
    Observable<ArtistTopAlbumsResponse> getArtistTopAlbums(@Query("artist") String artistName
            , @Query("mbid") String mbid, @Query("page") int pageNumber);

    @GET("?method=artist.getinfo")
    Observable<ArtistInfoResponse> getArtistInfo(@Query("mbid") String mbid);

    @GET("?method=artist.getsimilar")
    Observable<SimilarArtistsResponse> getSimilarArtists(@Query("artist") String artisName);

    // Track Requests /////////////////////////////////////////////////////
    @GET("?method=track.search")
    Observable<TrackSearchResponse> searchTrack(@Query("artist") String artistName, @Query("track") String trackName
            , @Query("page") int pageNumber);

    @GET("?method=track.getinfo")
    Observable<TrackDetailResponse> getTrackDetails(@Query("artist") String artist, @Query("track") String track, @Query("mbid") String mbid);

    @GET("?method=track.getsimilar")
    Observable<SimilarTracksResponse> getSimilarTracks(@Query("artist") String artisName, @Query("track") String track, @Query("limit") int limit, @Query("page") int page);

    // Tag Requests ///////////////////////////////////////////////////////

    @GET("?method=tag.gettopalbums")
    Observable<TagTopAlbumsResponse> getTagsTopAlbums(@Query("tag") String tag, @Query("page") int pageNumber);

    @GET("?method=tag.gettopartists")
    Observable<TagTopArtistsResponse> getTagsTopArtists(@Query("tag") String tag, @Query("page") int pageNumber);

    @GET("?method=tag.gettoptracks")
    Observable<TagTopTracksResponse> getTagsTopTracks(@Query("tag") String tag, @Query("page") int pageNumber);

    // Album Requests ////////////////////////////////////////////////////
    @GET("?method=album.getinfo")
    Observable<AlbumDetailResponse> getAlbumDetails(@Query("artist") String artist, @Query("album") String album, @Query("mbid") String mbid);

    // VK track request
    @GET
    Observable<VkTrackNewResponse> getTrackStream(@Url String fullUrlToTrack);

    @GET
    Observable<VkPopularTrackResponse> getVkPopularTrack(@Url String fullRequestUrl);

    @GET
    Observable<LyricsResponse> getTrackLyrics(@Url String fullRequestUrl);
}
