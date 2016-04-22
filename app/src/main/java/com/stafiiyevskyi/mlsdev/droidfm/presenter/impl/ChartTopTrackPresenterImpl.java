package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.TopChartModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TopChartModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ChartTopTracksScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.ChartTopTrackListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ChartTopTracksScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ChartTopTrackPresenterImpl extends BasePresenter implements ChartTopTracksScreenPresenter {

    private ChartTopTracksScreenView mView;
    private TopChartModel mModel;

    public ChartTopTrackPresenterImpl(ChartTopTracksScreenView mView) {
        this.mView = mView;
        mModel = new TopChartModelImpl();
    }

    @Override
    public void getChartTopTracks(int pageNumber) {
        Subscription subscription = mModel.getTopChartTracks(pageNumber)
                .map(new ChartTopTrackListMapper())
                .subscribe(new Observer<List<TopTrackEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TopTrackEntity> trackEntities) {
                        mView.showChartTopTracks(trackEntities);
                    }
                });
        addSubscription(subscription);
    }
}
