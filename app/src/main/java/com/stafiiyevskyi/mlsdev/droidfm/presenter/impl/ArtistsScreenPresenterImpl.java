package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import android.util.Log;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TopChartModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.ArtistModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TopChartModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistsScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.SearchArtistMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.TopChartArtistMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistsScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 20.04.16.
 */
public class ArtistsScreenPresenterImpl extends BasePresenter implements ArtistsScreenPresenter {

    private ArtistsScreenView mView;
    private TopChartModel mTopChartModel;
    private ArtistModel mArtistModel;

    public ArtistsScreenPresenterImpl(ArtistsScreenView view) {
        this.mView = view;
        mTopChartModel = new TopChartModelImpl();
        mArtistModel = new ArtistModelImpl();
    }

    @Override
    public void searchArtist(String artistName, int page) {
        Subscription subscription = mArtistModel.searchArtistByName(artistName, page)
                .map(new SearchArtistMapper())
                .subscribe(new Observer<List<ArtistEntity>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("onComplete","onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ArtistEntity> artistEntities) {
                        mView.showArtists(artistEntities);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getTopArtists(int page) {
        Subscription subscription = mTopChartModel.getTopChartArtists(page)
                .map(new TopChartArtistMapper())
                .subscribe(new Observer<List<ArtistEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ArtistEntity> artistEntities) {
                        mView.showArtists(artistEntities);
                    }
                });
        addSubscription(subscription);
    }
}
