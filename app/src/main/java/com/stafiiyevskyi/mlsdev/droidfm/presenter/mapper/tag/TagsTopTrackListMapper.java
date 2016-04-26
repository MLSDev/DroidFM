package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.toptracks.TagTopTracksResponse;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagsTopTrackListMapper implements Func1<TagTopTracksResponse, List<TopTrackEntity>> {
    @Override
    public List<TopTrackEntity> call(TagTopTracksResponse tagTopTracksResponse) {
        return Observable.from(tagTopTracksResponse.getTracks().getTrack())
                .map(new TagsTopTrackMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
