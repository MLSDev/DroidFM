package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.similar.track.Track;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 19.05.16.
 */
public class ListSimilarTrackToListTopTrackMapper implements Func1<List<Track>, List<TopTrackEntity>> {
    @Override
    public List<TopTrackEntity> call(List<Track> tracks) {
        return Observable.from(tracks)
                .map(new SimilarTrackToTagTopTrackMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
