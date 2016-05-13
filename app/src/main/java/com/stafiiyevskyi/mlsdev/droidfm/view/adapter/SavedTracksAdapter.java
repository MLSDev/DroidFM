package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.SavedTrackEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 13.05.16.
 */
public class SavedTracksAdapter extends RecyclerView.Adapter<SavedTracksAdapter.SavedTrackVH> {
    private OnSavedTrackClickListener mListener;
    private List<SavedTrackEntity> mData;

    public SavedTracksAdapter(OnSavedTrackClickListener mListener) {
        this.mListener = mListener;
    }

    public void setData(List<SavedTrackEntity> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public SavedTrackVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_track_artist, parent, false);
        return new SavedTrackVH(view);
    }

    @Override
    public void onBindViewHolder(SavedTrackVH holder, int position) {
        holder.mTvTrackName.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mData != null) return mData.size();
        return 0;
    }

    public interface OnSavedTrackClickListener {
        void onTrackClick(SavedTrackEntity track);
    }

    class SavedTrackVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_track_name)
        AppCompatTextView mTvTrackName;

        public SavedTrackVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                mListener.onTrackClick(mData.get(getAdapterPosition()));
            });
        }
    }
}
