package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.TopChartModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TopChartModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ChartTopTagScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTagEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.chart.ChartTopTagListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ChartTopTagScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 25.04.16.
 */
public class ChartTopTagScreenPresenterImpl extends BasePresenter implements ChartTopTagScreenPresenter {

    private ChartTopTagScreenView view;
    private TopChartModel model;

    public ChartTopTagScreenPresenterImpl(ChartTopTagScreenView view) {
        this.view = view;
        this.model = new TopChartModelImpl();
    }

    @Override
    public void getTopTags(int pageNumber) {
        Subscription subscription = model.getTopChartTags(pageNumber)
                .map(new ChartTopTagListMapper())
                .subscribe(new Observer<List<TopTagEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TopTagEntity> topTagEntities) {
                        view.showTopTags(topTagEntities);
                    }
                });
        addSubscription(subscription);

    }
}
