package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.tag;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.topalbums.TagTopAlbumsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagsTopAlbumListMapper implements Func1<TagTopAlbumsResponse, List<AlbumEntity>> {
    @Override
    public List<AlbumEntity> call(TagTopAlbumsResponse tagTopAlbumsResponse) {
        return Observable.from(tagTopAlbumsResponse.getAlbums().getAlbum())
                .map(new TagsTopAlbumMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
