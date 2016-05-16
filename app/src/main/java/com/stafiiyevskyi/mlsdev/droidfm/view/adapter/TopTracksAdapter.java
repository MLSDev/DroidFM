package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.bumptech.glide.Glide;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksAdapter.TopTrackVH> {
    private static final int ANIMATED_ITEMS_COUNT = 10;
    private int lastAnimatedPosition = -1;

    private List<TopTrackEntity> mData = new ArrayList<>();
    private OnTopTrackClickListener mListener;
    private Context mContext;

    public TopTracksAdapter(OnTopTrackClickListener mListener) {
        this.mListener = mListener;
    }

    public void setData(List<TopTrackEntity> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<TopTrackEntity> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public TopTrackVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_track, parent, false);
        return new TopTrackVH(view);
    }

    @Override
    public void onBindViewHolder(TopTrackVH holder, int position) {
        runEnterAnimation(holder.itemView, position);
        TopTrackEntity entity = mData.get(position);
        String imageUrl = "";
        for (ImageEntity imageEntity : entity.getTracksImages()) {
            if (imageEntity.getSize().equalsIgnoreCase("medium")) {
                imageUrl = imageEntity.getText();
                break;
            }
        }
        holder.bindTrackName(entity.getName());
        holder.bindArtistName(entity.getArtistName());
        holder.bindTrackIcon(imageUrl);

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
    public int getItemCount() {
        return mData.size();
    }

    public interface OnTopTrackClickListener {
        void onTopTrackClick(TopTrackEntity topTrack);
    }

    public class TopTrackVH extends RecyclerView.ViewHolder {

        private AppCompatImageView mIvTopTrackIcon;
        private AppCompatTextView mTvTopTrackName;
        private AppCompatTextView mTvArtistName;

        public TopTrackVH(View itemView) {
            super(itemView);
            mIvTopTrackIcon = (AppCompatImageView) itemView.findViewById(R.id.iv_top_track);
            mTvTopTrackName = (AppCompatTextView) itemView.findViewById(R.id.tv_track_name);
            mTvArtistName = (AppCompatTextView) itemView.findViewById(R.id.tv_artist_name);
            itemView.setOnClickListener(view -> mListener.onTopTrackClick(mData.get(getAdapterPosition())));
        }

        public void bindTrackName(String trackName) {
            mTvTopTrackName.setText(trackName);
        }

        public void bindArtistName(String artistName) {
            mTvArtistName.setText(artistName);
        }

        public void bindTrackIcon(String url) {
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.ic_music_circle_grey600_48dp)
                    .centerCrop()
                    .into(mIvTopTrackIcon);
        }
    }
}
