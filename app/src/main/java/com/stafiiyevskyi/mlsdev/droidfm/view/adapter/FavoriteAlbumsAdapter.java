package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteAlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;

import java.util.List;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteAlbumsAdapter extends RecyclerView.Adapter<FavoriteAlbumsAdapter.AlbumVH> {
    private static final int ANIMATED_ITEMS_COUNT = 10;
    private int lastAnimatedPosition = -1;

    private OnAlbumClickListener listener;
    private List<FavoriteAlbumEntity> data;
    private Context context;

    public FavoriteAlbumsAdapter(OnAlbumClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<FavoriteAlbumEntity> albums) {
        this.data = albums;
        notifyDataSetChanged();
    }

    public void addData(List<FavoriteAlbumEntity> albums) {
        this.data.addAll(albums);
        notifyDataSetChanged();
    }

    @Override
    public AlbumVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_album, parent, false);
        return new AlbumVH(view);
    }

    @Override
    public void onBindViewHolder(AlbumVH holder, int position) {
        runEnterAnimation(holder.itemView,position);
        FavoriteAlbumEntity album = data.get(position);
        holder.bindArtistName(album.getArtistName());
        holder.bindAlbumName(album.getAlbumName());
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
        if (data != null) return data.size();
        return 0;
    }

    public interface OnAlbumClickListener {
        void onAlbumClick(FavoriteAlbumEntity album);
    }

    class AlbumVH extends RecyclerView.ViewHolder {
        private AppCompatTextView mTvAlbumName;
        private AppCompatTextView mTvArtistName;

        public AlbumVH(View itemView) {
            super(itemView);
            mTvAlbumName = (AppCompatTextView) itemView.findViewById(R.id.tv_album_name);
            mTvArtistName = (AppCompatTextView) itemView.findViewById(R.id.tv_artist_name);
            itemView.setOnClickListener(view -> listener.onAlbumClick(data.get(getAdapterPosition())));
        }

        public void bindAlbumName(String album) {
            mTvAlbumName.setText(album);
        }

        public void bindArtistName(String artist) {
            mTvArtistName.setText(artist);
        }
    }
}
