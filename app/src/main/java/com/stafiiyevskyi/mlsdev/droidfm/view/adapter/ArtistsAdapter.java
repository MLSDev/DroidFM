package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistVH> {
    private static final int ANIMATED_ITEMS_COUNT = 10;
    private int lastAnimatedPosition = -1;
    private List<ArtistEntity> mData = new ArrayList<>();
    private OnArtistClickListener mListener;
    private Context mContext;

    public ArtistsAdapter(OnArtistClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public ArtistVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_artist, parent, false);
        return new ArtistVH(view);
    }

    @Override
    public void onBindViewHolder(ArtistVH holder, int position) {
        runEnterAnimation(holder.itemView, position);
        ArtistEntity entity = mData.get(position);
        String imageUrl = "";
        for (ImageEntity imageEntity : entity.getArtistImages()) {
            if (imageEntity.getSize().equalsIgnoreCase("large")) {
                imageUrl = imageEntity.getText();
                break;
            }
        }
        holder.bindArtistName(entity.getArtistName());
        holder.bindArtistPhoto(imageUrl);
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

    public void addData(List<ArtistEntity> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(List<ArtistEntity> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnArtistClickListener {
        void onArtistClick(ArtistEntity artist, AppCompatImageView imageView);
    }

    public class ArtistVH extends RecyclerView.ViewHolder {

        @Bind(R.id.fl_progress)
        FrameLayout mFlProgress;

        @Bind(R.id.iv_artist)
        AppCompatImageView mIvArtistPhoto;

        @Bind(R.id.tv_artist_name)
        AppCompatTextView mTvArtistName;

        public ArtistVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> mListener.onArtistClick(mData.get(getAdapterPosition()), mIvArtistPhoto));
        }

        public void bindArtistName(String artistName) {
            mTvArtistName.setText(artistName);
        }

        public void bindArtistPhoto(String url) {
            Glide.with(mContext)
                    .load(url)
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            mFlProgress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(mIvArtistPhoto);
        }
    }
}
