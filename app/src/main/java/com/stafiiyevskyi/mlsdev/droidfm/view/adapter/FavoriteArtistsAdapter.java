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
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 11.05.16.
 */
public class FavoriteArtistsAdapter extends RecyclerView.Adapter<FavoriteArtistsAdapter.ArtistVH> {
    private static final int ANIMATED_ITEMS_COUNT = 10;
    private int lastAnimatedPosition = -1;

    private List<FavoriteArtistEntity> data = new ArrayList<>();
    private OnArtistClickListener listener;
    private Context context;

    public FavoriteArtistsAdapter(OnArtistClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ArtistVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false);
        return new ArtistVH(view);
    }

    @Override
    public void onBindViewHolder(ArtistVH holder, int position) {
        runEnterAnimation(holder.itemView, position);
        FavoriteArtistEntity entity = data.get(position);
        holder.bindArtistName(entity.getName());
        holder.bindArtistPhoto(entity.getImage());
    }



    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(AnimationUtil.getScreenHeight(context));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<FavoriteArtistEntity> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(List<FavoriteArtistEntity> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnArtistClickListener {
        void onArtistClick(FavoriteArtistEntity artist, AppCompatImageView imageView);
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
            itemView.setOnClickListener(view -> listener.onArtistClick(data.get(getAdapterPosition()), mIvArtistPhoto));
        }

        public void bindArtistName(String artistName) {
            mTvArtistName.setText(artistName);
        }

        public void bindArtistPhoto(String url) {
            Glide.with(context)
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

