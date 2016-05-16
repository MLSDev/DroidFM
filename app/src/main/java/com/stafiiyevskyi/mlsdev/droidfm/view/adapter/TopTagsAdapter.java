package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTagEntity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksandr on 25.04.16.
 */
public class TopTagsAdapter extends RecyclerView.Adapter<TopTagsAdapter.TopTagVH> {
    private static final int ANIMATED_ITEMS_COUNT = 15;
    private int lastAnimatedPosition = -1;

    private List<TopTagEntity> mData = new ArrayList<>();
    private OnTagClickListener mListener;
    private Context mContext;

    public TopTagsAdapter(OnTagClickListener mListener) {
        this.mListener = mListener;
    }

    public void setData(List<TopTagEntity> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void addData(List<TopTagEntity> entities) {
        this.mData.addAll(entities);
        notifyDataSetChanged();
    }

    @Override
    public TopTagVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag, parent, false);
        return new TopTagVH(view);
    }

    @Override
    public void onBindViewHolder(TopTagVH holder, int position) {
        runEnterAnimation(holder.itemView,position);
        TopTagEntity entity = mData.get(position);
        holder.bindTag(entity.getName());
    }

    private void runEnterAnimation(View view, int position) {
        if ( position >= ANIMATED_ITEMS_COUNT - 1) {
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

    public interface OnTagClickListener {
        void onTagClick(TopTagEntity tag);
    }

    public class TopTagVH extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvTag;

        public TopTagVH(View itemView) {
            super(itemView);
            mTvTag = (AppCompatTextView) itemView.findViewById(R.id.tv_tag);
            itemView.setOnClickListener(view -> {
                mListener.onTagClick(mData.get(getAdapterPosition()));
            });
        }

        public void bindTag(String tag) {
            mTvTag.setText(tag);
        }
    }
}
