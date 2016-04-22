package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.search.TrackSearchResponse;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 22.04.16.
 */
public class SearchTracksListMapper implements Func1<TrackSearchResponse, List<TopTrackEntity>> {
    @Override
    public List<TopTrackEntity> call(TrackSearchResponse searchTracks) {
        return Observable.from(searchTracks.getResults().getTrackmatches().getTrack())
                .map(new SearchTrackMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
