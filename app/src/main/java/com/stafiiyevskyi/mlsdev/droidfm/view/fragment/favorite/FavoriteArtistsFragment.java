package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.FavoriteArtistScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.FavoriteArtistScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.FavoriteArtistScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.FavoriteArtistsAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Created by oleksandr on 11.05.16.
 */
public class FavoriteArtistsFragment extends BaseFragment implements FavoriteArtistScreenView, FavoriteArtistsAdapter.OnArtistClickListener {
    @BindView(R.id.rv_artists)
    RecyclerView mRvArtists;
    @BindView(R.id.pb_progress)
    ProgressBar mPbProgress;

    private FavoriteArtistsAdapter mAdapter;
    private FavoriteArtistScreenPresenter mPresenter;
    private RecyclerView.LayoutManager mLayoutManager;


    public static BaseFragment newInstance() {
        BaseFragment fragment = new FavoriteArtistsFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRvArtists();
        mPresenter = new FavoriteArtistScreenPresenterImpl(this);
        mPresenter.getFavoriteArtists();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) menu.clear();
    }

    private void setupRvArtists() {
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mAdapter = new FavoriteArtistsAdapter(this);
        mRvArtists.setLayoutManager(mLayoutManager);
        mRvArtists.setAdapter(mAdapter);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_favorite_artists;
    }

    @Override
    public void updateToolbar() {
        getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public void showFavoriteArtists(List<FavoriteArtistEntity> artists) {
        mPbProgress.setVisibility(View.GONE);
        mAdapter.setData(artists);
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void onArtistClick(FavoriteArtistEntity artist, AppCompatImageView imageView) {
        ((Navigator) getActivity()).navigateToArtistContentDetailsScreen(artist.getMbid(), artist.getName(), artist.getImage(), imageView);
    }
}
