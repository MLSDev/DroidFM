package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail.TrackDetail;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail.TrackDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TrackDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track.TrackDetailMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TrackDetailScreenView;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 27.04.16.
 */
public class TrackDetailScreenPresenterImpl extends BasePresenter implements TrackDetailScreenPresenter {

    private TrackModel mTrackModel;
    private TrackDetailScreenView mView;

    public TrackDetailScreenPresenterImpl(TrackDetailScreenView mView) {
        this.mView = mView;
        this.mTrackModel = new TrackModelImpl();
    }

    @Override
    public void getTrackDetails(String artist, String track, String mbid) {
        Subscription subscription = mTrackModel.getTrackDetail(artist, track, mbid)
                .map(this::unwrapResponse)
                .map(new TrackDetailMapper())
                .subscribe(new Observer<TrackDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(TrackDetailEntity trackDetailEntity) {
                        mView.showTrackDetail(trackDetailEntity);
                    }
                });
        addSubscription(subscription);
    }

    private TrackDetail unwrapResponse(TrackDetailResponse response) {
        return response.getTrack();
    }
}
