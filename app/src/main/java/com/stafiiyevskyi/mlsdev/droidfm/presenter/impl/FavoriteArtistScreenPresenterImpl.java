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

    private FavoriteArtistScreenView view;
    private DBArtistModel model;

    public FavoriteArtistScreenPresenterImpl(FavoriteArtistScreenView view) {
        this.view = view;
        this.model = new DBArtistModelImpl(this);
    }

    @Override
    public void getFavoriteArtists() {
        Subscription subscription = model.getFavoriteArtists().map(new FavoriteListArtistFromDAOMapper())
                .subscribe(new Observer<List<FavoriteArtistEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<FavoriteArtistEntity> favoriteArtistEntities) {
                        view.showFavoriteArtists(favoriteArtistEntities);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void deleteFromFavorites(FavoriteArtistEntity artist) {
        model.deleteFromFavorites(new FavoriteArtistToDAOMapper().call(artist));
    }

    @Override
    public void onSuccess() {
        if (view != null)
            view.showSuccess();
    }

    @Override
    public void stop() {
        super.stop();
        view = null;
    }
}
