package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.vk;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.popular.Response;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.VkPopularTrackEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 19.05.16.
 */
public class ListVkPTResponseToListVkPopTrackEntityMapper implements Func1<List<Response>, List<VkPopularTrackEntity>> {
    @Override
    public List<VkPopularTrackEntity> call(List<Response> responses) {
        return Observable.from(responses)
                .map(new VkPTResponseToVkPopularTrackEntityMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
