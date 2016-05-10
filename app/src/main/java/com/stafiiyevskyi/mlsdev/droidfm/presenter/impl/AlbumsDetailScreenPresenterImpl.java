package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import android.util.Log;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetail;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.AlbumModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBAlbumModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.AlbumModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.DBAlbumModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.AlbumsDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumsDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteAlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album.AlbumsDetailMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album.AlbumsDetailToDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album.FavoriteListAlbumsFromDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.AlbumDetailsScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsDetailScreenPresenterImpl extends BasePresenter implements AlbumsDetailScreenPresenter, DBAlbumModelImpl.AddAlbumToDBCallback {

    private AlbumModel mAlbumModel;
    private DBAlbumModel mDBAlbumModel;
    private AlbumDetailsScreenView mView;

    public AlbumsDetailScreenPresenterImpl(AlbumDetailsScreenView mView) {
        this.mView = mView;
        mAlbumModel = new AlbumModelImpl();
        mDBAlbumModel = new DBAlbumModelImpl(this);
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

    @Override
    public void addAlbumToFavorite(AlbumsDetailEntity album) {
        mDBAlbumModel.addFavoriteAlbum(new AlbumsDetailToDAOMapper().call(album));
    }

    @Override
    public void deleteFromFavorite(AlbumsDetailEntity album) {
        mDBAlbumModel.deleteFromFavorites(new AlbumsDetailToDAOMapper().call(album));
    }

    @Override
    public void isTrackFavorite(AlbumsDetailEntity album) {
        Subscription subscription = mDBAlbumModel.findAlbum(album.getArtistName(), album.getName())
                .map(new FavoriteListAlbumsFromDAOMapper())
                .subscribe(new Observer<List<FavoriteAlbumEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("DB error", e.getMessage());
                    }

                    @Override
                    public void onNext(List<FavoriteAlbumEntity> favoriteAlbumsEntities) {
                        if (favoriteAlbumsEntities.size() > 0) {
                            mView.showAlbumIsFavorite(true);
                        } else {
                            mView.showAlbumIsFavorite(false);
                        }

                    }
                });
        addSubscription(subscription);
    }


    private AlbumDetail unwrapResponse(AlbumDetailResponse response) {
        return response.getAlbum();
    }

    @Override
    public void onSuccess() {
        if (mView != null) ;
        mView.onSuccess();
    }
}
