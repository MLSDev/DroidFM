package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.TagModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.TagModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TagTopAlbumsPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag.TagsTopAlbumListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopAlbumsScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopAlbumsPresenterImpl extends BasePresenter implements TagTopAlbumsPresenter {

    private TagModel mModel;
    private TagTopAlbumsScreenView mView;

    public TagTopAlbumsPresenterImpl(TagTopAlbumsScreenView mView) {
        this.mView = mView;
        this.mModel = new TagModelImpl();
    }

    @Override
    public void getTopAlbums(String tag, int pageNumber) {
        Subscription subscription = mModel.getTagsTopAlbums(tag, pageNumber)
                .map(new TagsTopAlbumListMapper())
                .subscribe(new Observer<List<AlbumEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<AlbumEntity> albumEntities) {
                        mView.showTopAlbums(albumEntities);
                    }
                });
        addSubscription(subscription);
    }
}
