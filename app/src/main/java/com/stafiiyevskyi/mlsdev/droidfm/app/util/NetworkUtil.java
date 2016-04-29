package com.stafiiyevskyi.mlsdev.droidfm.app.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by oleksandr on 29.04.16.
 */
public class NetworkUtil {
    public NetworkUtil() {
    }

    public static boolean isNetworkConnected(Context mContext) {
        if (mContext != null) {
            ConnectivityManager manager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager.getActiveNetworkInfo() != null) {
                return (manager.getActiveNetworkInfo().isAvailable() && manager
                        .getActiveNetworkInfo().isConnected());
            }
        }
        return false;
    }
}
