package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.Track;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ChartTopTrackMapper implements Func1<Track, TopTrackEntity> {
    @Override
    public TopTrackEntity call(Track track) {
        TopTrackEntity entity = new TopTrackEntity();
        entity.setName(track.getName());
        entity.setTrackMbid(track.getMbid());
        List<ImageEntity> imageEntities = new ArrayList<>();
        for (Image image : track.getImage()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setSize(image.getSize());
            imageEntity.setText(image.getText());
            imageEntities.add(imageEntity);
        }
        entity.setTracksImages(imageEntities);
        return entity;
    }
}
