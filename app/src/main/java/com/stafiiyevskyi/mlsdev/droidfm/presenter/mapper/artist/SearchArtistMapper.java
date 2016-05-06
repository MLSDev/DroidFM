package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.artist;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.artist.SearchArtist;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 21.04.16.
 */
public class SearchArtistMapper implements Func1<SearchArtist, List<ArtistEntity>> {
    @Override
    public List<ArtistEntity> call(SearchArtist searchArtist) {
        return Observable.from(searchArtist.getResults().getArtistmatches().getArtist())
                .map(new ArtistMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
