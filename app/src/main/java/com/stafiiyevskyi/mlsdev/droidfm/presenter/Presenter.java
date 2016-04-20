package com.stafiiyevskyi.mlsdev.droidfm.presenter;

import rx.Subscription;

/**
 * Created by oleksandr on 20.04.16.
 */
public interface Presenter {

    void stop();

    void addSubscription(Subscription subscription);
}
