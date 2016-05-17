package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistTopTracksScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.ArtistTopTracksScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistTopTracksScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.ArtistTopTracksAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ArtistTopTracksFragment extends BaseFragment implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener, ArtistTopTracksAdapter.OnTopTrackClickListener, ArtistTopTracksScreenView, SwipeRefreshLayout.OnRefreshListener {

    public static final String ARTIST_MBID_BUNDLE_KEY = "artist_top_tracks_fragment_mbid";
    public static final String ARTIST_NAME_BUNDLE_KEY = "artist_top_tracks_fragment_name";

    @Bind(R.id.rv_toptracks)
    RecyclerView rvToptracks;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private SearchView searchView;

    private String mbid;
    private String artistName;

    private RecyclerView.LayoutManager layoutManager;
    private ArtistTopTracksAdapter adapter;
    private ArtistTopTracksScreenPresenter presenter;


    private boolean isLoading = true;
    private boolean isSearchFirstCall = false;
    private boolean isSearchActivate = false;
    private int currentPageNumber = 1;
    private int visibleItemCount, totalItemCount;
    private int lastVisibleItemPosition;
    private String searchQuery = "";

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = layoutManager.getChildCount();
            totalItemCount = adapter.getItemCount();
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

            if (!isLoading) {
                if ((visibleItemCount + lastVisibleItemPosition) >= totalItemCount
                        && lastVisibleItemPosition >= 0) {
                    isLoading = true;
                    currentPageNumber = ++currentPageNumber;
                    pbProgress.setVisibility(View.VISIBLE);
                    if (isSearchActivate) {
                        presenter.searchArtistTracks(artistName, searchQuery, currentPageNumber);
                    } else {
                        presenter.getArtistTopTracks(artistName, mbid, currentPageNumber);
                    }

                }
            }
        }
    };


    public static BaseFragment newInstance(String mbid, String artistName) {

        Bundle args = new Bundle();
        args.putString(ARTIST_MBID_BUNDLE_KEY, mbid);
        args.putString(ARTIST_NAME_BUNDLE_KEY, artistName);
        BaseFragment fragment = new ArtistTopTracksFragment();
        fragment.setArguments(args);
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
        Bundle args = getArguments();
        srlRefresh.setOnRefreshListener(this);
        mbid = args.getString(ARTIST_MBID_BUNDLE_KEY);
        artistName = args.getString(ARTIST_NAME_BUNDLE_KEY);
        presenter = new ArtistTopTracksScreenPresenterImpl(this);
        presenter.getArtistTopTracks(artistName, mbid, currentPageNumber);
        setupRvTracks();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) {
            menu.clear();
            inflater.inflate(R.menu.menu_artist_top_tracks_screen, menu);
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setOnQueryTextListener(this);
            searchView.setOnCloseListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.stop();
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_top_tracks;
    }

    @Override
    public void updateToolbar() {
        if (isVisible())
            getActivity().supportInvalidateOptionsMenu();
    }

    private void setupRvTracks() {
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ArtistTopTracksAdapter(this);
        rvToptracks.setLayoutManager(layoutManager);
        rvToptracks.setAdapter(adapter);
        rvToptracks.addOnScrollListener(recyclerViewOnScrollListener);
    }

    @Override
    public void onTopTrackClick(TopTrackEntity topTrack) {
        ((Navigator) getActivity()).navigateToTrackDetails(topTrack.getArtistName(), topTrack.getName(), topTrack.getTrackMbid());
    }

    @Override
    public void showTracks(List<TopTrackEntity> tracks) {
        srlRefresh.setRefreshing(false);
        pbProgress.setVisibility(View.GONE);
        if (isSearchFirstCall) {
            adapter.setData(tracks);
            isSearchFirstCall = false;
        } else {
            adapter.addData(tracks);
            isLoading = false;
        }
    }

    @Override
    public void showError(String errorMessage) {
        srlRefresh.setRefreshing(false);
        Snackbar.make(rvToptracks, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onClose() {
        isSearchActivate = false;
        isSearchFirstCall = true;
        currentPageNumber = 1;
        pbProgress.setVisibility(View.VISIBLE);
        presenter.getArtistTopTracks(artistName, mbid, currentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        pbProgress.setVisibility(View.VISIBLE);
        isSearchFirstCall = true;
        isSearchActivate = true;
        currentPageNumber = 1;
        searchQuery = query;
        presenter.searchArtistTracks(artistName, searchQuery, currentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onRefresh() {
        isSearchActivate = false;
        isSearchFirstCall = true;
        currentPageNumber = 1;
        presenter.getArtistTopTracks(artistName, mbid, currentPageNumber);
    }
}
