package com.stafiiyevskyi.mlsdev.droidfm.data.model.impl;

import com.stafiiyevskyi.mlsdev.droidfm.app.util.PreferencesManager;
import com.stafiiyevskyi.mlsdev.droidfm.data.api.LastFMRestClient;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VkTrackNewResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.VKTrackModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandr on 29.04.16.
 */
public class VKTrackModelImpl implements VKTrackModel {
    @Override
    public Observable<VkTrackNewResponse> getVKTrack(String trackSearch) {
        String fullUrl = "https://api.vk.com/method/audio.search?q=" + trackSearch + "&count=1&access_token=" + PreferencesManager.getInstance().getAccessToken()+"&v=5.52";
        return LastFMRestClient.getService().getTrackStream(fullUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
