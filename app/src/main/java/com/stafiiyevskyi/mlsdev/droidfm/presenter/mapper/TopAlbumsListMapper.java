package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.album.ArtistTopAlbumsResponse;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TopAlbumsListMapper implements Func1<ArtistTopAlbumsResponse, List<AlbumEntity>> {
    @Override
    public List<AlbumEntity> call(ArtistTopAlbumsResponse artistTopAlbumsResponse) {
        return Observable.from(artistTopAlbumsResponse.getTopalbums().getAlbum())
                .map(new TopAlbumMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
