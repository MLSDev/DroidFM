package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.tag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TagTopArtistsPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.TagTopArtistsPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopArtistScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.ArtistsAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopArtistsFragment extends BaseFragment implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener, ArtistsAdapter.OnArtistClickListener, TagTopArtistScreenView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG_BUNDLE_KEY = "tag_bundle_key_tag_top_artists_fragment";

    @BindView(R.id.rv_artists)
    RecyclerView mRvArtists;
    @BindView(R.id.pb_progress)
    ProgressBar mPbProgress;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private SearchView mSearchView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArtistsAdapter mAdapter;
    private TagTopArtistsPresenter mPresenter;

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
                        mPresenter.getTopArtists(mTag, mCurrentPageNumber);
                    }

                }
            }
        }
    };


    public static BaseFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString(TAG_BUNDLE_KEY, tag);
        BaseFragment fragment = new TagTopArtistsFragment();
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
        setupRecycler();
        mSrlRefresh.setOnRefreshListener(this);
        mTag = getArguments().getString(TAG_BUNDLE_KEY);
        mPresenter = new TagTopArtistsPresenterImpl(this);
        mPresenter.getTopArtists(mTag, mCurrentPageNumber);
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
        mPresenter.stop();
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_artists_search_list;
    }

    @Override
    public void updateToolbar() {
        if (isVisible())
            getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public void onArtistClick(ArtistEntity artist, AppCompatImageView imageView) {
        ((Navigator) getActivity())
                .navigateToArtistContentDetailsScreen(artist.getArtisMbid()
                        , artist.getArtistName(), artist.getArtistImages().get(3).getText(), imageView);
    }

    @Override
    public void showError(String errorMessage) {
        mSrlRefresh.setRefreshing(false);
        Snackbar.make(mRvArtists, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onClose() {
        mIsFirstCall = true;
        mIsSearchActivate = false;
        mCurrentPageNumber = 1;
        mPbProgress.setVisibility(View.VISIBLE);
        mPresenter.getTopArtists(mTag, mCurrentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPbProgress.setVisibility(View.VISIBLE);
        mIsFirstCall = true;
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

    @Override
    public void showTopArtist(List<ArtistEntity> artistEntities) {
        mSrlRefresh.setRefreshing(false);
        mPbProgress.setVisibility(View.GONE);
        if (mIsFirstCall) {
            mAdapter.setData(artistEntities);
            mIsFirstCall = false;
        } else {
            mAdapter.addData(artistEntities);
            mIsLoading = false;
        }
    }

    @Override
    public void onRefresh() {
        mIsFirstCall = true;
        mIsSearchActivate = false;
        mCurrentPageNumber = 1;
        mPresenter.getTopArtists(mTag, mCurrentPageNumber);
    }
}
