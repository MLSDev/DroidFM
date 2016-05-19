package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.SimilarTracksPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.SimilarTracksPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopTracksScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.TopTracksAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by oleksandr on 19.05.16.
 */
public class SimilarTracksFragment extends BaseFragment implements TagTopTracksScreenView, TopTracksAdapter.OnTopTrackClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TRACK_BUNDLE_KEY = "track_bundle_key_similar_tracks_fragment";
    private static final String ARTIST_BUNDLE_KEY = "artist_bundle_key_similar_tracks_fragment";

    @Bind(R.id.rv_toptracks)
    RecyclerView rvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private RecyclerView.LayoutManager layoutManager;
    private TopTracksAdapter adapter;
    private SimilarTracksPresenter presenter;


    private boolean isLoading = true;
    private boolean isFirstCall = false;
    private int currentPageNumber = 1;
    private int visibleItemCount, totalItemCount;
    private int lastVisibleItemPosition;

    private String track;
    private String artistName;

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
                    presenter.getSimilarTracks(artistName, track, currentPageNumber);

                }
            }
        }
    };

    public static BaseFragment newInstance(String artistName, String track) {
        Bundle args = new Bundle();
        args.putString(TRACK_BUNDLE_KEY, track);
        args.putString(ARTIST_BUNDLE_KEY, artistName);
        BaseFragment fragment = new SimilarTracksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        srlRefresh.setOnRefreshListener(this);
        track = getArguments().getString(TRACK_BUNDLE_KEY);
        artistName = getArguments().getString(ARTIST_BUNDLE_KEY);
        presenter = new SimilarTracksPresenterImpl(this);
        presenter.getSimilarTracks(artistName, track, currentPageNumber);
        setupRvTracks();
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(track + " similar");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible())
            menu.clear();
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
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(track + " similar");
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
    public void onRefresh() {
        isFirstCall = true;
        currentPageNumber = 1;
        presenter.getSimilarTracks(artistName, track, currentPageNumber);
    }
}
