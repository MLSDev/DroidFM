package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.tag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TagTopAlbumsPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.TagTopAlbumsPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopAlbumsScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.TopAlbumsAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopAlbumsFragment extends BaseFragment implements TopAlbumsAdapter.OnAlbumClickListener, TagTopAlbumsScreenView {

    private static final String TAG_BUNDLE_KEY = "tag_bundle_key_tag_top_albums_fragment";

    @Bind(R.id.rv_topalbums)
    RecyclerView mRvAlbums;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;

    private RecyclerView.LayoutManager mLayoutManager;
    private TopAlbumsAdapter mAdapter;
    private TagTopAlbumsPresenter mPresenter;


    private boolean mIsLoading = true;
    private int mCurrentPageNumber = 1;
    private int mVisibleItemCount, mTotalItemCount;
    private int mLastVisibleItemPosition;


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
                    mPresenter.getTopAlbums(mTag, mCurrentPageNumber);
                }
            }
        }
    };

    public static BaseFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString(TAG_BUNDLE_KEY, tag);
        BaseFragment fragment = new TagTopAlbumsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mTag = getArguments().getString(TAG_BUNDLE_KEY);
        mPresenter = new TagTopAlbumsPresenterImpl(this);
        mPresenter.getTopAlbums(mTag, mCurrentPageNumber);
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
        ButterKnife.unbind(this);
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
        ((Navigator) getActivity()).navigateToAlbumDetails(album.getMbid(), album.getImage().get(3).getText());
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(mRvAlbums, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTopAlbums(List<AlbumEntity> albumEntities) {
        mPbProgress.setVisibility(View.GONE);
        mAdapter.addData(albumEntities);
        mIsLoading = false;
    }
}
