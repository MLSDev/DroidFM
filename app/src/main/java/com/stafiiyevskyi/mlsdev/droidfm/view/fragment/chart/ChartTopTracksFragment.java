package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ChartTopTracksScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.ChartTopTrackPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ChartTopTracksScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.TopTracksAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ChartTopTracksFragment extends BaseFragment implements TopTracksAdapter.OnTopTrackClickListener, ChartTopTracksScreenView, SearchView.OnQueryTextListener, SearchView.OnCloseListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String IS_FROM_CHART_BUNDLE_KEY = "is_from_bundle_key_chart_top_tracks_fragment";

    @Bind(R.id.rv_toptracks)
    RecyclerView rvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    private SearchView searchView;

    private ChartTopTracksScreenPresenter presenter;
    private RecyclerView.LayoutManager layoutManager;
    private TopTracksAdapter adapter;

    private boolean isLoading = true;
    private boolean isSearchFirstCall = false;
    private boolean isSearchActivate = false;
    private int currentPageNumber = 1;
    private int visibleItemCount, totalItemCount;
    private int lastVisibleItemPosition;
    private String searchQuery = "";

    private boolean isFromChart;
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
                        presenter.searchTracks(searchQuery, currentPageNumber);
                    } else {
                        presenter.getChartTopTracks(currentPageNumber);
                    }

                }
            }
        }
    };
    ;

    public static BaseFragment newInstance(boolean isFromChart) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_FROM_CHART_BUNDLE_KEY, isFromChart);
        BaseFragment fragment = new ChartTopTracksFragment();
        fragment.setArguments(bundle);
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
        setupRvTracks();
        srlRefresh.setOnRefreshListener(this);
        presenter = new ChartTopTrackPresenterImpl(this);
        presenter.getChartTopTracks(currentPageNumber);
        if (!getArguments().getBoolean(IS_FROM_CHART_BUNDLE_KEY))
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.tab_title_top_tracks));
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

    private void setupRvTracks() {
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new TopTracksAdapter(this);
        rvTracks.setLayoutManager(layoutManager);
        rvTracks.setAdapter(adapter);
        rvTracks.addOnScrollListener(recyclerViewOnScrollListener);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_top_tracks;
    }

    @Override
    public void updateToolbar() {
        if (isVisible())
            ((Navigator) getActivity()).setDrawerToggleEnabled();
        getActivity().supportInvalidateOptionsMenu();
        if (!getArguments().getBoolean(IS_FROM_CHART_BUNDLE_KEY))
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.tab_title_top_tracks));
    }

    @Override
    public void onTopTrackClick(TopTrackEntity topTrack) {
        ((Navigator) getActivity()).navigateToTrackDetails(topTrack.getArtistName(), topTrack.getName(), topTrack.getTrackMbid());
    }


    @Override
    public void showChartTopTracks(List<TopTrackEntity> trackEntities) {
        pbProgress.setVisibility(View.GONE);
        srlRefresh.setRefreshing(false);
        if (isSearchFirstCall) {
            adapter.setData(trackEntities);
            isSearchFirstCall = false;
        } else {
            adapter.addData(trackEntities);
            isLoading = false;
        }
    }

    @Override
    public void showError(String errorMessage) {
        pbProgress.setVisibility(View.GONE);
        srlRefresh.setRefreshing(false);
        Snackbar.make(rvTracks, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onClose() {
        isSearchFirstCall = true;
        isSearchActivate = false;
        currentPageNumber = 1;
        pbProgress.setVisibility(View.VISIBLE);
        presenter.getChartTopTracks(currentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        pbProgress.setVisibility(View.VISIBLE);
        isSearchFirstCall = true;
        isSearchActivate = true;
        currentPageNumber = 1;
        searchQuery = query;
        presenter.searchTracks(searchQuery, currentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onRefresh() {
        isSearchFirstCall = true;
        isSearchActivate = false;
        currentPageNumber = 1;
        presenter.getChartTopTracks(currentPageNumber);
    }
}
