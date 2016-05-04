package com.stafiiyevskyi.mlsdev.droidfm.app.player;

import android.media.MediaPlayer;

import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventCurrentTrackPause;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventTrackStart;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by oleksandr on 04.05.16.
 */
public class MediaPlayerWrapper implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {

    private static MediaPlayerWrapper mInstance;
    private String mTrackUrl;
    private String mTrackName;
    private String mAlbumImageUrl;
    private String mArtistName;
    private State mState = State.Retrieving;
    private MediaPlayer mediaPlayer;

    public enum State {
        Retrieving,
        Stopped,
        Preparing,
        Playing,
        Paused
    }

    private MediaPlayerWrapper() {

    }

    public synchronized static MediaPlayerWrapper getInstance() {
        if (mInstance == null) mInstance = new MediaPlayerWrapper();
        return mInstance;
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

    public void playTrack(String mCurrentTrackUrl, String artistName, String mTrackName, String albumImageUrl) {
        setmArtistName(artistName);
        setmTrackName(mTrackName);
        setmAlbumImageUrl(albumImageUrl);
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
            EventTrackStart event = new EventTrackStart();
            event.setAlbumImage(albumImageUrl);
            event.setArtistName(artistName);
            event.setTrackName(mTrackName);
            event.setTrackUrl(mCurrentTrackUrl);
            EventBus.getDefault().post(event);

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


    public void pausePlayer() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
        EventBus.getDefault().post(new EventCurrentTrackPause());
        mState = State.Paused;
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

    public String getmTrackName() {
        return mTrackName;
    }

    public void setmTrackName(String mTrackName) {
        this.mTrackName = mTrackName;
    }

    public String getmAlbumImageUrl() {
        return mAlbumImageUrl;
    }

    public void setmAlbumImageUrl(String mAlbumImageUrl) {
        this.mAlbumImageUrl = mAlbumImageUrl;
    }

    public String getmArtistName() {
        return mArtistName;
    }

    public void setmArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    public String getTrackUrl() {
        return mTrackUrl;
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
