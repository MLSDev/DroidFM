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
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ArtistTopTracksFragment extends BaseFragment implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener, ArtistTopTracksAdapter.OnTopTrackClickListener, ArtistTopTracksScreenView, SwipeRefreshLayout.OnRefreshListener {

    public static final String ARTIST_MBID_BUNDLE_KEY = "artist_top_tracks_fragment_mbid";
    public static final String ARTIST_NAME_BUNDLE_KEY = "artist_top_tracks_fragment_name";

    @Bind(R.id.rv_toptracks)
    RecyclerView mRvToptracks;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private SearchView mSearchView;

    private String mMbid;
    private String mArtistName;

    private RecyclerView.LayoutManager mLayoutManager;
    private ArtistTopTracksAdapter mAdapter;
    private ArtistTopTracksScreenPresenter mPresenter;


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
                        mPresenter.searchArtistTracks(mArtistName, mSearchQuery, mCurrentPageNumber);
                    } else {
                        mPresenter.getArtistTopTracks(mArtistName, mMbid, mCurrentPageNumber);
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
        mSrlRefresh.setOnRefreshListener(this);
        mMbid = args.getString(ARTIST_MBID_BUNDLE_KEY);
        mArtistName = args.getString(ARTIST_NAME_BUNDLE_KEY);
        mPresenter = new ArtistTopTracksScreenPresenterImpl(this);
        mPresenter.getArtistTopTracks(mArtistName, mMbid, mCurrentPageNumber);
        setupRvTracks();
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

    @Override
    protected int getResourceId() {
        return R.layout.fragment_top_tracks;
    }

    @Override
    public void updateToolbar() {
        getActivity().supportInvalidateOptionsMenu();
    }

    private void setupRvTracks() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ArtistTopTracksAdapter(this);
        mRvToptracks.setLayoutManager(mLayoutManager);
        mRvToptracks.setAdapter(mAdapter);
        mRvToptracks.addOnScrollListener(mRecyclerViewOnScrollListener);
    }

    @Override
    public void onTopTrackClick(TopTrackEntity topTrack) {
        ((Navigator) getActivity()).navigateToTrackDetails(topTrack.getArtistName(), topTrack.getName(), topTrack.getTrackMbid());
    }

    @Override
    public void showTracks(List<TopTrackEntity> tracks) {
        mSrlRefresh.setRefreshing(false);
        mPbProgress.setVisibility(View.GONE);
        if (mIsSearchFirstCall) {
            mAdapter.setData(tracks);
            mIsSearchFirstCall = false;
        } else {
            mAdapter.addData(tracks);
            mIsLoading = false;
        }
    }

    @Override
    public void showError(String errorMessage) {
        mSrlRefresh.setRefreshing(false);
        Snackbar.make(mRvToptracks, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onClose() {
        mIsSearchActivate = false;
        mIsSearchFirstCall = true;
        mCurrentPageNumber = 1;
        mPbProgress.setVisibility(View.VISIBLE);
        mPresenter.getArtistTopTracks(mArtistName, mMbid, mCurrentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPbProgress.setVisibility(View.VISIBLE);
        mIsSearchFirstCall = true;
        mIsSearchActivate = true;
        mCurrentPageNumber = 1;
        mSearchQuery = query;
        mPresenter.searchArtistTracks(mArtistName, mSearchQuery, mCurrentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onRefresh() {
        mIsSearchActivate = false;
        mIsSearchFirstCall = true;
        mCurrentPageNumber = 1;
        mPresenter.getArtistTopTracks(mArtistName, mMbid, mCurrentPageNumber);
    }
}
