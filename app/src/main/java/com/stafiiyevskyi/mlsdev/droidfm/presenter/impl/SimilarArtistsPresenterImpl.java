package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist.Artist;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist.SimilarArtistsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.ArtistModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.ArtistModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.SimilarArtistPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist.SimilarArtistListToArtistEntityListMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistsScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 18.05.16.
 */
public class SimilarArtistsPresenterImpl extends BasePresenter implements SimilarArtistPresenter {

    private ArtistModel artistModel;
    private ArtistsScreenView view;

    public SimilarArtistsPresenterImpl(ArtistsScreenView view) {
        this.view = view;
        artistModel = new ArtistModelImpl();
    }

    @Override
    public void getSimilarArtists(String artistName) {
        Subscription subscription = artistModel.getSimilarArtists(artistName).map(this::unwrapResponse)
                .map(new SimilarArtistListToArtistEntityListMapper()).subscribe(new Observer<List<ArtistEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ArtistEntity> artistEntities) {
                        view.showArtists(artistEntities);
                    }
                });
        addSubscription(subscription);
    }

    private List<Artist> unwrapResponse(SimilarArtistsResponse response) {
        return response.getSimilarartists().getArtist();
    }

}
