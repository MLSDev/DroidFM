package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import android.util.Log;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail.TrackDetail;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail.TrackDetailResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VKTrackResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VkTrackItemResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.DBTrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.VKTrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.DBTrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.VKTrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TrackDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track.FavoriteListTracksFromDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track.FavoriteTrackToDAOMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track.TrackDetailMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TrackDetailScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 27.04.16.
 */
public class TrackDetailScreenPresenterImpl extends BasePresenter implements TrackDetailScreenPresenter, DBTrackModelImpl.AddTrackToDBCallback {

    private TrackModel mTrackModel;
    private VKTrackModel mVKTrackModel;
    private DBTrackModel mDBTrackModel;
    private TrackDetailScreenView mView;

    public TrackDetailScreenPresenterImpl(TrackDetailScreenView mView) {
        this.mView = mView;
        this.mTrackModel = new TrackModelImpl();
        this.mVKTrackModel = new VKTrackModelImpl();
        this.mDBTrackModel = new DBTrackModelImpl(this);
    }

    @Override
    public void getTrackDetails(String artist, String track, String mbid) {
        Subscription subscription = mTrackModel.getTrackDetail(artist, track, mbid)
                .map(this::unwrapResponse)
                .map(new TrackDetailMapper())
                .subscribe(new Observer<TrackDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Error Track Detail", e.getMessage());
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(TrackDetailEntity trackDetailEntity) {
                        mView.showTrackDetail(trackDetailEntity);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void isTrackFavorite(TrackDetailEntity trackDetailEntity) {
        Subscription subscription = mDBTrackModel.findTracks(trackDetailEntity.getArtistName(), trackDetailEntity.getName())
                .map(new FavoriteListTracksFromDAOMapper())
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
                        if (favoriteTrackEntities.size() > 0) {
                            mView.showTrackIsFavorite(true);
                        } else {
                            mView.showTrackIsFavorite(false);
                        }

                    }
                });
        addSubscription(subscription);

    }

    @Override
    public void getTrackStreamUrl(String trackSearch) {
        Subscription subscription = mVKTrackModel.getVKTrack(trackSearch)
                .subscribe(new Observer<VKTrackResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(VKTrackResponse vkTrackResponse) {
                        VkTrackItemResponse response = vkTrackResponse.getResponse()[0];
                        mView.showTrackStreamUrl(response.getUrl(), response.getDuration());
                    }
                });
        addSubscription(subscription);

    }

    @Override
    public void addTrackToFavorite(FavoriteTrackEntity track) {
        mDBTrackModel.addFavoriteTrack(new FavoriteTrackToDAOMapper().call(track));
    }

    @Override
    public void deleteFromFavorite(FavoriteTrackEntity track) {
        mDBTrackModel.deleteFromFavorites(new FavoriteTrackToDAOMapper().call(track));
    }

    private TrackDetail unwrapResponse(TrackDetailResponse response) {
        return response.getTrack();
    }

    @Override
    public void onSuccess() {
        mView.onSuccess();
    }
}
