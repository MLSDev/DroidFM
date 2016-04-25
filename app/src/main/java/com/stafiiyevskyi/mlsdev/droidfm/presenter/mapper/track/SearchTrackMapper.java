package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search.TrackSearch;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 22.04.16.
 */
public class SearchTrackMapper implements Func1<TrackSearch, TopTrackEntity> {
    @Override
    public TopTrackEntity call(TrackSearch track) {
        TopTrackEntity topTrackEntity = new TopTrackEntity();
        topTrackEntity.setTrackMbid(track.getMbid());
        topTrackEntity.setName(track.getName());
        List<ImageEntity> imageEntities = new ArrayList<>();

        for (Image image : track.getImage()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setSize(image.getSize());
            imageEntity.setText(image.getText());
            imageEntities.add(imageEntity);
        }
        topTrackEntity.setTracksImages(imageEntities);
        return topTrackEntity;
    }
}
