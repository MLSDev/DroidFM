package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.ArtistTopTrack;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ArtistTopTrackMapper implements Func1<ArtistTopTrack, TopTrackEntity> {
    @Override
    public TopTrackEntity call(ArtistTopTrack artistTopTrack) {
        List<ImageEntity> imageEntities = new ArrayList<>();

        for (Image image : artistTopTrack.getImage()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setSize(image.getSize());
            imageEntity.setText(image.getText());
            imageEntities.add(imageEntity);
        }
        TopTrackEntity trackEntity = new TopTrackEntity();
        trackEntity.setName(artistTopTrack.getName());
        trackEntity.setTrackMbid(artistTopTrack.getMbid());
        trackEntity.setTracksImages(imageEntities);
        trackEntity.setArtistName(artistTopTrack.getArtist().getName());

        return trackEntity;
    }
}
