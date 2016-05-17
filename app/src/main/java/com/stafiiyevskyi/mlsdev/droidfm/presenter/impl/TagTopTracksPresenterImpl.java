package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.TagModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TagModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TagTopTracksPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag.TagsTopTrackListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track.SearchTracksListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopTracksScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopTracksPresenterImpl extends BasePresenter implements TagTopTracksPresenter {

    private TagTopTracksScreenView view;
    private TagModel tagModel;
    private TrackModel trackModel;

    public TagTopTracksPresenterImpl(TagTopTracksScreenView view) {
        this.view = view;
        this.tagModel = new TagModelImpl();
        this.trackModel = new TrackModelImpl();
    }

    @Override
    public void getTopTracks(String tag, int pageNumber) {
        Subscription subscription = tagModel.getTagsTopTracks(tag, pageNumber)
                .map(new TagsTopTrackListMapper())
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
                        view.showTopArtists(trackEntities);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void searchTracks(String trackName, int pageNumber) {
        Subscription subscription = trackModel.searchTrack("", trackName, pageNumber)
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
                        view.showTopArtists(topTrackEntities);
                    }
                });
        addSubscription(subscription);
    }
}
