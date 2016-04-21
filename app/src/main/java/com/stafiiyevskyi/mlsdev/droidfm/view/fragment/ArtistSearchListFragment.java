package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.TopChartArtists;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.TopChartTracks;

import java.util.LinkedHashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 20.04.16.
 */
public class ArtistSearchListFragment extends BaseFragment {


    public static BaseFragment newInstance() {
        BaseFragment fragment = new ArtistSearchListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Map<String, String> query = new LinkedHashMap<>();
        query.put("format", "json");
        query.put("api_key", getString(R.string.last_fm_api_key));
        LastFMRestClient.getService().getTopChartArtist(1, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TopChartArtists>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error response", e.getMessage());
                    }

                    @Override
                    public void onNext(TopChartArtists topChartArtists) {
                        Log.i("TopChartsArtist", topChartArtists.getArtists().getArtist().get(0).getName());
                    }
                });

        LastFMRestClient.getService().getTopChartTraks(1, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TopChartTracks>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error response", e.getMessage());
                    }

                    @Override
                    public void onNext(TopChartTracks topChartTracks) {
                        Log.i("TopChartsTracks", topChartTracks.getTracks().getTrack().get(0).getName());
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_artists_search_screen, menu);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_artists_search_list;
    }
}
