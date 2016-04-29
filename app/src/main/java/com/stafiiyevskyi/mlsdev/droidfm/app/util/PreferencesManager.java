package com.stafiiyevskyi.mlsdev.droidfm.app.util;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesManager {

    private static final String PREF_NAME = "settings";

    private static final String ACCESS_TOKEN = "access_token";
    private static final String USER_ID = "user_id";

    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;


    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeTaskInstance(..) method first.");
        }
        return sInstance;
    }

    public SharedPreferences getPreferences() {
        return mPref;
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .commit();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }

    public String getAccessToken() {
        return mPref.getString(ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken) {
        mPref.edit()
                .putString(ACCESS_TOKEN, accessToken)
                .apply();
    }

    public String getUserId() {
        return mPref.getString(USER_ID, null);
    }

    public void setUserId(String userId) {
        mPref.edit()
                .putString(USER_ID, userId)
                .apply();
    }
}
