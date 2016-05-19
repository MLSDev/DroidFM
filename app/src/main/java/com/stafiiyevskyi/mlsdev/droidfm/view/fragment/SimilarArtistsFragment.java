package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.SimilarArtistPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.SimilarArtistsPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistsScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.ArtistsAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by oleksandr on 18.05.16.
 */
public class SimilarArtistsFragment extends BaseFragment implements ArtistsScreenView, ArtistsAdapter.OnArtistClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String ARTIST_BUNDLE_KEY = "artist_bundle_key_similar_artist_fragment";

    @Bind(R.id.rv_artists)
    RecyclerView rvArtists;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private SimilarArtistPresenter presenter;
    private RecyclerView.LayoutManager layoutManager;
    private ArtistsAdapter adapter;
    private String artistName;

    public static BaseFragment newInstance(@NonNull String artistName) {
        Bundle args = new Bundle();
        args.putString(ARTIST_BUNDLE_KEY, artistName);
        SimilarArtistsFragment fragment = new SimilarArtistsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        artistName = getArguments().getString(ARTIST_BUNDLE_KEY);
        presenter = new SimilarArtistsPresenterImpl(this);
        srlRefresh.setOnRefreshListener(this);
        setupRvArtists();
        presenter.getSimilarArtists(artistName);
//        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(artistName + " similar");
    }

    private void setupRvArtists() {
        layoutManager = new GridLayoutManager(getActivity(), 2);
        adapter = new ArtistsAdapter(this);
        rvArtists.setLayoutManager(layoutManager);
        rvArtists.setAdapter(adapter);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_artists_search_list;
    }

    @Override
    public void updateToolbar() {
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(artistName + " similar");
    }

    @Override
    public void showArtists(List<ArtistEntity> artistEntities) {
        pbProgress.setVisibility(View.GONE);
        srlRefresh.setRefreshing(false);
        adapter.setData(artistEntities);
    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void onArtistClick(ArtistEntity artist, AppCompatImageView imageView) {
        ((Navigator) getActivity()).navigateToArtistContentDetailsScreen(artist.getArtisMbid()
                , artist.getArtistName(), artist.getArtistImages().get(3).getText(), imageView);
    }

    @Override
    public void onRefresh() {
        srlRefresh.setRefreshing(true);
        presenter.getSimilarArtists(artistName);
    }
}
