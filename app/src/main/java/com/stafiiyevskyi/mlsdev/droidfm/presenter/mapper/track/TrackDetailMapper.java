package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.ItemsTag;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail.TrackDetail;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TagWithUrlEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackDetailEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TrackDetailMapper implements Func1<TrackDetail, TrackDetailEntity> {
    @Override
    public TrackDetailEntity call(TrackDetail trackDetail) {
        TrackDetailEntity entity = new TrackDetailEntity();
        entity.setMbid(trackDetail.getMbid());
        entity.setName(trackDetail.getName());
        entity.setArtistName(trackDetail.getArtist().getName());
        if (trackDetail.getWiki() != null) {
            entity.setContent(trackDetail.getWiki().getContent());
            entity.setPublished(trackDetail.getWiki().getPublished());
        }
        entity.setDuration(trackDetail.getDuration());
        List<TagWithUrlEntity> tags = new ArrayList<>();
        for (ItemsTag itemsTag : trackDetail.getToptags().getTag()) {
            TagWithUrlEntity tagWithUrlEntity = new TagWithUrlEntity();
            tagWithUrlEntity.setName(itemsTag.getName());
            tagWithUrlEntity.setUrl(itemsTag.getUrl());
            tags.add(tagWithUrlEntity);
        }
        entity.setTags(tags);

        return entity;
    }
}
