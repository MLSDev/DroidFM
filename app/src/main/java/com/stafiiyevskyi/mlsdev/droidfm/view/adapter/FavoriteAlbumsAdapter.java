package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteAlbumEntity;

import java.util.List;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteAlbumsAdapter extends RecyclerView.Adapter<FavoriteAlbumsAdapter.AlbumVH> {
    private OnAlbumClickListener mListener;
    private List<FavoriteAlbumEntity> mData;

    public FavoriteAlbumsAdapter(OnAlbumClickListener mListener) {
        this.mListener = mListener;
    }

    public void setData(List<FavoriteAlbumEntity> albums) {
        this.mData = albums;
        notifyDataSetChanged();
    }

    public void addData(List<FavoriteAlbumEntity> albums) {
        this.mData.addAll(albums);
        notifyDataSetChanged();
    }

    @Override
    public AlbumVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_album, parent, false);
        return new AlbumVH(view);
    }

    @Override
    public void onBindViewHolder(AlbumVH holder, int position) {
        FavoriteAlbumEntity album = mData.get(position);
        holder.bindArtistName(album.getArtistName());
        holder.bindAlbumName(album.getAlbumName());
    }

    @Override
    public int getItemCount() {
        if (mData != null) return mData.size();
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
            itemView.setOnClickListener(view -> mListener.onAlbumClick(mData.get(getAdapterPosition())));
        }

        public void bindAlbumName(String album) {
            mTvAlbumName.setText(album);
        }

        public void bindArtistName(String artist) {
            mTvArtistName.setText(artist);
        }
    }
}
