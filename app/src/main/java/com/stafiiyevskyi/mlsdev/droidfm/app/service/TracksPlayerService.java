package com.stafiiyevskyi.mlsdev.droidfm.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.MediaPlayerWrapper;

/**
 * Created by oleksandr on 29.04.16.
 */
public class TracksPlayerService extends Service {

    private static final int FLAG_FROM_NOTIFICATION = 2;
    public static final int FLAG_FROM_WIDGET = 1;
    private static final int NOTIFICATION_ID = 1;
    public static final String INTENT_PLAYER_KEY = "FLAG";

    public static final String ACTION_NOTIFICATION_PLAY_PAUSE = "action_notification_play_pause";
    public static final String ACTION_NOTIFICATION_FAST_FORWARD = "action_notification_fast_forward";
    public static final String ACTION_NOTIFICATION_REWIND = "action_notification_rewind";


    private AudioManager mAudioManager;
    private AFListener mAFListener;
    private NotificationManager notificationManager;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mAFListener = new AFListener();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int requestResult = mAudioManager.requestAudioFocus(mAFListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        Log.e(TracksPlayerService.class.getSimpleName(), "Music request focus, result: " + requestResult);
        MediaPlayerWrapper.getInstance().init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mAFListener != null)
            mAudioManager.abandonAudioFocus(mAFListener);
        MediaPlayerWrapper.getInstance().releaseMP();
        notificationManager.cancel(NOTIFICATION_ID);
        Log.e("Service", "onDestroy");
        super.onDestroy();
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
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private RemoteViews getExpandedView(boolean isPlaying) {
        RemoteViews customView = new RemoteViews(getPackageName(), R.layout.view_notification);
        if (isPlaying) {
            customView.setImageViewResource(R.id.ib_play_pause, R.drawable.ic_pause_white_36dp);
        } else {
            customView.setImageViewResource(R.id.ib_play_pause, R.drawable.ic_play_white_36dp);
        }

        if (MediaPlayerWrapper.getInstance().getCurrentTrack().getmArtistName() != null) {
            customView.setTextViewText(R.id.tv_notification, MediaPlayerWrapper.getInstance().getCurrentTrack().getmArtistName() + " - " + MediaPlayerWrapper.getInstance().getCurrentTrack().getmTrackName());
        } else {
            customView.setTextViewText(R.id.tv_notification, MediaPlayerWrapper.getInstance().getCurrentTrack().getmTrackName());
        }

        Intent intent = new Intent(getApplicationContext(), TracksPlayerService.class);
        intent.putExtra(INTENT_PLAYER_KEY, FLAG_FROM_NOTIFICATION);

        intent.setAction(ACTION_NOTIFICATION_PLAY_PAUSE);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        customView.setOnClickPendingIntent(R.id.ib_play_pause, pendingIntent);

        return customView;
    }

    class AFListener implements AudioManager.OnAudioFocusChangeListener {


        @Override
        public void onAudioFocusChange(int focusChange) {
            String event = "";
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS:
                    event = "AUDIOFOCUS_LOSS";
                    if (MediaPlayerWrapper.getInstance().isMusicPlay())
                        MediaPlayerWrapper.getInstance().setIsPauseFromApp(false);
                    MediaPlayerWrapper.getInstance().pausePlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    event = "AUDIOFOCUS_LOSS_TRANSIENT";
                    if (MediaPlayerWrapper.getInstance().isMusicPlay())
                        MediaPlayerWrapper.getInstance().setIsPauseFromApp(false);
                    MediaPlayerWrapper.getInstance().pausePlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    event = "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK";
                    MediaPlayerWrapper.getInstance().changeVolume(0.5f, 0.5f);
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    event = "AUDIOFOCUS_GAIN";
                    if (!MediaPlayerWrapper.getInstance().isMusicPlay() && !MediaPlayerWrapper.getInstance().isIsPauseFromApp())
                        MediaPlayerWrapper.getInstance().startPlayer();
                    MediaPlayerWrapper.getInstance().changeVolume(1.0f, 1.0f);
                    break;
            }
            Log.e(MediaPlayerWrapper.class.getSimpleName(), " onAudioFocusChange: " + event);
        }
    }

}
