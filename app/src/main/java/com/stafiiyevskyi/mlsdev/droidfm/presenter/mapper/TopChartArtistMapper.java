package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.TopChartArtists;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 21.04.16.
 */
public class TopChartArtistMapper implements Func1<TopChartArtists, List<ArtistEntity>> {
    @Override
    public List<ArtistEntity> call(TopChartArtists topChartArtists) {
        return Observable.from(topChartArtists.getArtists().getArtist())
                .map(new ArtistMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
