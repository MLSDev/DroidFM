package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import android.util.Log;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBArtistModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TransactionCallback;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.DBArtistModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistContentDetailsScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist.FavoriteArtistToDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist.FavoriteListArtistFromDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistContentScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 11.05.16.
 */
public class ArtistContentDetailsScreenPresenterImp extends BasePresenter implements ArtistContentDetailsScreenPresenter, TransactionCallback {
    private DBArtistModel mArtistModel;
    private ArtistContentScreenView mView;

    public ArtistContentDetailsScreenPresenterImp(ArtistContentScreenView mView) {
        mArtistModel = new DBArtistModelImpl(this);
        this.mView = mView;
    }

    @Override
    public void addArtistToFavorite(FavoriteArtistEntity artist) {
        mArtistModel.addFavoriteArtist(new FavoriteArtistToDAOMapper().call(artist));
    }

    @Override
    public void deleteFromFavorite(FavoriteArtistEntity artist) {
        mArtistModel.deleteFromFavorites(new FavoriteArtistToDAOMapper().call(artist));
    }

    @Override
    public void isArtistFavorite(FavoriteArtistEntity artist) {
        Subscription subscription = mArtistModel.findArtist(artist.getName(), artist.getMbid())
                .map(new FavoriteListArtistFromDAOMapper())
                .subscribe(new Observer<List<FavoriteArtistEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("DB error", e.getMessage());
                    }

                    @Override
                    public void onNext(List<FavoriteArtistEntity> favoriteTrackEntities) {
                        if (favoriteTrackEntities.size() > 0) {
                            mView.showArtistIsFavorite(true);
                        } else {
                            mView.showArtistIsFavorite(false);
                        }
                    }
                });
        addSubscription(subscription);
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
