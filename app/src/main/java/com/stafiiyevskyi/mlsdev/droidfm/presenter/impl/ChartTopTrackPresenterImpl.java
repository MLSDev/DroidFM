package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.TopChartModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TopChartModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ChartTopTracksScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.chart.ChartTopTrackListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track.SearchTracksListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ChartTopTracksScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ChartTopTrackPresenterImpl extends BasePresenter implements ChartTopTracksScreenPresenter {

    private ChartTopTracksScreenView view;
    private TopChartModel topChartModel;
    private TrackModel trackModel;

    public ChartTopTrackPresenterImpl(ChartTopTracksScreenView view) {
        this.view = view;
        topChartModel = new TopChartModelImpl();
        trackModel = new TrackModelImpl();
    }

    @Override
    public void getChartTopTracks(int pageNumber) {
        Subscription subscription = topChartModel.getTopChartTracks(pageNumber)
                .map(new ChartTopTrackListMapper())
                .subscribe(new Observer<List<TopTrackEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TopTrackEntity> trackEntities) {
                        view.showChartTopTracks(trackEntities);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void searchTracks(String track, int page) {
        Subscription subscription = trackModel.searchTrack("", track, page)
                .map(new SearchTracksListMapper())
                .subscribe(new Observer<List<TopTrackEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TopTrackEntity> trackEntities) {
                        view.showChartTopTracks(trackEntities);
                    }
                });
        addSubscription(subscription);
    }
}
