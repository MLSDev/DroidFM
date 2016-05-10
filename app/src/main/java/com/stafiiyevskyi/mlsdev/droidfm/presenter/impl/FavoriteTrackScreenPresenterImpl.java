package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import android.util.Log;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.DBModelImpl;
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
public class FavoriteTrackScreenPresenterImpl extends BasePresenter implements FavoriteTracksScreenPresenter, DBModelImpl.AddTrackToDBCallback {

    private DBModel mDBModel;
    private FavoriteTrackScreenView mView;

    public FavoriteTrackScreenPresenterImpl(FavoriteTrackScreenView mView) {
        this.mView = mView;
        mDBModel = new DBModelImpl(this);
    }

    @Override
    public void getFavoritesTrack() {
        Subscription subscription = mDBModel.getFavoriteTracks().map(new FavoriteListTracksFromDAOMapper())
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
        mDBModel.deleteFromFavorites(new FavoriteTrackToDAOMapper().call(track));
    }

    @Override
    public void onSuccess() {
        mView.showSuccess();
    }
}
