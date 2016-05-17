package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.tag;

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
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TagTopAlbumsPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.TagTopAlbumsPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TagTopAlbumsScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.TopAlbumsAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopAlbumsFragment extends BaseFragment implements TopAlbumsAdapter.OnAlbumClickListener, TagTopAlbumsScreenView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG_BUNDLE_KEY = "tag_bundle_key_tag_top_albums_fragment";

    @Bind(R.id.rv_topalbums)
    RecyclerView rvAlbums;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private RecyclerView.LayoutManager layoutManager;
    private TopAlbumsAdapter adapter;
    private TagTopAlbumsPresenter presenter;


    private boolean isLoading = true;
    private int currentPageNumber = 1;
    private int visibleItemCount, totalItemCount;
    private int lastVisibleItemPosition;


    private String tag;

    private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = layoutManager.getChildCount();
            totalItemCount = adapter.getItemCount();
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();

            if (!isLoading) {
                if ((visibleItemCount + lastVisibleItemPosition) >= totalItemCount
                        && lastVisibleItemPosition >= 0) {
                    isLoading = true;
                    currentPageNumber = ++currentPageNumber;
                    pbProgress.setVisibility(View.VISIBLE);
                    presenter.getTopAlbums(tag, currentPageNumber);
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
        srlRefresh.setOnRefreshListener(this);
        tag = getArguments().getString(TAG_BUNDLE_KEY);
        presenter = new TagTopAlbumsPresenterImpl(this);
        presenter.getTopAlbums(tag, currentPageNumber);
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
        presenter.stop();
    }

    private void setupRvTracks() {
        layoutManager = new GridLayoutManager(getActivity(), 2);
        adapter = new TopAlbumsAdapter(this);
        rvAlbums.setLayoutManager(layoutManager);
        rvAlbums.setAdapter(adapter);
        rvAlbums.addOnScrollListener(mRecyclerViewOnScrollListener);
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
    public void showError(String errorMessage) {
        srlRefresh.setRefreshing(false);
        Snackbar.make(rvAlbums, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTopAlbums(List<AlbumEntity> albumEntities) {
        pbProgress.setVisibility(View.GONE);
        srlRefresh.setRefreshing(false);
        adapter.addData(albumEntities);
        isLoading = false;
    }

    @Override
    public void onRefresh() {
        presenter.getTopAlbums(tag, currentPageNumber);
    }
}
