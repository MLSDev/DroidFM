package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ChartTopTagScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TopTagEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.ChartTopTagScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ChartTopTagScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.TopTagsAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 25.04.16.
 */
public class ChartTopTagsFragment extends BaseFragment implements TopTagsAdapter.OnTagClickListener, ChartTopTagScreenView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv_toptags)
    RecyclerView mRvTags;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;


    private RecyclerView.LayoutManager mLayoutManager;
    private TopTagsAdapter mAdapter;
    private ChartTopTagScreenPresenter mPresenter;

    private int mCurrentPageNumber = 1;


    public static BaseFragment newInstance() {
        return new ChartTopTagsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ChartTopTagScreenPresenterImpl(this);
        setupRvTags();
        mSrlRefresh.setOnRefreshListener(this);
        mPresenter.getTopTags(mCurrentPageNumber);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) {
            menu.clear();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.stop();
    }

    private void setupRvTags() {
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new TopTagsAdapter(this);
        mRvTags.setAdapter(mAdapter);
        mRvTags.setLayoutManager(mLayoutManager);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_top_tags;
    }

    @Override
    public void updateToolbar() {
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onTagClick(TopTagEntity tag) {
        ((Navigator) getActivity()).navigateToTagTopContent(tag.getName());
    }

    @Override
    public void showTopTags(List<TopTagEntity> tags) {
        mSrlRefresh.setRefreshing(false);
        mPbProgress.setVisibility(View.GONE);
        mAdapter.addData(tags);
    }

    @Override
    public void showError(String errorMessage) {
        mSrlRefresh.setRefreshing(false);
        Snackbar.make(mRvTags, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        mCurrentPageNumber = 1;
        mPresenter.getTopTags(mCurrentPageNumber);
    }
}
