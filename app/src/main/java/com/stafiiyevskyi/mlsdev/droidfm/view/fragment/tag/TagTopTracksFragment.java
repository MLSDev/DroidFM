package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.tag;

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
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TagTopTracksPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.TagTopTracksPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopTracksScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.TopTracksAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopTracksFragment extends BaseFragment implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener, TagTopTracksScreenView, TopTracksAdapter.OnTopTrackClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG_BUNDLE_KEY = "tag_bundle_key_tag_top_tracks_fragment";

    @Bind(R.id.rv_toptracks)
    RecyclerView rvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private RecyclerView.LayoutManager layoutManager;
    private TopTracksAdapter adapter;
    private TagTopTracksPresenter presenter;


    private boolean isLoading = true;
    private boolean isFirstCall = false;
    private boolean isSearchActivate = false;
    private int currentPageNumber = 1;
    private int visibleItemCount, totalItemCount;
    private int lastVisibleItemPosition;
    private String searchQuery = "";

    private String tag;

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
                        presenter.getTopTracks(tag, currentPageNumber);
                    }
                }
            }
        }
    };
    private SearchView mSearchView;

    public static BaseFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString(TAG_BUNDLE_KEY, tag);
        BaseFragment fragment = new TagTopTracksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        srlRefresh.setOnRefreshListener(this);
        tag = getArguments().getString(TAG_BUNDLE_KEY);
        presenter = new TagTopTracksPresenterImpl(this);
        presenter.getTopTracks(tag, currentPageNumber);
        setupRvTracks();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) {
            menu.clear();
            inflater.inflate(R.menu.menu_artists_search_screen, menu);
            mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            mSearchView.setOnQueryTextListener(this);
            mSearchView.setOnCloseListener(this);
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
        getActivity().supportInvalidateOptionsMenu();
    }


    @Override
    public void showError(String errorMessage) {
        srlRefresh.setRefreshing(false);
        Snackbar.make(rvTracks, errorMessage, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void showTopArtists(List<TopTrackEntity> topTrackEntities) {
        pbProgress.setVisibility(View.GONE);
        srlRefresh.setRefreshing(false);
        if (isFirstCall) {
            adapter.setData(topTrackEntities);
            isFirstCall = false;
        } else {
            adapter.addData(topTrackEntities);
            isLoading = false;
        }
    }

    @Override
    public void onTopTrackClick(TopTrackEntity topTrack) {
        ((Navigator) getActivity()).navigateToTrackDetails(topTrack.getArtistName(), topTrack.getName(), topTrack.getTrackMbid());
    }

    @Override
    public boolean onClose() {
        isFirstCall = true;
        isSearchActivate = false;
        currentPageNumber = 1;
        pbProgress.setVisibility(View.VISIBLE);
        presenter.getTopTracks(tag, currentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        pbProgress.setVisibility(View.VISIBLE);
        isFirstCall = true;
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
        isFirstCall = true;
        isSearchActivate = false;
        currentPageNumber = 1;
        presenter.getTopTracks(tag, currentPageNumber);
    }
}
