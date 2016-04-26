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

    private TagTopTracksScreenView mView;
    private TagModel mTagModel;
    private TrackModel mTrackModel;

    public TagTopTracksPresenterImpl(TagTopTracksScreenView mView) {
        this.mView = mView;
        this.mTagModel = new TagModelImpl();
        this.mTrackModel = new TrackModelImpl();
    }

    @Override
    public void getTopTracks(String tag, int pageNumber) {
        Subscription subscription = mTagModel.getTagsTopTracks(tag, pageNumber)
                .map(new TagsTopTrackListMapper())
                .subscribe(new Observer<List<TopTrackEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TopTrackEntity> trackEntities) {
                        mView.showTopArtists(trackEntities);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void searchTracks(String trackName, int pageNumber) {
        Subscription subscription = mTrackModel.searchTrack("", trackName, pageNumber)
                .map(new SearchTracksListMapper())
                .subscribe(new Observer<List<TopTrackEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TopTrackEntity> topTrackEntities) {
                        mView.showTopArtists(topTrackEntities);
                    }
                });
        addSubscription(subscription);
    }
}
