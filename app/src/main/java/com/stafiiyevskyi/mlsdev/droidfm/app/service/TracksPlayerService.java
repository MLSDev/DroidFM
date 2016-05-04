package com.stafiiyevskyi.mlsdev.droidfm.app.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.stafiiyevskyi.mlsdev.droidfm.app.player.MediaPlayerWrapper;

import java.io.IOException;

/**
 * Created by oleksandr on 29.04.16.
 */
public class TracksPlayerService extends Service {

    private static TracksPlayerService mInstance;

    public static TracksPlayerService getInstance() {
        return mInstance;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        MediaPlayerWrapper.getInstance().init();
        mInstance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    public void onDestroy() {
        MediaPlayerWrapper.getInstance().stopPlayer();
        MediaPlayerWrapper.getInstance().releaseMP();
    }
}
