package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventCurrentTrackPause;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventTrackStart;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.MediaPlayerWrapper;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.TrackPlayerEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by oleksandr on 05.05.16.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistVH> {

    private static final int PAUSED = 101;
    private static final int PLAYING = 102;

    private List<TrackPlayerEntity> mData;
    private OnPlaylistTrackClick mListener;

    public PlaylistAdapter(OnPlaylistTrackClick mListener) {
        this.mListener = mListener;
        EventBus.getDefault().register(this);
    }

    public void setData(List<TrackPlayerEntity> tracks) {
        this.mData = tracks;
        notifyDataSetChanged();
    }

    public void addData(List<TrackPlayerEntity> tracks) {
        this.mData.addAll(tracks);
        notifyDataSetChanged();
    }

    @Override
    public PlaylistVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case PAUSED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_track_paused, parent, false);
                break;
            case PLAYING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_track_playing, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_track_paused, parent, false);
                break;
        }
        return new PlaylistVH(view);
    }

    @Override
    public void onBindViewHolder(PlaylistVH holder, int position) {
        TrackPlayerEntity track = mData.get(position);
        holder.bindTrackName(track.getmArtistName().concat(" - ").concat(track.getmTrackName()));
    }

    @Override
    public int getItemCount() {
        if (mData != null) return mData.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).isPaused()) {
            return PAUSED;
        } else return PLAYING;
    }

    @Subscribe
    public void pauseTrackEvent(EventCurrentTrackPause event) {
        if (mData != null) {
            for (TrackPlayerEntity trackPlayerEntity : mData) {
                if (trackPlayerEntity.getmTrackName().equalsIgnoreCase(event.getTrack().getmTrackName())) {
                    if (MediaPlayerWrapper.getInstance().getCurrentTrack().isPaused()) {
                        trackPlayerEntity.setPaused(true);
                    } else {
                        trackPlayerEntity.setPaused(false);
                    }
                } else {
                    trackPlayerEntity.setPaused(true);
                }
            }
            notifyDataSetChanged();
        }
    }

    @Subscribe
    public void startTrackEvent(TrackPlayerEntity event) {
        for (TrackPlayerEntity trackPlayerEntity : mData) {
            if (trackPlayerEntity.getmTrackName().equalsIgnoreCase(event.getmTrackName())) {
                trackPlayerEntity.setPaused(false);
            } else {
                trackPlayerEntity.setPaused(true);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnPlaylistTrackClick {
        void onPlaylistTrackClick(TrackPlayerEntity trackPlayerEntity);
    }

    public class PlaylistVH extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvTrackName;

        public PlaylistVH(View itemView) {
            super(itemView);
            mTvTrackName = (AppCompatTextView) itemView.findViewById(R.id.tv_track_name);
            itemView.setOnClickListener(view -> {

//                for (TrackPlayerEntity trackPlayerEntity : mData) {
//                    if (!trackPlayerEntity.getmTrackName().equalsIgnoreCase(mData.get(getAdapterPosition()).getmTrackName()))
//                        trackPlayerEntity.setPaused(true);
//                }
//
//                if (mData.get(getAdapterPosition()).isPaused()) {
//                    mData.get(getAdapterPosition()).setPaused(false);
//                } else {
//                    mData.get(getAdapterPosition()).setPaused(true);
//                }

                mListener.onPlaylistTrackClick(mData.get(getAdapterPosition()));
            });
        }

        public void bindTrackName(String trackName) {
            mTvTrackName.setText(trackName);
        }
    }
}
