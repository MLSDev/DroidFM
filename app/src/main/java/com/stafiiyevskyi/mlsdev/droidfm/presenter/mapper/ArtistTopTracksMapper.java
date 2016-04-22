package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.ArtistTopTracks;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ArtistTopTracksMapper implements Func1<ArtistTopTracks, List<TopTrackEntity>> {
    @Override
    public List<TopTrackEntity> call(ArtistTopTracks artistTopTracks) {
        return Observable.from(artistTopTracks.getToptracks().getTrack())
                .map(new ArtistTopTrackMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
