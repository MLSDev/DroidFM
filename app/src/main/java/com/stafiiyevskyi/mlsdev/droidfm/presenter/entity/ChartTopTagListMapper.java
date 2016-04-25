package com.stafiiyevskyi.mlsdev.droidfm.presenter.entity;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.TopChartTags;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.chart.ChartTopTagMapper;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ChartTopTagListMapper implements Func1<TopChartTags, List<TopTagEntity>> {
    @Override
    public List<TopTagEntity> call(TopChartTags topChartTags) {
        return Observable.from(topChartTags.getTags().getTag())
                .map(new ChartTopTagMapper())
                .toList()
                .toBlocking()
                .first();
    }
}
