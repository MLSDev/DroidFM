package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist.Artist;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 19.05.16.
 */
public class SimilarArtistToArtistEntityMapper implements Func1<Artist, ArtistEntity> {
    @Override
    public ArtistEntity call(Artist artist) {
        ArtistEntity entity = new ArtistEntity();
        List<ImageEntity> images = new ArrayList<>();
        for (Image image : artist.getImage()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setSize(image.getSize());
            imageEntity.setText(image.getText());
            images.add(imageEntity);
        }
        entity.setArtistImages(images);
        entity.setArtisMbid(artist.getMbid());
        entity.setArtistName(artist.getName());
        return entity;
    }
}
