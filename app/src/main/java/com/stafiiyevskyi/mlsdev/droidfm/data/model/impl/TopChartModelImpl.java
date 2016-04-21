package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMService;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.TopChartArtists;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.TopChartTags;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TopChartModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 20.04.16.
 */
public class TopChartModelImpl implements TopChartModel {

    private LastFMService service;

    public TopChartModelImpl() {
        service = LastFMRestClient.getService();
    }

    @Override
    public Observable<TopChartArtists> getTopChartArtists(int pageNumber) {
        return service.getTopChartArtist(pageNumber, LastFMRestClient.getAdditionalQuery())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<TopChartTags> getTopChartTags(int pageNumber) {
        return service.getTopChartTags(pageNumber, LastFMRestClient.getAdditionalQuery())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
