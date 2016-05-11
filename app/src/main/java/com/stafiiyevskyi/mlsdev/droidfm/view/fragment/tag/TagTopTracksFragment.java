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
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopTracksFragment extends BaseFragment implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener, TagTopTracksScreenView, TopTracksAdapter.OnTopTrackClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG_BUNDLE_KEY = "tag_bundle_key_tag_top_tracks_fragment";

    @Bind(R.id.rv_toptracks)
    RecyclerView mRvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private RecyclerView.LayoutManager mLayoutManager;
    private TopTracksAdapter mAdapter;
    private TagTopTracksPresenter mPresenter;


    private boolean mIsLoading = true;
    private boolean mIsFirstCall = false;
    private boolean mIsSearchActivate = false;
    private int mCurrentPageNumber = 1;
    private int mVisibleItemCount, mTotalItemCount;
    private int mLastVisibleItemPosition;
    private String mSearchQuery = "";

    private String mTag;

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
                        mPresenter.getTopTracks(mTag, mCurrentPageNumber);
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
        mSrlRefresh.setOnRefreshListener(this);
        mTag = getArguments().getString(TAG_BUNDLE_KEY);
        mPresenter = new TagTopTracksPresenterImpl(this);
        mPresenter.getTopTracks(mTag, mCurrentPageNumber);
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
        getActivity().supportInvalidateOptionsMenu();
    }


    @Override
    public void showError(String errorMessage) {
        mSrlRefresh.setRefreshing(false);
        Snackbar.make(mRvTracks, errorMessage, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void showTopArtists(List<TopTrackEntity> topTrackEntities) {
        mPbProgress.setVisibility(View.GONE);
        mSrlRefresh.setRefreshing(false);
        if (mIsFirstCall) {
            mAdapter.setData(topTrackEntities);
            mIsFirstCall = false;
        } else {
            mAdapter.addData(topTrackEntities);
            mIsLoading = false;
        }
    }

    @Override
    public void onTopTrackClick(TopTrackEntity topTrack) {
        ((Navigator) getActivity()).navigateToTrackDetails(topTrack.getArtistName(), topTrack.getName(), topTrack.getTrackMbid());
    }

    @Override
    public boolean onClose() {
        mIsFirstCall = true;
        mIsSearchActivate = false;
        mCurrentPageNumber = 1;
        mPbProgress.setVisibility(View.VISIBLE);
        mPresenter.getTopTracks(mTag, mCurrentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPbProgress.setVisibility(View.VISIBLE);
        mIsFirstCall = true;
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

    @Override
    public void onRefresh() {
        mIsFirstCall = true;
        mIsSearchActivate = false;
        mCurrentPageNumber = 1;
        mPresenter.getTopTracks(mTag, mCurrentPageNumber);
    }
}
