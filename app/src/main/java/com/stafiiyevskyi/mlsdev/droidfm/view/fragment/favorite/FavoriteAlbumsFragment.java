package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.FavoriteAlbumsScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteAlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.FavoriteAlbumsScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.FavoriteAlbumScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.FavoriteAlbumsAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteAlbumsFragment extends BaseFragment implements FavoriteAlbumScreenView, FavoriteAlbumsAdapter.OnAlbumClickListener {

    @Bind(R.id.rv_albums)
    RecyclerView mRvAlbums;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;


    private FavoriteAlbumsAdapter mAdapter;
    private FavoriteAlbumsScreenPresenter mPresenter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static BaseFragment newInstance() {
        BaseFragment fragment = new FavoriteAlbumsFragment();
        return fragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRvAlbums();
        mPresenter = new FavoriteAlbumsScreenPresenterImpl(this);
        mPresenter.getFavoritesAlbums();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.stop();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) menu.clear();
    }

    private void setupRvAlbums() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new FavoriteAlbumsAdapter(this);
        mRvAlbums.setLayoutManager(mLayoutManager);
        mRvAlbums.setAdapter(mAdapter);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_favorite_albums;
    }

    @Override
    public void updateToolbar() {
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void showFavoriteAlbums(List<FavoriteAlbumEntity> albums) {
        mPbProgress.setVisibility(View.GONE);
        mAdapter.setData(albums);
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void onAlbumClick(FavoriteAlbumEntity album) {
        ((Navigator) getActivity()).navigateToAlbumDetails(album.getArtistName(), album.getAlbumName(), album.getMbid(), "");
    }
}
