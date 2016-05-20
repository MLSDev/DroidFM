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

import butterknife.Bind;

/**
 * Created by oleksandr on 11.05.16.
 */
public class FavoriteArtistsFragment extends BaseFragment implements FavoriteArtistScreenView, FavoriteArtistsAdapter.OnArtistClickListener {
    @Bind(R.id.rv_artists)
    RecyclerView rvArtists;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;

    private FavoriteArtistsAdapter adapter;
    private FavoriteArtistScreenPresenter presenter;
    private RecyclerView.LayoutManager layoutManager;


    public static BaseFragment newInstance() {
        BaseFragment fragment = new FavoriteArtistsFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRvArtists();
        presenter = new FavoriteArtistScreenPresenterImpl(this);
        presenter.getFavoriteArtists();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) menu.clear();
    }

    private void setupRvArtists() {
        layoutManager = new GridLayoutManager(getActivity(), 2);
        adapter = new FavoriteArtistsAdapter(this);
        rvArtists.setLayoutManager(layoutManager);
        rvArtists.setAdapter(adapter);
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
        pbProgress.setVisibility(View.GONE);
        adapter.setData(artists);
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void onArtistClick(FavoriteArtistEntity artist, AppCompatImageView imageView) {
        ((Navigator) getActivity()).navigateToArtistContentDetailsScreen(artist.getMbid(), artist.getName(), artist.getImage(),artist.getImage(), imageView);
    }
}
