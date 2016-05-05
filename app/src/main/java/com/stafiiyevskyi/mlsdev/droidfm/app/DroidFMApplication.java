package com.stafiiyevskyi.mlsdev.droidfm.app;

import android.app.Application;

import com.stafiiyevskyi.mlsdev.droidfm.app.util.PreferencesManager;
import com.vk.sdk.VKSdk;

/**
 * Created by oleksandr on 28.04.16.
 */
public class DroidFMApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
        PreferencesManager.initializeInstance(getApplicationContext());
    }
}
