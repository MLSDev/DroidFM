package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistsScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.ArtistsScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistsScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.ArtistsAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 20.04.16.
 */
public class ArtistSearchListFragment extends BaseFragment implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener, ArtistsAdapter.OnArtistClickListener, ArtistsScreenView {

    @Bind(R.id.rv_artists)
    RecyclerView mRvArtists;
    @Bind(R.id.fl_progress)
    ProgressBar mPbProgress;

    private SearchView mSearchView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArtistsAdapter mAdapter;
    private ArtistsScreenPresenter mPresenter;

    private boolean mIsLoading = true;
    private boolean mIsSearchFirstCall = false;
    private boolean mIsSearchActivate = false;
    private int mCurrentPageNumber = 2;
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
            mLastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();

            if (!mIsLoading) {
                if ((mVisibleItemCount + mLastVisibleItemPosition) >= mTotalItemCount
                        && mLastVisibleItemPosition >= 0) {
                    mIsLoading = true;
                    mCurrentPageNumber = ++mCurrentPageNumber;
                    mPbProgress.setVisibility(View.VISIBLE);
                    if (mIsSearchActivate) {
                        mPresenter.searchArtist(mSearchQuery, mCurrentPageNumber);
                    } else {
                        mPresenter.getTopArtists(mCurrentPageNumber);
                    }

                }
            }
        }
    };


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
        mPresenter.getTopArtists(mCurrentPageNumber);

    }

    private void setupRecycler() {
        mAdapter = new ArtistsAdapter(this);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRvArtists.setLayoutManager(mLayoutManager);
        mRvArtists.setAdapter(mAdapter);
        mRvArtists.addOnScrollListener(mRecyclerViewOnScrollListener);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_artists_search_screen, menu);
        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mPresenter.stop();
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_artists_search_list;
    }

    @Override
    public void updateToolbar() {
        ((Navigator) getActivity()).setDrawerToggleEnabled();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onArtistClick(ArtistEntity artist) {
        ((Navigator) getActivity()).navigateToArtistContentDetailsScreen(artist.getArtisMbid(), artist.getArtistName());
    }

    @Override
    public void showArtists(List<ArtistEntity> artistEntities) {
        mPbProgress.setVisibility(View.GONE);
        if (mIsSearchFirstCall) {
            mAdapter.setData(artistEntities);
            mIsSearchFirstCall = false;
        } else {
            mAdapter.addData(artistEntities);
            mIsLoading = false;
        }

    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(mRvArtists, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPbProgress.setVisibility(View.VISIBLE);
        mIsSearchFirstCall = true;
        mIsSearchActivate = true;
        mCurrentPageNumber = 1;
        mSearchQuery = query;
        mPresenter.searchArtist(mSearchQuery, mCurrentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
