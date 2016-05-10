package com.stafiiyevskyi.mlsdev.droidfm.app;

import android.app.Application;

import com.stafiiyevskyi.mlsdev.droidfm.app.util.PreferencesManager;
import com.stafiiyevskyi.mlsdev.droidfm.data.dao.DroidFMRealmModule;
import com.vk.sdk.VKSdk;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by oleksandr on 28.04.16.
 */
public class DroidFMApplication extends Application {

    private static DroidFMApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        VKSdk.initialize(getApplicationContext());
        PreferencesManager.initializeInstance(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext()).setModules(new DroidFMRealmModule()).build();
        Realm.setDefaultConfiguration(config);
    }

    public static DroidFMApplication getInstance() {
        return instance;
    }
}
