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

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventDownloadCurrentTrack;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventDownloadTrack;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.MediaPlayerWrapper;

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
        String trackName = event.getTrack().getArtistName() + " - " + event.getTrack().getName();
        String trackUrl = event.getTrackUrl();
        File trackFile = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), trackName + getString(R.string.mp3_file));
        if (!trackFile.exists())
            downloadTrack(trackName, trackUrl);
        else
            Toast.makeText(getApplicationContext(), getString(R.string.download_was_downloaded_toast), Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onDownloadCurrentTrack(EventDownloadCurrentTrack event) {
        if (MediaPlayerWrapper.getInstance().getCurrentTrack() != null) {
            String trackName = MediaPlayerWrapper.getInstance().getCurrentTrack().getmArtistName() + " - " + MediaPlayerWrapper.getInstance().getCurrentTrack().getmTrackName();
            String trackUrl = MediaPlayerWrapper.getInstance().getCurrentTrack().getmTrackUrl();
            File trackFile = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), trackName + getString(R.string.mp3_file));
            if (!trackFile.exists())
                downloadTrack(trackName, trackUrl);
            else
                Toast.makeText(getApplicationContext(), getString(R.string.download_was_downloaded_toast), Toast.LENGTH_LONG).show();
        }
    }

    private void downloadTrack(String trackName, String trackUrl) {
        File file = new File(trackUrl);
        if (!file.exists()) {
            File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), trackName + getString(R.string.mp3_file));
            if (!mediaStorageDir.exists()) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(trackUrl));
                request.setTitle(getString(R.string.download_title) + trackName);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                }
                request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_MUSIC, trackName + getString(R.string.mp3_file));
                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
                Toast.makeText(this, R.string.download_start_toast, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.download_was_downloaded_toast, Toast.LENGTH_LONG).show();
            }
        }

    }
}
