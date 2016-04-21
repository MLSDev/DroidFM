package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.Image;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.Artist;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ArtistMapper implements Func1<Artist, ArtistEntity> {
    @Override
    public ArtistEntity call(Artist artist) {
        ArtistEntity artistEntity = new ArtistEntity();
        List<ImageEntity> imageEntities = new ArrayList<>();

        for (Image image : artist.getImage()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setSize(image.getSize());
            imageEntity.setText(image.getText());
            imageEntities.add(imageEntity);
        }
        artistEntity.setArtistImages(imageEntities);
        artistEntity.setArtisMbid(artist.getMbid());
        artistEntity.setArtistName(artist.getName());
        return artistEntity;
    }
}
