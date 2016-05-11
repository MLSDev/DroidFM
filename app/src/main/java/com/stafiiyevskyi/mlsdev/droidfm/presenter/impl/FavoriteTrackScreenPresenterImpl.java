package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import android.util.Log;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBTrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TransactionCallback;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.DBTrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.FavoriteTracksScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track.FavoriteListTracksFromDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track.FavoriteTrackToDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.FavoriteTrackScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteTrackScreenPresenterImpl extends BasePresenter implements FavoriteTracksScreenPresenter, TransactionCallback {

    private DBTrackModel mDBTrackModel;
    private FavoriteTrackScreenView mView;

    public FavoriteTrackScreenPresenterImpl(FavoriteTrackScreenView mView) {
        this.mView = mView;
        mDBTrackModel = new DBTrackModelImpl(this);
    }

    @Override
    public void getFavoritesTrack() {
        Subscription subscription = mDBTrackModel.getFavoriteTracks().map(new FavoriteListTracksFromDAOMapper())
                .subscribe(new Observer<List<FavoriteTrackEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("DB error", e.getMessage());
                    }

                    @Override
                    public void onNext(List<FavoriteTrackEntity> favoriteTrackEntities) {
                        mView.showFavoriteTrack(favoriteTrackEntities);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void deleteFromFavorites(FavoriteTrackEntity track) {
        mDBTrackModel.deleteFromFavorites(new FavoriteTrackToDAOMapper().call(track));
    }

    @Override
    public void onSuccess() {
        if (mView != null)
            mView.showSuccess();
    }

    @Override
    public void stop() {
        super.stop();
        mView = null;
    }
}
