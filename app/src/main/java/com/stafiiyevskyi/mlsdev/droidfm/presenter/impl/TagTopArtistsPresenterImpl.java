package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.TagModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.ArtistModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TagModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TagTopArtistsPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist.SearchArtistMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag.TagsTopArtistListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopArtistScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopArtistsPresenterImpl extends BasePresenter implements TagTopArtistsPresenter {

    private TagModel mTagModel;
    private ArtistModel mArtistModel;
    private TagTopArtistScreenView mView;

    public TagTopArtistsPresenterImpl(TagTopArtistScreenView mView) {
        this.mView = mView;
        this.mTagModel = new TagModelImpl();
        this.mArtistModel = new ArtistModelImpl();
    }

    @Override
    public void getTopArtists(String tag, int pageNumber) {
        Subscription subscription = mTagModel.getTagsTopArtists(tag, pageNumber)
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

    @Override
    public void searchArtist(String artistName, int pageNumber) {
        Subscription subscription = mArtistModel.searchArtistByName(artistName, pageNumber)
                .map(new SearchArtistMapper())
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
