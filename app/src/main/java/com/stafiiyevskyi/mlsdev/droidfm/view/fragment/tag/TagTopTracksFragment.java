package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.tag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TagTopTracksPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.TagTopTracksPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopTracksScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.TopTracksAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopTracksFragment extends BaseFragment implements TagTopTracksScreenView, TopTracksAdapter.OnTopTrackClickListener {

    private static final String TAG_BUNDLE_KEY = "tag_bundle_key_tag_top_tracks_fragment";

    @Bind(R.id.rv_toptracks)
    RecyclerView mRvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;

    private RecyclerView.LayoutManager mLayoutManager;
    private TopTracksAdapter mAdapter;
    private TagTopTracksPresenter mPresenter;


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
            mLastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();

            if (!mIsLoading) {
                if ((mVisibleItemCount + mLastVisibleItemPosition) >= mTotalItemCount
                        && mLastVisibleItemPosition >= 0) {
                    mIsLoading = true;
                    mCurrentPageNumber = ++mCurrentPageNumber;
                    mPbProgress.setVisibility(View.VISIBLE);
                    mPresenter.getTopTracks(mTag, mCurrentPageNumber);
                }
            }
        }
    };

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
        mTag = getArguments().getString(TAG_BUNDLE_KEY);
        mPresenter = new TagTopTracksPresenterImpl(this);
        mPresenter.getTopTracks(mTag, mCurrentPageNumber);
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
        Snackbar.make(mRvTracks, errorMessage, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void showTopArtists(List<TopTrackEntity> topTrackEntities) {
        mPbProgress.setVisibility(View.GONE);
        mAdapter.addData(topTrackEntities);
        mIsLoading = false;
    }

    @Override
    public void onTopTrackClick(TopTrackEntity topTrack) {

    }
}
