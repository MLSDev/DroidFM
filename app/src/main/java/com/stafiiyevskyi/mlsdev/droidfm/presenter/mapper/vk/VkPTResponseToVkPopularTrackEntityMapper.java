package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.vk;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.popular.Response;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.VkPopularTrackEntity;

import rx.functions.Func1;

/**
 * Created by oleksandr on 19.05.16.
 */
public class VkPTResponseToVkPopularTrackEntityMapper implements Func1<Response,VkPopularTrackEntity> {
    @Override
    public VkPopularTrackEntity call(Response response) {
        VkPopularTrackEntity entity = new VkPopularTrackEntity();
        entity.setArtist(response.getArtist());
        entity.setTitle(response.getTitle());
        entity.setDuration(response.getDuration());
        entity.setGenre(response.getGenreId());
        entity.setUrl(response.getUrl());
        return entity;
    }
}
