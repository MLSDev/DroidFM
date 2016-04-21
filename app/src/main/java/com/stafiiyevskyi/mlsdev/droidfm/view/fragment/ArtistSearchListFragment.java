package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistsScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.ArtistsScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistsScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.ArtistsAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by oleksandr on 20.04.16.
 */
public class ArtistSearchListFragment extends BaseFragment implements ArtistsAdapter.OnArtistClickListener, ArtistsScreenView {

    @Bind(R.id.rv_artists)
    RecyclerView mRvArtists;

    private RecyclerView.LayoutManager mLayoutManager;
    private ArtistsAdapter mAdapter;
    private ArtistsScreenPresenter mPresenter;

    public static BaseFragment newInstance() {
        BaseFragment fragment = new ArtistSearchListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecycler();
        mPresenter = new ArtistsScreenPresenterImpl(this);
        mPresenter.getTopArtists(1);

    }

    private void setupRecycler() {
        mAdapter = new ArtistsAdapter(this);
        mLayoutManager = new GridLayoutManager(getActivity(),2);
        mRvArtists.setLayoutManager(mLayoutManager);
        mRvArtists.setAdapter(mAdapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_artists_search_screen, menu);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_artists_search_list;
    }

    @Override
    public void onArtistClick(ArtistEntity artist) {

    }

    @Override
    public void showArtists(List<ArtistEntity> artistEntities) {
        mAdapter.setData(artistEntities);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(mRvArtists, errorMessage, Snackbar.LENGTH_LONG).show();
    }
}
