package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistTopTracksModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.ArtistTopTracksModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistTopTracksScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.TopTracksMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistTopTracksScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ArtistTopTracksScreenPresenterImpl extends BasePresenter implements ArtistTopTracksScreenPresenter {

    private ArtistTopTracksScreenView mView;
    private ArtistTopTracksModel model;

    public ArtistTopTracksScreenPresenterImpl(ArtistTopTracksScreenView mView) {
        this.mView = mView;
        model = new ArtistTopTracksModelImpl();
    }

    @Override
    public void getArtistTopTracks(String artistName, String artistMbid, int page) {
        Subscription subscription = model.getArtistTopTracks(artistName, artistMbid, page)
                .map(new TopTracksMapper())
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
                        mView.showTracks(trackEntities);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void searchArtistTracks(String trackName, String artistMbid, int page) {

    }
}
