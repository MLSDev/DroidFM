package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.TopTagsAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 25.04.16.
 */
public class ChartTopTagsFragment extends BaseFragment implements TopTagsAdapter.OnTagClickListener, ChartTopTagScreenView {

    @Bind(R.id.rv_toptags)
    RecyclerView mRvTags;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;


    private RecyclerView.LayoutManager mLayoutManager;
    private TopTagsAdapter mAdapter;
    private ChartTopTagScreenPresenter mPresenter;

    private int mCurrentPageNumber = 1;


    public static BaseFragment newInstance() {
        BaseFragment fragment = new ChartTopTagsFragment();
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
        mPresenter = new ChartTopTagScreenPresenterImpl(this);
        setupRvTags();
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
        ButterKnife.unbind(this);
        mPresenter.stop();
    }

    private void setupRvTags() {
        mLayoutManager = new GridLayoutManager(getContext(), 2);
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

    }

    @Override
    public void showTopTags(List<TopTagEntity> tags) {
        mPbProgress.setVisibility(View.GONE);
        mAdapter.addData(tags);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(mRvTags, errorMessage, Snackbar.LENGTH_LONG).show();
    }
}
