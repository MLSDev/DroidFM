package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsTracksAdapter extends RecyclerView.Adapter<AlbumsTracksAdapter.TopTrackVH> {
    private static final int ANIMATED_ITEMS_COUNT = 10;
    private int lastAnimatedPosition = -1;

    private List<TrackEntity> data = new ArrayList<>();
    private OnTopTrackClickListener listener;
    private Context context;

    public AlbumsTracksAdapter(OnTopTrackClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<TrackEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<TrackEntity> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public TopTrackVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_track_artist, parent, false);
        return new TopTrackVH(view);
    }

    @Override
    public void onBindViewHolder(TopTrackVH holder, int position) {
        runEnterAnimation(holder.itemView, position);
        TrackEntity entity = data.get(position);
        holder.bindTrackName(entity.getName());
    }



    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(AnimationUtil.getScreenHeight(context));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnTopTrackClickListener {
        void onTrackClick(TrackEntity topTrack);
    }

    public class TopTrackVH extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvTopTrackName;

        public TopTrackVH(View itemView) {
            super(itemView);
            mTvTopTrackName = (AppCompatTextView) itemView.findViewById(R.id.tv_track_name);
            itemView.setOnClickListener(view -> listener.onTrackClick(data.get(getAdapterPosition())));
        }

        public void bindTrackName(String trackName) {
            mTvTopTrackName.setText(trackName);
        }

    }
}