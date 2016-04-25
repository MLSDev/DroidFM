package com.stafiiyevskyi.mlsdev.droidfm.presenter.view;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTagEntity;

import java.util.List;

/**
 * Created by oleksandr on 25.04.16.
 */
public interface ChartTopTagScreenView extends BaseScreenView{

    void showTopTags(List<TopTagEntity> tags);
}
