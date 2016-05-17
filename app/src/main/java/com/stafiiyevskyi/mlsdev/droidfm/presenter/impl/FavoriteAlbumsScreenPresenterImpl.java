package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBAlbumModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TransactionCallback;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.DBAlbumModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.FavoriteAlbumsScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteAlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album.FavoriteAlbumToDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album.FavoriteListAlbumsFromDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.FavoriteAlbumScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteAlbumsScreenPresenterImpl extends BasePresenter implements FavoriteAlbumsScreenPresenter, TransactionCallback {

    private DBAlbumModel albumModel;
    private FavoriteAlbumScreenView view;

    public FavoriteAlbumsScreenPresenterImpl(FavoriteAlbumScreenView view) {
        this.view = view;
        this.albumModel = new DBAlbumModelImpl(this);
    }

    @Override
    public void getFavoritesAlbums() {
        Subscription subscription = albumModel.getFavoriteAlbums()
                .map(new FavoriteListAlbumsFromDAOMapper())
                .subscribe(new Observer<List<FavoriteAlbumEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<FavoriteAlbumEntity> favoriteAlbumEntities) {
                        view.showFavoriteAlbums(favoriteAlbumEntities);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void deleteFromFavorites(FavoriteAlbumEntity album) {
        albumModel.deleteFromFavorites(new FavoriteAlbumToDAOMapper().call(album));
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
