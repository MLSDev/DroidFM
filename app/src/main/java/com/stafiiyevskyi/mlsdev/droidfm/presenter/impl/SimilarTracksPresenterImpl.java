package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.track.SimilarTracksResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.track.Track;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.SimilarTracksPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track.ListSimilarTrackToListTopTrackMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopTracksScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 19.05.16.
 */
public class SimilarTracksPresenterImpl extends BasePresenter implements SimilarTracksPresenter {

    private TrackModel model;
    private TagTopTracksScreenView view;

    public SimilarTracksPresenterImpl(TagTopTracksScreenView view) {
        this.view = view;
        model = new TrackModelImpl();
    }

    @Override
    public void getSimilarTracks(String artist, String track, int page) {
        Subscription subscription = model.getSimilarTracks(artist, track, page)
                .map(this::unwrapResponse)
                .map(new ListSimilarTrackToListTopTrackMapper())
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
                        view.showTopArtists(topTrackEntities);
                    }
                });
        addSubscription(subscription);
    }

    private List<Track> unwrapResponse(SimilarTracksResponse response) {
        return response.getSimilartracks().getTrack();
    }
}
