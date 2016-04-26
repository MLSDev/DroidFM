package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsTracksAdapter extends RecyclerView.Adapter<AlbumsTracksAdapter.TopTrackVH> {


    private List<TrackEntity> mData = new ArrayList<>();
    private OnTopTrackClickListener mListener;
    private Context mContext;

    public AlbumsTracksAdapter(OnTopTrackClickListener mListener) {
        this.mListener = mListener;
    }

    public void setData(List<TrackEntity> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<TrackEntity> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public TopTrackVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_top_track_artist, parent, false);
        TopTrackVH topTrackVH = new TopTrackVH(view);
        return topTrackVH;
    }

    @Override
    public void onBindViewHolder(TopTrackVH holder, int position) {
        TrackEntity entity = mData.get(position);
        holder.bindTrackName(entity.getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnTopTrackClickListener {
        void onTrackClick(TrackEntity topTrack);
    }

    public class TopTrackVH extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvTopTrackName;

        public TopTrackVH(View itemView) {
            super(itemView);;
            mTvTopTrackName = (AppCompatTextView) itemView.findViewById(R.id.tv_track_name);
            itemView.setOnClickListener(view -> mListener.onTrackClick(mData.get(getAdapterPosition())));
        }

        public void bindTrackName(String trackName) {
            mTvTopTrackName.setText(trackName);
        }

    }
}