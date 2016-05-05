package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.TopTracksAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ChartTopTracksFragment extends BaseFragment implements TopTracksAdapter.OnTopTrackClickListener, ChartTopTracksScreenView, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    @Bind(R.id.rv_toptracks)
    RecyclerView mRvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;
    private SearchView mSearchView;

    private ChartTopTracksScreenPresenter mPresenter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TopTracksAdapter mAdapter;

    private boolean mIsLoading = true;
    private boolean mIsSearchFirstCall = false;
    private boolean mIsSearchActivate = false;
    private int mCurrentPageNumber = 1;
    private int mVisibleItemCount, mTotalItemCount;
    private int mLastVisibleItemPosition;
    private String mSearchQuery = "";

    private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mVisibleItemCount = mLayoutManager.getChildCount();
            mTotalItemCount = mAdapter.getItemCount();
            mLastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();

            if (!mIsLoading) {
                if ((mVisibleItemCount + mLastVisibleItemPosition) >= mTotalItemCount
                        && mLastVisibleItemPosition >= 0) {
                    mIsLoading = true;
                    mCurrentPageNumber = ++mCurrentPageNumber;
                    mPbProgress.setVisibility(View.VISIBLE);
                    if (mIsSearchActivate) {
                        mPresenter.searchTracks(mSearchQuery, mCurrentPageNumber);
                    } else {
                        mPresenter.getChartTopTracks(mCurrentPageNumber);
                    }

                }
            }
        }
    };
    ;

    public static BaseFragment newInstance() {
        return new ChartTopTracksFragment();
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
        mPresenter = new ChartTopTrackPresenterImpl(this);
        mPresenter.getChartTopTracks(mCurrentPageNumber);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) {
            menu.clear();
            inflater.inflate(R.menu.menu_artist_top_tracks_screen, menu);
            mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            mSearchView.setOnQueryTextListener(this);
            mSearchView.setOnCloseListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mPresenter.stop();
    }

    private void setupRvTracks() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new TopTracksAdapter(this);
        mRvTracks.setLayoutManager(mLayoutManager);
        mRvTracks.setAdapter(mAdapter);
        mRvTracks.addOnScrollListener(mRecyclerViewOnScrollListener);
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
    }

    @Override
    public void onTopTrackClick(TopTrackEntity topTrack) {
        ((Navigator) getActivity()).navigateToTrackDetails(topTrack.getArtistName(), topTrack.getName(), topTrack.getTrackMbid());
    }

    @Override
    public void showChartTopTracks(List<TopTrackEntity> trackEntities) {
        mPbProgress.setVisibility(View.GONE);
        if (mIsSearchFirstCall) {
            mAdapter.setData(trackEntities);
            mIsSearchFirstCall = false;
        } else {
            mAdapter.addData(trackEntities);
            mIsLoading = false;
        }
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(mRvTracks, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onClose() {
        mIsSearchFirstCall = true;
        mIsSearchActivate = false;
        mCurrentPageNumber = 1;
        mPbProgress.setVisibility(View.VISIBLE);
        mPresenter.getChartTopTracks(mCurrentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPbProgress.setVisibility(View.VISIBLE);
        mIsSearchFirstCall = true;
        mIsSearchActivate = true;
        mCurrentPageNumber = 1;
        mSearchQuery = query;
        mPresenter.searchTracks(mSearchQuery, mCurrentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
