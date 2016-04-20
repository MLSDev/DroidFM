package com.stafiiyevskyi.mlsdev.droidfm.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by oleksandr on 20.04.16.
 */
public class BasePresenter implements Presenter {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public void stop() {
        compositeSubscription.unsubscribe();
    }

    @Override
    public void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }
}
