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
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ImageEntity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

import io.realm.processor.Utils;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TopAlbumsAdapter extends RecyclerView.Adapter<TopAlbumsAdapter.TopAlbumsVH> {

    private static final int ANIMATED_ITEMS_COUNT = 10;
    private int lastAnimatedPosition = -1;

    private List<AlbumEntity> mData = new ArrayList<>();
    private OnAlbumClickListener mListener;
    private Context mContext;

    public TopAlbumsAdapter(OnAlbumClickListener mListener) {
        this.mListener = mListener;
    }

    public void addData(List<AlbumEntity> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public TopAlbumsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_album, parent, false);
        return new TopAlbumsVH(view);
    }

    @Override
    public void onBindViewHolder(TopAlbumsVH holder, int position) {
        runEnterAnimation(holder.itemView, position);
        AlbumEntity albumEntity = mData.get(position);
        String imageUrl = "";
        for (ImageEntity imageEntity : albumEntity.getImage()) {
            if (imageEntity.getSize().equalsIgnoreCase("large")) {
                imageUrl = imageEntity.getText();
                break;
            }
        }
        holder.bindAlbumIcon(imageUrl);
        holder.bindAlbumName(albumEntity.getName());
        holder.bindArtistName(albumEntity.getArtistName());
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

    public interface OnAlbumClickListener {
        void onAlbumClick(AlbumEntity album);
    }

    public class TopAlbumsVH extends RecyclerView.ViewHolder {

        private AppCompatTextView mTvAlbumName;
        private AppCompatTextView mTvArtistName;
        private AppCompatImageView mIvAlbumIcon;
        private FrameLayout mFlProgress;

        public TopAlbumsVH(View itemView) {
            super(itemView);
            mIvAlbumIcon = (AppCompatImageView) itemView.findViewById(R.id.iv_album);
            mTvAlbumName = (AppCompatTextView) itemView.findViewById(R.id.tv_album_name);
            mTvArtistName = (AppCompatTextView) itemView.findViewById(R.id.tv_artist_name);
            mFlProgress = (FrameLayout) itemView.findViewById(R.id.fl_progress);
            itemView.setOnClickListener(view -> mListener.onAlbumClick(mData.get(getAdapterPosition())));
        }

        public void bindAlbumName(String albumName) {
            mTvAlbumName.setText(albumName);
        }

        public void bindArtistName(String artistName) {
            mTvArtistName.setText(artistName);
        }

        public void bindAlbumIcon(String url) {
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
                    .into(mIvAlbumIcon);
        }
    }
}
