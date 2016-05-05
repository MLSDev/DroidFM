package com.stafiiyevskyi.mlsdev.droidfm.app.player;

import android.media.MediaPlayer;

import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventCurrentTrackPause;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

/**
 * Created by oleksandr on 04.05.16.
 */
public class MediaPlayerWrapper implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {

    private static MediaPlayerWrapper mInstance;
    private TrackPlayerEntity mCurrentTrack = new TrackPlayerEntity();
    private State mState = State.Retrieving;
    private MediaPlayer mediaPlayer;

    public enum State {
        Retrieving,
        Stopped,
        Preparing,
        Playing,
        Paused
    }

    private List<TrackPlayerEntity> mPlaylist;
    private boolean isFromPlaylist = false;
    private int mTrackPlaylistIndex;

    private MediaPlayerWrapper() {

    }

    public static synchronized MediaPlayerWrapper getInstance() {
        if (mInstance == null) mInstance = new MediaPlayerWrapper();
        return mInstance;
    }

    public TrackPlayerEntity getCurrentTrack() {
        return mCurrentTrack;
    }

    public void init() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mState = State.Retrieving;
    }

    public void releaseMP() {
        if (mediaPlayer.isPlaying())
            stopPlayer();
        mediaPlayer.release();
        mState = State.Retrieving;
    }

    public void playTrack(TrackPlayerEntity mCurrentTrack) {

        if (isTrackPlaying(mCurrentTrack.getmTrackUrl())) {
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
            EventBus.getDefault().post(new EventCurrentTrackPause());
        } else {
            this.mCurrentTrack = mCurrentTrack;

            EventBus.getDefault().post(mCurrentTrack);
            switch (mState) {
                case Stopped:
                    preparedPlayer();
                    break;
                case Paused:
                    preparedPlayer();
                    break;
                case Playing:
                    preparedPlayer();
                    break;
                case Preparing:
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

    public void playPlaylist(List<TrackPlayerEntity> tracks) {
        isFromPlaylist = true;
        mPlaylist = tracks;
        mTrackPlaylistIndex = 0;

        playTrack(mPlaylist.get(mTrackPlaylistIndex));
    }

    public void nextTrack() {
        if (mTrackPlaylistIndex <= (mPlaylist.size() - 1)) {
            mCurrentTrack = mPlaylist.get(mTrackPlaylistIndex);
            preparedPlayer();
        }
    }

    public void previousTrack() {
        if (mTrackPlaylistIndex >= 0) {
            mCurrentTrack = mPlaylist.get(mTrackPlaylistIndex);
            preparedPlayer();
        }
    }

    public boolean isTrackPlaying(String trackUrl) {
        return trackUrl.equalsIgnoreCase(mCurrentTrack.getmTrackUrl());
    }


    public void pausePlayer() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
        mState = State.Paused;
        EventBus.getDefault().post(new EventCurrentTrackPause());
    }

    public void stopPlayer() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mState = State.Stopped;
    }

    public void startPlayer() {
        mediaPlayer.start();
        mState = State.Playing;
    }

    public void preparedPlayer() {
        stopPlayer();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(mCurrentTrack.getmTrackUrl());
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
        if (isFromPlaylist) {
            mTrackPlaylistIndex = mTrackPlaylistIndex + 1;
            if (mTrackPlaylistIndex <= (mPlaylist.size() - 1)) {
                mCurrentTrack = mPlaylist.get(mTrackPlaylistIndex);
                preparedPlayer();
            } else {
                releaseMP();
            }
        } else {
            releaseMP();
        }
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
