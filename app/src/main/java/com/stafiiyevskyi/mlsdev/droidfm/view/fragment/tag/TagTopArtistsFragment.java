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

import butterknife.Bind;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopArtistsFragment extends BaseFragment implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener, ArtistsAdapter.OnArtistClickListener, TagTopArtistScreenView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG_BUNDLE_KEY = "tag_bundle_key_tag_top_artists_fragment";

    @Bind(R.id.rv_artists)
    RecyclerView rvArtists;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private SearchView searchView;
    private RecyclerView.LayoutManager layoutManager;
    private ArtistsAdapter adapter;
    private TagTopArtistsPresenter presenter;

    private boolean isLoading = true;
    private boolean isFirstCall = false;
    private boolean isSearchActivate = false;
    private int currentPageNumber = 1;
    private int visibleItemCount, totalItemCount;
    private int lastVisibleItemPosition;
    private String searchQuery = "";

    private String tag;
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
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
                    if (isSearchActivate) {
                        presenter.searchArtist(searchQuery, currentPageNumber);
                    } else {
                        presenter.getTopArtists(tag, currentPageNumber);
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
        srlRefresh.setOnRefreshListener(this);
        tag = getArguments().getString(TAG_BUNDLE_KEY);
        presenter = new TagTopArtistsPresenterImpl(this);
        presenter.getTopArtists(tag, currentPageNumber);
    }

    private void setupRecycler() {
        adapter = new ArtistsAdapter(this);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        rvArtists.setLayoutManager(layoutManager);
        rvArtists.setAdapter(adapter);
        rvArtists.addOnScrollListener(recyclerViewOnScrollListener);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) {
            menu.clear();
            inflater.inflate(R.menu.menu_artists_search_screen, menu);
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setOnQueryTextListener(this);
            searchView.setOnCloseListener(this);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.stop();
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
        srlRefresh.setRefreshing(false);
        Snackbar.make(rvArtists, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onClose() {
        isFirstCall = true;
        isSearchActivate = false;
        currentPageNumber = 1;
        pbProgress.setVisibility(View.VISIBLE);
        presenter.getTopArtists(tag, currentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        pbProgress.setVisibility(View.VISIBLE);
        isFirstCall = true;
        isSearchActivate = true;
        currentPageNumber = 1;
        searchQuery = query;
        presenter.searchArtist(searchQuery, currentPageNumber);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void showTopArtist(List<ArtistEntity> artistEntities) {
        srlRefresh.setRefreshing(false);
        pbProgress.setVisibility(View.GONE);
        if (isFirstCall) {
            adapter.setData(artistEntities);
            isFirstCall = false;
        } else {
            adapter.addData(artistEntities);
            isLoading = false;
        }
    }

    @Override
    public void onRefresh() {
        isFirstCall = true;
        isSearchActivate = false;
        currentPageNumber = 1;
        presenter.getTopArtists(tag, currentPageNumber);
    }
}
