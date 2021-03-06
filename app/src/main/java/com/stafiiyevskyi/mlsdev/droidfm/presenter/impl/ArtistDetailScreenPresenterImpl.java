package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.ArtistModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist.ArtistDetailMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistDetailScreenView;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 25.04.16.
 */
public class ArtistDetailScreenPresenterImpl extends BasePresenter implements ArtistDetailScreenPresenter {

    private ArtistModel model;
    private ArtistDetailScreenView view;

    public ArtistDetailScreenPresenterImpl(ArtistDetailScreenView view) {
        this.view = view;
        this.model = new ArtistModelImpl();
    }

    @Override
    public void getArtistInformation(String artistName, String mbid) {
        Subscription subscription = model.getArtistInfo(mbid)
                .map(new ArtistDetailMapper())
                .subscribe(new Observer<ArtistDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArtistDetailEntity artistDetailEntity) {
                        view.showArtistDetailInformation(artistDetailEntity);
                    }
                });
        addSubscription(subscription);
    }
}
