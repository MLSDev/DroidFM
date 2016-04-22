package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tracks.TopChartTracks;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ChartTopTrackListMapper implements Func1<TopChartTracks, List<TopTrackEntity>> {
    @Override
    public List<TopTrackEntity> call(TopChartTracks topChartTracks) {
        return Observable.from(topChartTracks.getTracks().getTrack())
                .map(new ChartTopTrackMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
