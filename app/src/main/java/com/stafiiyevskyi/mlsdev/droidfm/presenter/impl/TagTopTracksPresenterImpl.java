package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.TagModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TagModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TagTopTracksPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag.TagsTopTrackListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopTracksScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopTracksPresenterImpl extends BasePresenter implements TagTopTracksPresenter {

    private TagTopTracksScreenView mView;
    private TagModel mModel;

    public TagTopTracksPresenterImpl(TagTopTracksScreenView mView) {
        this.mView = mView;
        this.mModel = new TagModelImpl();
    }

    @Override
    public void getTopTracks(String tag, int pageNumber) {
        Subscription subscription = mModel.getTagsTopTracks(tag, pageNumber)
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
}
