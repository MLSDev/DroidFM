package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.ArtistTopTrack;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.ArtistTopTracks;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 21.04.16.
 */
public class TopTracksMapper implements Func1<ArtistTopTracks, List<TrackEntity>> {
    @Override
    public List<TrackEntity> call(ArtistTopTracks artistTopTracks) {
        return Observable.from(artistTopTracks.getToptracks().getTrack())
                .map(new TrackMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
