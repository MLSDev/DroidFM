package com.stafiiyevskyi.mlsdev.droidfm.app.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventDownloadCurrentTrack;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventDownloadTrack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

/**
 * Created by oleksandr on 13.05.16.
 */
public class DownloadService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onDownloadTrackEvent(EventDownloadTrack event) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), event.getTrack().getArtistName() + " - " + event.getTrack().getName() + ".mp3");
        if (!mediaStorageDir.exists()) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(event.getTrackUrl()));
            request.setTitle("DroidFM save track " + event.getTrack().getArtistName() + " - " + event.getTrack().getName());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_MUSIC, event.getTrack().getArtistName() + " - " + event.getTrack().getName() + ".mp3");
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
            Toast.makeText(this, "Downloading started", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "The track was downloaded earlier", Toast.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void onDownloadCurrentTrack(EventDownloadCurrentTrack event) {

    }
}
