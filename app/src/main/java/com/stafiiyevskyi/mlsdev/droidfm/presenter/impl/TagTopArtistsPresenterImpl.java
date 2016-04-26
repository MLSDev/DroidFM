package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.TagModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TagModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TagTopArtistsPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag.TagsTopArtistListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopArtistScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopArtistsPresenterImpl extends BasePresenter implements TagTopArtistsPresenter {

    private TagModel mModel;
    private TagTopArtistScreenView mView;

    public TagTopArtistsPresenterImpl(TagTopArtistScreenView mView) {
        this.mView = mView;
        this.mModel = new TagModelImpl();
    }

    @Override
    public void getTopArtists(String tag, int pageNumber) {
        Subscription subscription = mModel.getTagsTopArtists(tag, pageNumber)
                .map(new TagsTopArtistListMapper())
                .subscribe(new Observer<List<ArtistEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ArtistEntity> artistEntities) {
                        mView.showTopArtist(artistEntities);
                    }
                });
        addSubscription(subscription);
    }
}
