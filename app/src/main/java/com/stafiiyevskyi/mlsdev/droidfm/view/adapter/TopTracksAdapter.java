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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksAdapter.TopTrackVH> {


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
        TopTrackVH topTrackVH = new TopTrackVH(view);
        return topTrackVH;
    }

    @Override
    public void onBindViewHolder(TopTrackVH holder, int position) {
        TopTrackEntity entity = mData.get(position);
        String imageUrl = "";
        for (ImageEntity imageEntity : entity.getTracksImages()) {
            if (imageEntity.getSize().equalsIgnoreCase("medium")) {
                imageUrl = imageEntity.getText();
                break;
            }
        }
        holder.bindTrackName(entity.getName());
        holder.bindTrackIcon(imageUrl);

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

        public TopTrackVH(View itemView) {
            super(itemView);
            mIvTopTrackIcon = (AppCompatImageView) itemView.findViewById(R.id.iv_top_track);
            mTvTopTrackName = (AppCompatTextView) itemView.findViewById(R.id.tv_track_name);
            itemView.setOnClickListener(view -> mListener.onTopTrackClick(mData.get(getAdapterPosition())));
        }

        public void bindTrackName(String trackName) {
            mTvTopTrackName.setText(trackName);
        }

        public void bindTrackIcon(String url) {
            Glide.with(mContext)
                    .load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(mIvTopTrackIcon);
        }
    }
}
