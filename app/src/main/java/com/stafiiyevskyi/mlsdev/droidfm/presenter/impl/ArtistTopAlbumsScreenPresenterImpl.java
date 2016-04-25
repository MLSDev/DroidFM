package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.ArtistModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistTopAlbumsPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist.TopAlbumsListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistTopAlbumsScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ArtistTopAlbumsScreenPresenterImpl extends BasePresenter implements ArtistTopAlbumsPresenter {

    private ArtistModel mModel;
    private ArtistTopAlbumsScreenView mView;

    public ArtistTopAlbumsScreenPresenterImpl(ArtistTopAlbumsScreenView mView) {
        this.mView = mView;
        mModel = new ArtistModelImpl();
    }

    @Override
    public void getArtistTopAlbums(String artistName, String artistMbid, int page) {
        Subscription subscription = mModel.getArtistTopAlbums(artistName, artistMbid, page)
                .map(new TopAlbumsListMapper())
                .subscribe(new Observer<List<AlbumEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<AlbumEntity> albumEntities) {
                        mView.showArtistTopAlbums(albumEntities);
                    }
                });
        addSubscription(subscription);
    }
}
