package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
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

/**
 * Created by oleksandr on 25.04.16.
 */
public class ChartTopTagsFragment extends BaseFragment implements TopTagsAdapter.OnTagClickListener, ChartTopTagScreenView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv_toptags)
    RecyclerView rvTags;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;


    private RecyclerView.LayoutManager layoutManager;
    private TopTagsAdapter adapter;
    private ChartTopTagScreenPresenter presenter;

    private int currentPageNumber = 1;


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
        presenter = new ChartTopTagScreenPresenterImpl(this);
        setupRvTags();
        srlRefresh.setOnRefreshListener(this);
        presenter.getTopTags(currentPageNumber);
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
        presenter.stop();
    }

    private void setupRvTags() {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new TopTagsAdapter(this);
        rvTags.setAdapter(adapter);
        rvTags.setLayoutManager(layoutManager);
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
        srlRefresh.setRefreshing(false);
        pbProgress.setVisibility(View.GONE);
        adapter.addData(tags);
    }

    @Override
    public void showError(String errorMessage) {
        srlRefresh.setRefreshing(false);
        Snackbar.make(rvTags, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        currentPageNumber = 1;
        presenter.getTopTags(currentPageNumber);
    }
}
