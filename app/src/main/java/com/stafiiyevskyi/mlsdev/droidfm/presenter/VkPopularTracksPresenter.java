package com.stafiiyevskyi.mlsdev.droidfm.presenter;

import com.stafiiyevskyi.mlsdev.droidfm.app.util.Genre;

/**
 * Created by oleksandr on 19.05.16.
 */
public interface VkPopularTracksPresenter extends Presenter {
    void getVkPopularTrack(Genre genre, int offset, int count);
}
