package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.Album;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TopAlbumMapper implements Func1<Album, AlbumEntity> {
    @Override
    public AlbumEntity call(Album album) {
        AlbumEntity entity = new AlbumEntity();
        List<ImageEntity> imageEntities = new ArrayList<>();
        for (Image image : album.getImage()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setSize(image.getSize());
            imageEntity.setText(image.getText());
            imageEntities.add(imageEntity);
        }
        entity.setName(album.getName());
        entity.setArtistName(album.getArtist().getName());
        entity.setPlaycount(album.getPlaycount());
        entity.setImage(imageEntities);
        return entity;
    }
}
