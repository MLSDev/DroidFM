package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBArtistModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TransactionCallback;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.DBArtistModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.FavoriteArtistScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist.FavoriteArtistToDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist.FavoriteListArtistFromDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.FavoriteArtistScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 11.05.16.
 */
public class FavoriteArtistScreenPresenterImpl extends BasePresenter implements FavoriteArtistScreenPresenter, TransactionCallback {

    private FavoriteArtistScreenView mView;
    private DBArtistModel mModel;

    public FavoriteArtistScreenPresenterImpl(FavoriteArtistScreenView mView) {
        this.mView = mView;
        this.mModel = new DBArtistModelImpl(this);
    }

    @Override
    public void getFavoriteArtists() {
        Subscription subscription = mModel.getFavoriteArtists().map(new FavoriteListArtistFromDAOMapper())
                .subscribe(new Observer<List<FavoriteArtistEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<FavoriteArtistEntity> favoriteArtistEntities) {
                        mView.showFavoriteArtists(favoriteArtistEntities);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void deleteFromFavorites(FavoriteArtistEntity artist) {
        mModel.deleteFromFavorites(new FavoriteArtistToDAOMapper().call(artist));
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
