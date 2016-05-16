package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;

import java.util.List;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteTracksAdapter extends RecyclerView.Adapter<FavoriteTracksAdapter.FavoriteTrackVH> {
    private static final int ANIMATED_ITEMS_COUNT = 10;
    private int lastAnimatedPosition = -1;

    private List<FavoriteTrackEntity> mData;
    private OnFavoriteTrackClickListener mListener;
    private Context mContext;

    public FavoriteTracksAdapter(OnFavoriteTrackClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public FavoriteTrackVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
        return new FavoriteTrackVH(view);
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(AnimationUtil.getScreenHeight(mContext));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    @Override
    public void onBindViewHolder(FavoriteTrackVH holder, int position) {
        runEnterAnimation(holder.itemView,position);
        FavoriteTrackEntity track = mData.get(position);
        holder.bindTrackName(track.getTrackName());
        holder.bindArtistName(track.getArtistName());
    }

    @Override
    public int getItemCount() {
        if (mData != null) return mData.size();
        return 0;
    }

    public void addData(List<FavoriteTrackEntity> tracks) {
        mData.addAll(tracks);
        notifyDataSetChanged();
    }

    public void setData(List<FavoriteTrackEntity> tracks) {
        mData = tracks;
        notifyDataSetChanged();
    }

    public interface OnFavoriteTrackClickListener {
        void onFavoriteTrackClick(FavoriteTrackEntity track);
    }

    class FavoriteTrackVH extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvTrackName;
        private AppCompatTextView mTvArtistName;

        public FavoriteTrackVH(View itemView) {
            super(itemView);
            mTvArtistName = (AppCompatTextView) itemView.findViewById(R.id.tv_artist_name);
            mTvTrackName = (AppCompatTextView) itemView.findViewById(R.id.tv_track_name);
            itemView.setOnClickListener(view -> mListener.onFavoriteTrackClick(mData.get(getAdapterPosition())));
        }

        public void bindArtistName(String artist) {
            mTvArtistName.setText(artist);
        }

        public void bindTrackName(String track) {
            mTvTrackName.setText(track);
        }
    }
}
