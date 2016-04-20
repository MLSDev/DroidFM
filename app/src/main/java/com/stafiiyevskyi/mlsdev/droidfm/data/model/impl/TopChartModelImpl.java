package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMService;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.TopChartArtists;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.TopChartTags;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TopChartModel;

import java.util.LinkedHashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 20.04.16.
 */
public class TopChartModelImpl implements TopChartModel {

    private LastFMService service;
    private Map<String, String> query;

    public TopChartModelImpl() {
        service = LastFMRestClient.getService();
        query = new LinkedHashMap<>();
        query.put("format", "json");
        query.put("api_key", "c0cca0938e628d1582474f036955fcfa");
    }

    @Override
    public Observable<TopChartArtists> getTopChartArtists(int pageNumber) {
        return service.getTopChartArtist(pageNumber, query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<TopChartTags> getTopChartTags(int pageNumber) {
        return service.getTopChartTags(pageNumber, query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
