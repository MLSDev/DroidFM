package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetail;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.AlbumModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.AlbumModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.AlbumsDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumsDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album.AlbumsDetailMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.AlbumDetailsScreenView;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsDetailScreenPresenterImpl extends BasePresenter implements AlbumsDetailScreenPresenter {

    private AlbumModel mAlbumModel;
    private AlbumDetailsScreenView mView;

    public AlbumsDetailScreenPresenterImpl(AlbumDetailsScreenView mView) {
        this.mView = mView;
        mAlbumModel = new AlbumModelImpl();
    }

    @Override
    public void getAlbumsDetails(String artist, String album, String mbid) {
        Subscription subscription = mAlbumModel.getAlbumDetails(artist, album, mbid)
                .map(this::unwrapResponse)
                .map(new AlbumsDetailMapper())
                .subscribe(new Observer<AlbumsDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(AlbumsDetailEntity albumsDetailEntity) {
                        mView.showAlbumsDetails(albumsDetailEntity);
                    }
                });
        addSubscription(subscription);
    }

    private AlbumDetail unwrapResponse(AlbumDetailResponse response) {
        return response.getAlbum();
    }
}
