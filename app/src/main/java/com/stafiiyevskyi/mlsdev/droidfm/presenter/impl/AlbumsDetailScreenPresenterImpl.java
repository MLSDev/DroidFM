package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import android.util.Log;

import com.stafiiyevskyi.mlsdev.droidfm.app.player.TrackPlayerEntity;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetail;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.detail.AlbumDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VKTrackResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.AlbumModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.VKTrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.AlbumModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.VKTrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.AlbumsDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumsDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.album.AlbumsDetailMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.AlbumDetailsScreenView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsDetailScreenPresenterImpl extends BasePresenter implements AlbumsDetailScreenPresenter {

    private AlbumModel mAlbumModel;
    private VKTrackModel mVKTrackModel;
    private AlbumDetailsScreenView mView;

    public AlbumsDetailScreenPresenterImpl(AlbumDetailsScreenView mView) {
        this.mView = mView;
        mAlbumModel = new AlbumModelImpl();
        mVKTrackModel = new VKTrackModelImpl();
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

    public void getAlbumPlaylist(List<TrackEntity> tracks) {
        List<Observable<VKTrackResponse>> responses = new ArrayList<>();

        for (TrackEntity trackEntity : tracks) {
            responses.add(mVKTrackModel.getVKTrack(trackEntity.getArtistName() + " - " + trackEntity.getName()).delay(2, TimeUnit.SECONDS));
        }
        Subscription subscription = Observable.merge(responses)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .toList()
                .single()
                .subscribe(new Observer<List<VKTrackResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error Merge", e.getMessage());
                    }

                    @Override
                    public void onNext(List<VKTrackResponse> vkTrackResponses) {
                        List<TrackPlayerEntity> playlist = new ArrayList<>();
                        for (VKTrackResponse response : vkTrackResponses) {
                            if (response.getResponse() != null) {
                                TrackPlayerEntity entity = new TrackPlayerEntity();
                                entity.setmTrackUrl(response.getResponse()[0].getUrl());
                                playlist.add(entity);
                            }
                        }
                        mView.showPlaylistUrls(playlist);
                    }
                });
        addSubscription(subscription);
    }

    private AlbumDetail unwrapResponse(AlbumDetailResponse response) {
        return response.getAlbum();
    }
}
