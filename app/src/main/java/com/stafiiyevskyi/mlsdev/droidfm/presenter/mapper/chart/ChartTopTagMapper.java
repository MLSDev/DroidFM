package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.chart;

import com.stafiiyevskyi.mlsdev.droidfm.data.dto.tag.Tag;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTagEntity;

import rx.functions.Func1;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ChartTopTagMapper implements Func1<Tag, TopTagEntity> {
    @Override
    public TopTagEntity call(Tag tag) {
        TopTagEntity entity = new TopTagEntity();
        entity.setName(tag.getName());
        entity.setReach(tag.getReach());
        entity.setTaggings(tag.getTaggings());

        return entity;
    }
}
