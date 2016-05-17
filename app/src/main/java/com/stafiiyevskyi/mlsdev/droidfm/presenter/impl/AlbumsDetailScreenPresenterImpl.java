package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import android.util.Log;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetail;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.AlbumModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBAlbumModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TransactionCallback;
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
public class AlbumsDetailScreenPresenterImpl extends BasePresenter implements AlbumsDetailScreenPresenter, TransactionCallback {

    private AlbumModel albumModel;
    private DBAlbumModel dbAlbumModel;
    private AlbumDetailsScreenView view;

    public AlbumsDetailScreenPresenterImpl(AlbumDetailsScreenView view) {
        this.view = view;
        albumModel = new AlbumModelImpl();
        dbAlbumModel = new DBAlbumModelImpl(this);
    }

    @Override
    public void getAlbumsDetails(String artist, String album, String mbid) {
        Subscription subscription = albumModel.getAlbumDetails(artist, album, mbid)
                .map(this::unwrapResponse)
                .map(new AlbumsDetailMapper())
                .subscribe(new Observer<AlbumsDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(AlbumsDetailEntity albumsDetailEntity) {
                        view.showAlbumsDetails(albumsDetailEntity);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void addAlbumToFavorite(AlbumsDetailEntity album) {
        dbAlbumModel.addFavoriteAlbum(new AlbumsDetailToDAOMapper().call(album));
    }

    @Override
    public void deleteFromFavorite(AlbumsDetailEntity album) {
        dbAlbumModel.deleteFromFavorites(new AlbumsDetailToDAOMapper().call(album));
    }

    @Override
    public void isTrackFavorite(AlbumsDetailEntity album) {
        Subscription subscription = dbAlbumModel.findAlbum(album.getArtistName(), album.getName())
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
                            view.showAlbumIsFavorite(true);
                        } else {
                            view.showAlbumIsFavorite(false);
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
        if (view != null) ;
        view.onSuccess();
    }
}
