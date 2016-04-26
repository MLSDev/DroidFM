package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topartists.TopArtistByTag;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagsTopArtistMapper implements Func1<TopArtistByTag, ArtistEntity> {
    @Override
    public ArtistEntity call(TopArtistByTag topArtistByTag) {
        ArtistEntity entity = new ArtistEntity();
        entity.setArtistName(topArtistByTag.getName());
        entity.setArtisMbid(topArtistByTag.getMbid());
        List<ImageEntity> imageEntities = new ArrayList<>();
        for (Image image : topArtistByTag.getImage()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setSize(image.getSize());
            imageEntity.setText(image.getText());
            imageEntities.add(imageEntity);
        }
        entity.setArtistImages(imageEntities);
        return entity;
    }
}
