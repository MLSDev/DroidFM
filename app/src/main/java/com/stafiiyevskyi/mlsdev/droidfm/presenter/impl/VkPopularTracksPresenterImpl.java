package com.stafiiyevskyi.mlsdev.droidfm.presenter.impl;

import com.stafiiyevskyi.mlsdev.droidfm.app.util.Genre;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.popular.Response;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.popular.VkPopularTrackResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.VKTrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.VKTrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.BasePresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.VkPopularTracksPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.VkPopularTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.vk.ListVkPTResponseToListVkPopTrackEntityMapper;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.VkPopularTracksScreenView;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Created by oleksandr on 19.05.16.
 */
public class VkPopularTracksPresenterImpl extends BasePresenter implements VkPopularTracksPresenter {

    private VKTrackModel model;
    private VkPopularTracksScreenView view;

    public VkPopularTracksPresenterImpl(VkPopularTracksScreenView view) {
        this.view = view;
        model = new VKTrackModelImpl();
    }

    @Override
    public void getVkPopularTrack(Genre genre, int offset, int count) {
        Subscription subscription = model.getVkPopularTracks(genre.getId(), offset, count).map(this::unwrapResponse)
                .map(new ListVkPTResponseToListVkPopTrackEntityMapper())
                .subscribe(new Observer<List<VkPopularTrackEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<VkPopularTrackEntity> vkPopularTrackEntities) {
                        view.showVkPopularTracks(vkPopularTrackEntities);
                    }
                });
        addSubscription(subscription);
    }

    private List<Response> unwrapResponse(VkPopularTrackResponse response) {
        return response.getResponse();
    }
}
