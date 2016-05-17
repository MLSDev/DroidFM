package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.ArtistModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistTopTracksScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist.ArtistTopTracksMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track.SearchTracksListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistTopTracksScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ArtistTopTracksScreenPresenterImpl extends BasePresenter implements ArtistTopTracksScreenPresenter {

    private ArtistTopTracksScreenView view;
    private ArtistModel artistModel;
    private TrackModel trackModel;

    public ArtistTopTracksScreenPresenterImpl(ArtistTopTracksScreenView view) {
        this.view = view;
        artistModel = new ArtistModelImpl();
        trackModel = new TrackModelImpl();
    }

    @Override
    public void getArtistTopTracks(String artistName, String artistMbid, int page) {
        Subscription subscription = artistModel.getArtistTopTracks(artistName, artistMbid, page)
                .map(new ArtistTopTracksMapper())
                .subscribe(new Observer<List<TopTrackEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TopTrackEntity> trackEntities) {
                        view.showTracks(trackEntities);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void searchArtistTracks(String artistName, String trackName, int page) {
        Subscription subscription = trackModel.searchTrack(artistName, trackName, page)
                .map(new SearchTracksListMapper())
                .subscribe(new Observer<List<TopTrackEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TopTrackEntity> topTrackEntities) {
                        view.showTracks(topTrackEntities);
                    }
                });
        addSubscription(subscription);
    }
}
