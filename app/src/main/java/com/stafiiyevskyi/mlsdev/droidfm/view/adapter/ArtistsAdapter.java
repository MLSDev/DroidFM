package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistVH> {

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
        ArtistVH artistVH = new ArtistVH(view);
        return artistVH;
    }

    @Override
    public void onBindViewHolder(ArtistVH holder, int position) {
        ArtistEntity entity = mData.get(position);
        holder.bindArtistName(entity.getArtistName());
        holder.bindArtistPhoto(entity.getArtistImages().get(2).getText());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<ArtistEntity> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnArtistClickListener {
        void onArtistClick(ArtistEntity artist);
    }

    public class ArtistVH extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.pb_progress)
        FrameLayout mFlProgress;
        @Nullable
        @Bind(R.id.iv_artist)
        AppCompatImageView mIvArtistPhoto;
        @Nullable
        @Bind(R.id.tv_artist_name)
        AppCompatTextView mTvArtistName;

        public ArtistVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> mListener.onArtistClick(mData.get(getAdapterPosition())));
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
                            if (target.getRequest().isFailed()) {
                                target.getRequest().begin();
                            }
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
