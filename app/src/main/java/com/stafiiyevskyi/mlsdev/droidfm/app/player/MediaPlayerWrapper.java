package com.stafiiyevskyi.mlsdev.droidfm.app.player;

import android.media.MediaPlayer;

import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventCurrentTrackPause;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VKTrackResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.dto.vktrack.VkTrackItemResponse;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.VKTrackModel;
import com.stafiiyevskyi.mlsdev.droidfm.data.model.impl.VKTrackModelImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import rx.Observer;

/**
 * Created by oleksandr on 04.05.16.
 */
public class MediaPlayerWrapper implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {

    private static MediaPlayerWrapper mInstance;
    private TrackPlayerEntity mCurrentTrack = new TrackPlayerEntity();
    private State mState = State.Retrieving;
    private MediaPlayer mediaPlayer;
    private VKTrackModel mVKTrackModel;

    public enum State {
        Retrieving,
        Stopped,
        Preparing,
        Playing,
        Paused
    }


    private MediaPlayerWrapper() {
        mVKTrackModel = new VKTrackModelImpl();
    }

    private boolean isFromAlbum;

    public boolean isFromAlbum() {
        return isFromAlbum;
    }

    public void setFromAlbum(boolean fromAlbum) {
        isFromAlbum = fromAlbum;
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

    private void chooseCurrentTrack() {
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
        EventBus.getDefault().post(new EventCurrentTrackPause(mCurrentTrack));
    }

    private void chooseAnotherTrack(TrackPlayerEntity mCurrentTrack) {
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

    public void playTrack(boolean isFromNotification) {
        playTrack(mCurrentTrack, isFromNotification);
    }

    public void playTrack(TrackPlayerEntity mCurrentTrack, boolean isFromNotification) {
        mCurrentTrack.setFromNotification(isFromNotification);
        if (isTrackPlaying(mCurrentTrack.getmTrackName())) {
            this.mCurrentTrack.setFromNotification(isFromNotification);
            chooseCurrentTrack();
        } else {
            if (mCurrentTrack.getmTrackUrl() == null) {
                this.mCurrentTrack = mCurrentTrack;
                String search = mCurrentTrack.getmArtistName() + " - " + mCurrentTrack.getmTrackName();
                mVKTrackModel.getVKTrack(search).subscribe(new Observer<VKTrackResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VKTrackResponse vkTrackResponse) {
                        if (vkTrackResponse.getResponse() != null) {
                            VkTrackItemResponse itemResponse = vkTrackResponse.getResponse()[0];
                            MediaPlayerWrapper.this.mCurrentTrack.setmTrackUrl(itemResponse.getUrl());
                            preparedPlayer();
                        } else {
                            stopPlayer();
                        }
                    }
                });

            } else {
                chooseAnotherTrack(mCurrentTrack);
            }
        }
    }

    public boolean isTrackPlaying(String trackName) {
        return trackName.equalsIgnoreCase(mCurrentTrack.getmTrackName());
    }

    public void pausePlayer() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
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
        stopPlayer();
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