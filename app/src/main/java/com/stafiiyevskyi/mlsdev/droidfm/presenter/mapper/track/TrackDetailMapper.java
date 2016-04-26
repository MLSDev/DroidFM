package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.detail.TrackDetail;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackDetailEntity;

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
        entity.setContent(trackDetail.getWiki().getContent());
        entity.setPublished(trackDetail.getWiki().getPublished());
        entity.setDuration(trackDetail.getDuration());

        return entity;
    }
}
