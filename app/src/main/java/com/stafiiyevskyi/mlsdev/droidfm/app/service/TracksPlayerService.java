package com.stafiiyevskyi.mlsdev.droidfm.app.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

/**
 * Created by oleksandr on 29.04.16.
 */
public class TracksPlayerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {
    private MediaPlayer mediaPlayer;

    private static TracksPlayerService mInstance;
    private String mTrackUrl;
    private State mState;

    public static TracksPlayerService getInstance() {
        return mInstance;
    }

    public enum State {
        Retrieving,
        Stopped,
        Preparing,
        Playing,
        Paused
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mInstance = this;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mState = State.Retrieving;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    public void onDestroy() {
        stopPlayer();
        releaseMP();
    }

    private void releaseMP() {
        if (mediaPlayer.isPlaying())
            stopPlayer();
        mediaPlayer.release();
        mState = State.Retrieving;
    }

    public void playTrack(String mCurrentTrackUrl) {
        if (isTrackPlaying(mCurrentTrackUrl)) {
            switch (mState) {
                case Stopped:
                    preparedPlayer();
                    break;
                case Paused:
                    startPlayer();
                    break;
                case Playing:
                    pausePlayer();
                    break;
                case Preparing:
                    stopPlayer();
                    preparedPlayer();
                    break;
                case Retrieving:
                    preparedPlayer();
                    break;
                default:
                    break;
            }
        } else {
            this.mTrackUrl = mCurrentTrackUrl;
            switch (mState) {
                case Stopped:
                    stopPlayer();
                    preparedPlayer();
                    break;
                case Paused:
                    stopPlayer();
                    preparedPlayer();
                    break;
                case Playing:
                    stopPlayer();
                    preparedPlayer();
                    break;
                case Preparing:
                    stopPlayer();
                    preparedPlayer();
                    break;
                case Retrieving:
                    preparedPlayer();
                    break;
                default:
                    break;
            }
        }
    }

    public boolean isTrackPlaying(String trackUrl) {
        return trackUrl.equalsIgnoreCase(this.mTrackUrl);
    }


    private void pausePlayer() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
        mState = State.Paused;
    }

    private void stopPlayer() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mState = State.Stopped;
    }

    private void startPlayer() {
        mediaPlayer.start();
        mState = State.Playing;
    }

    private void preparedPlayer() {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(mTrackUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mState = State.Preparing;
    }


    public State getCurrentState() {
        return mState;
    }

    public int getPlayerTotalDuration() {
        if (mState.equals(State.Paused) || mState.equals(State.Playing))
            return mediaPlayer.getDuration();
        return 0;
    }

    public int getPlayerCurrentPosition() {
        if (mState.equals(State.Paused) || mState.equals(State.Playing))
            return mediaPlayer.getCurrentPosition();
        return 0;
    }

    public void seekPlayerTo(int value) {
        if (mState.equals(State.Paused) || mState.equals(State.Playing))
            mediaPlayer.seekTo(value);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        releaseMP();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        startPlayer();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }
}
