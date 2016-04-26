package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topalbums.TopAlbumByTag;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagsTopAlbumMapper implements Func1<TopAlbumByTag, AlbumEntity> {
    @Override
    public AlbumEntity call(TopAlbumByTag topAlbumByTag) {
        AlbumEntity entity = new AlbumEntity();
        entity.setName(topAlbumByTag.getName());
        entity.setArtistName(topAlbumByTag.getArtist().getName());
        entity.setMbid(topAlbumByTag.getMbid());
        List<ImageEntity> imageEntities = new ArrayList<>();
        for (Image image : topAlbumByTag.getImage()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setSize(image.getSize());
            imageEntity.setText(image.getText());
            imageEntities.add(imageEntity);
        }
        entity.setImage(imageEntities);
        return entity;
    }
}
