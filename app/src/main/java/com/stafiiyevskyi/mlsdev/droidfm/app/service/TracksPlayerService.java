package com.stafiiyevskyi.mlsdev.droidfm.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.MediaPlayerWrapper;

/**
 * Created by oleksandr on 29.04.16.
 */
public class TracksPlayerService extends Service {

    private static final int FLAG_FROM_NOTIFICATION = 2;
    public static final int FLAG_FROM_WIDGET = 1;

    public static final String INTENT_PLAYER_KEY = "FLAG";

    public static final String ACTION_NOTIFICATION_PLAY_PAUSE = "action_notification_play_pause";
    public static final String ACTION_NOTIFICATION_FAST_FORWARD = "action_notification_fast_forward";
    public static final String ACTION_NOTIFICATION_REWIND = "action_notification_rewind";


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
        handleIntent(intent);
        return START_STICKY;
    }

    public void onDestroy() {
        MediaPlayerWrapper.getInstance().releaseMP();
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equalsIgnoreCase(ACTION_NOTIFICATION_PLAY_PAUSE)) {

                boolean mIsPlaying;
                if (intent.getIntExtra(INTENT_PLAYER_KEY, 0) == FLAG_FROM_NOTIFICATION) {
                    if (MediaPlayerWrapper.getInstance().getCurrentState().equals(MediaPlayerWrapper.State.Playing)) {
                        mIsPlaying = false;
                    } else {
                        mIsPlaying = true;
                    }
                    MediaPlayerWrapper.getInstance().playTrack(true);
                } else {
                    if (MediaPlayerWrapper.getInstance().getCurrentState().equals(MediaPlayerWrapper.State.Playing) || MediaPlayerWrapper.getInstance().getCurrentState().equals(MediaPlayerWrapper.State.Preparing)) {
                        mIsPlaying = true;
                    } else {
                        mIsPlaying = false;
                    }
                }
                showNotification(mIsPlaying);
            } else if (intent.getAction().equalsIgnoreCase(ACTION_NOTIFICATION_FAST_FORWARD)) {


            } else if (intent.getAction().equalsIgnoreCase(ACTION_NOTIFICATION_REWIND)) {


            }
        }
    }

    private void showNotification(boolean isPlaying) {
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_music_circle_white_36dp)
                .setContentTitle(getString(R.string.app_name))
                .build();


        notification.bigContentView = getExpandedView(isPlaying);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }

    private RemoteViews getExpandedView(boolean isPlaying) {
        RemoteViews customView = new RemoteViews(getPackageName(), R.layout.view_notification);
        if (isPlaying) {
            customView.setImageViewResource(R.id.ib_play_pause, R.drawable.ic_pause_white_36dp);
        } else {
            customView.setImageViewResource(R.id.ib_play_pause, R.drawable.ic_play_white_36dp);
        }
        customView.setImageViewResource(R.id.ib_rewind, R.drawable.ic_rewind_white_36dp);
        customView.setImageViewResource(R.id.ib_fast_forward, R.drawable.ic_fast_forward_white_36dp);
        customView.setTextViewText(R.id.tv_notification, MediaPlayerWrapper.getInstance().getCurrentTrack().getmTrackName());

        Intent intent = new Intent(getApplicationContext(), TracksPlayerService.class);
        intent.putExtra(INTENT_PLAYER_KEY, FLAG_FROM_NOTIFICATION);

        intent.setAction(ACTION_NOTIFICATION_PLAY_PAUSE);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        customView.setOnClickPendingIntent(R.id.ib_play_pause, pendingIntent);

        intent.setAction(ACTION_NOTIFICATION_FAST_FORWARD);
        pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        customView.setOnClickPendingIntent(R.id.ib_fast_forward, pendingIntent);

        intent.setAction(ACTION_NOTIFICATION_REWIND);
        pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        customView.setOnClickPendingIntent(R.id.ib_rewind, pendingIntent);

        return customView;
    }


}
