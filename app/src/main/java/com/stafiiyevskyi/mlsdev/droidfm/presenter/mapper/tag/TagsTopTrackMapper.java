package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.toptracks.TopTrackByTag;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagsTopTrackMapper implements Func1<TopTrackByTag, TopTrackEntity> {
    @Override
    public TopTrackEntity call(TopTrackByTag topTrackByTag) {
        TopTrackEntity entity = new TopTrackEntity();
        entity.setArtistName(topTrackByTag.getArtist().getName());
        entity.setName(topTrackByTag.getName());
        entity.setTrackMbid(topTrackByTag.getMbid());
        List<ImageEntity> imageEntities = new ArrayList<>();
        for (Image image : topTrackByTag.getImage()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setSize(image.getSize());
            imageEntity.setText(image.getText());
            imageEntities.add(imageEntity);
        }
        entity.setTracksImages(imageEntities);
        return entity;
    }
}
