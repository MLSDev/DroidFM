package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventCurrentTrackPause;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventSynchronizingAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.MediaPlayerWrapper;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.TrackPlayerEntity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.FileTrackUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by oleksandr on 05.05.16.
 */
public class AlbumPlaylistAdapter extends RecyclerView.Adapter<AlbumPlaylistAdapter.PlaylistVH> {

    private static final int PAUSED = 101;
    private static final int PLAYING = 102;

    private List<TrackPlayerEntity> data;
    private OnPlaylistTrackClick listener;
    private Context context;

    public AlbumPlaylistAdapter(OnPlaylistTrackClick listener) {
        this.listener = listener;
        EventBus.getDefault().register(this);
    }

    public void setData(List<TrackPlayerEntity> tracks) {
        this.data = tracks;
        notifyDataSetChanged();
    }

    public void addData(List<TrackPlayerEntity> tracks) {
        this.data.addAll(tracks);
        notifyDataSetChanged();
    }

    @Override
    public PlaylistVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
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
        TrackPlayerEntity track = data.get(position);
        if (track.getmArtistName()!=null){
            holder.bindTrackName(track.getmArtistName().concat(" - ").concat(track.getmTrackName()));
        } else {
            holder.bindTrackName(track.getmTrackName());
        }

    }

    @Override
    public int getItemCount() {
        if (data != null) return data.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).isPaused()) {
            return PAUSED;
        } else return PLAYING;
    }

    @Subscribe
    public void pauseTrackEvent(EventCurrentTrackPause event) {
        if (data != null) {
            for (TrackPlayerEntity trackPlayerEntity : data) {
                if (trackPlayerEntity.getmTrackName().equalsIgnoreCase(event.getTrack().getmTrackName())) {
                    if (MediaPlayerWrapper.getInstance().getCurrentTrack().isPaused()) {
                        trackPlayerEntity.setPaused(true);
                    } else {
                        trackPlayerEntity.setPaused(false);
                        notifyDataSetChanged();
                    }
                } else {
                    trackPlayerEntity.setPaused(true);
                }
            }
            notifyDataSetChanged();
        }
    }

    @Subscribe
    public void synchronizedEvent(EventSynchronizingAdapter event) {
        if (data != null) {
            for (TrackPlayerEntity trackPlayerEntity : data) {
                if (trackPlayerEntity.getmTrackName().equalsIgnoreCase(MediaPlayerWrapper.getInstance().getCurrentTrack().getmTrackName())) {
                    if (MediaPlayerWrapper.getInstance().getCurrentTrack().isPaused()) {
                        trackPlayerEntity.setPaused(true);
                    } else {
                        trackPlayerEntity.setPaused(false);
                        notifyDataSetChanged();
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
        if (data != null) {
            for (TrackPlayerEntity trackPlayerEntity : data) {
                if (trackPlayerEntity.getmTrackName().equalsIgnoreCase(event.getmTrackName())) {
                    trackPlayerEntity.setPaused(false);
                    notifyDataSetChanged();
                } else {
                    trackPlayerEntity.setPaused(true);
                }
            }
            notifyDataSetChanged();
        }
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
                TrackPlayerEntity track = data.get(getAdapterPosition());
                Pair<Boolean, Uri> pair = FileTrackUtil.isTrackExist(context, track.getmArtistName(), track.getmTrackName());
                if (pair.first) {
                    track.setmTrackUrl(pair.second.getPath());
                }
                listener.onPlaylistTrackClick(track);
            });
        }

        public void bindTrackName(String trackName) {
            mTvTrackName.setText(trackName);
        }
    }
}
