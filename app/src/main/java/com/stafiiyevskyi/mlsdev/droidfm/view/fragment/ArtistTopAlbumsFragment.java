package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistTopAlbumsPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.ArtistTopAlbumsScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistTopAlbumsScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.TopAlbumsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 22.04.16.
 */
public class ArtistTopAlbumsFragment extends BaseFragment implements TopAlbumsAdapter.OnAlbumClickListener, ArtistTopAlbumsScreenView, SwipeRefreshLayout.OnRefreshListener {

    public static final String ARTIST_MBID_BUNDLE_KEY = "artist_top_albums_fragment_mbid";
    public static final String ARTIST_NAME_BUNDLE_KEY = "artist_top_albums_fragment_name";

    @BindView(R.id.rv_topalbums)
    RecyclerView mRvAlbums;
    @BindView(R.id.pb_progress)
    ProgressBar mPbProgress;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private String mMbid;
    private String mArtistName;

    private RecyclerView.LayoutManager mLayoutManager;
    private TopAlbumsAdapter mAdapter;
    private ArtistTopAlbumsPresenter mPresenter;


    private boolean mIsLoading = true;
    private int mCurrentPageNumber = 1;
    private int mVisibleItemCount, mTotalItemCount;
    private int mLastVisibleItemPosition;


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
                    mPresenter.getArtistTopAlbums(mArtistName, mMbid, mCurrentPageNumber);
                }
            }
        }
    };

    public static BaseFragment newInstance(String artistMbid, String artistName) {
        Bundle args = new Bundle();
        args.putString(ARTIST_NAME_BUNDLE_KEY, artistName);
        args.putString(ARTIST_MBID_BUNDLE_KEY, artistMbid);
        BaseFragment fragment = new ArtistTopAlbumsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mSrlRefresh.setOnRefreshListener(this);
        Bundle args = getArguments();
        mMbid = args.getString(ARTIST_MBID_BUNDLE_KEY);
        mArtistName = args.getString(ARTIST_NAME_BUNDLE_KEY);
        mPresenter = new ArtistTopAlbumsScreenPresenterImpl(this);
        mPresenter.getArtistTopAlbums(mArtistName, mMbid, mCurrentPageNumber);
        setupRvTracks();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) menu.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.stop();
    }

    private void setupRvTracks() {
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mAdapter = new TopAlbumsAdapter(this);
        mRvAlbums.setLayoutManager(mLayoutManager);
        mRvAlbums.setAdapter(mAdapter);
        mRvAlbums.addOnScrollListener(mRecyclerViewOnScrollListener);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_top_albums;
    }

    @Override
    public void updateToolbar() {
        getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public void onAlbumClick(AlbumEntity album) {
        ((Navigator) getActivity()).navigateToAlbumDetails(album.getArtistName(), album.getName(), album.getMbid(), album.getImage().get(3).getText());
    }

    @Override
    public void showArtistTopAlbums(List<AlbumEntity> albumEntities) {
        mSrlRefresh.setRefreshing(false);
        mPbProgress.setVisibility(View.GONE);
        mAdapter.addData(albumEntities);
        mIsLoading = false;
    }

    @Override
    public void showError(String errorMessage) {
        mSrlRefresh.setRefreshing(false);
        Snackbar.make(mRvAlbums, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        mCurrentPageNumber = 1;
        mPresenter.getArtistTopAlbums(mArtistName, mMbid, mCurrentPageNumber);
    }
}
