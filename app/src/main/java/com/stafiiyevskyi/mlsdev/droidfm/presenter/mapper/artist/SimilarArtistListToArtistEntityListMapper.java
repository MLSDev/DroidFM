package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.artist.Artist;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 19.05.16.
 */
public class SimilarArtistListToArtistEntityListMapper implements Func1<List<Artist>, List<ArtistEntity>> {
    @Override
    public List<ArtistEntity> call(List<Artist> artists) {
        return Observable.from(artists)
                .map(new SimilarArtistToArtistEntityMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
