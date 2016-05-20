package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.VkPopularTrackEntity;

import java.util.List;

/**
 * Created by oleksandr on 19.05.16.
 */
public class VkPopularTracksAdapter extends RecyclerView.Adapter<VkPopularTracksAdapter.VkPopularTrackVH> {

    private List<VkPopularTrackEntity> data;
    private OnPopularTrackClickListener listener;

    public VkPopularTracksAdapter(OnPopularTrackClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<VkPopularTrackEntity> tracks) {
        data = tracks;
        notifyDataSetChanged();
    }

    public void addData(List<VkPopularTrackEntity> tracks) {
        data.addAll(tracks);
        notifyDataSetChanged();
    }


    @Override
    public VkPopularTrackVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_track_artist, parent, false);
        return new VkPopularTrackVH(view);
    }

    @Override
    public void onBindViewHolder(VkPopularTrackVH holder, int position) {
        VkPopularTrackEntity entity = data.get(position);
        holder.bindTrackName(entity.getArtist() + " - " + entity.getTitle());
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }


    public interface OnPopularTrackClickListener {
        void onTopTrackClick(VkPopularTrackEntity topTrack);
    }

    public class VkPopularTrackVH extends RecyclerView.ViewHolder {

        private AppCompatTextView mTvTopTrackName;

        public VkPopularTrackVH(View itemView) {
            super(itemView);
            mTvTopTrackName = (AppCompatTextView) itemView.findViewById(R.id.tv_track_name);
            itemView.setOnClickListener(view -> listener.onTopTrackClick(data.get(getAdapterPosition())));
        }

        public void bindTrackName(String trackName) {
            mTvTopTrackName.setText(trackName);
        }
    }
}
