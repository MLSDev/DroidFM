package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topartists.TagTopArtistsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagsTopArtistListMapper implements Func1<TagTopArtistsResponse, List<ArtistEntity>> {
    @Override
    public List<ArtistEntity> call(TagTopArtistsResponse tagTopArtistsResponse) {
        return Observable.from(tagTopArtistsResponse.getTopartists().getArtist())
                .map(new TagsTopArtistMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
