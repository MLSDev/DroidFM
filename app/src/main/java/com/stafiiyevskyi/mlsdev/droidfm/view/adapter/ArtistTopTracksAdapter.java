package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 25.04.16.
 */
public class ArtistTopTracksAdapter extends RecyclerView.Adapter<ArtistTopTracksAdapter.TopTrackVH> {


    private List<TopTrackEntity> mData = new ArrayList<>();
    private OnTopTrackClickListener mListener;
    private Context mContext;

    public ArtistTopTracksAdapter(OnTopTrackClickListener mListener) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_top_track_artist, parent, false);
        return new TopTrackVH(view);
    }

    @Override
    public void onBindViewHolder(TopTrackVH holder, int position) {
        TopTrackEntity entity = mData.get(position);
        holder.bindTrackName(entity.getName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnTopTrackClickListener {
        void onTopTrackClick(TopTrackEntity topTrack);
    }

    public class TopTrackVH extends RecyclerView.ViewHolder {

        private AppCompatTextView mTvTopTrackName;

        public TopTrackVH(View itemView) {
            super(itemView);
            mTvTopTrackName = (AppCompatTextView) itemView.findViewById(R.id.tv_track_name);
            itemView.setOnClickListener(view -> mListener.onTopTrackClick(mData.get(getAdapterPosition())));
        }

        public void bindTrackName(String trackName) {
            mTvTopTrackName.setText(trackName);
        }
    }
}