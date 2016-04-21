package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.SearchArtist;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.TopChartArtists;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TopChartModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.ArtistModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TopChartModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistsScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistsScreenView;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 20.04.16.
 */
public class ArtistsScreenPresenterImpl extends BasePresenter implements ArtistsScreenPresenter {

    private ArtistsScreenView view;
    private TopChartModel topChartModel;
    private ArtistModel artistModel;

    public ArtistsScreenPresenterImpl(ArtistsScreenView view) {
        this.view = view;
        topChartModel = new TopChartModelImpl();
        artistModel = new ArtistModelImpl();
    }

    @Override
    public void searchArtist(String artistName, int page) {
        Subscription subscription = artistModel.searchArtistByName(artistName,page)
                .subscribe(new Observer<SearchArtist>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchArtist searchArtist) {

                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getTopArtists(int page) {
        Subscription subscription = topChartModel.getTopChartArtists(page)
                .subscribe(new Observer<TopChartArtists>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TopChartArtists topChartArtists) {

                    }
                });
        addSubscription(subscription);
    }
}
