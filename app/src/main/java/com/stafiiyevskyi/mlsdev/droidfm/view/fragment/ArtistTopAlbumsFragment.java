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

import butterknife.Bind;


/**
 * Created by oleksandr on 22.04.16.
 */
public class ArtistTopAlbumsFragment extends BaseFragment implements TopAlbumsAdapter.OnAlbumClickListener, ArtistTopAlbumsScreenView, SwipeRefreshLayout.OnRefreshListener {

    public static final String ARTIST_MBID_BUNDLE_KEY = "artist_top_albums_fragment_mbid";
    public static final String ARTIST_NAME_BUNDLE_KEY = "artist_top_albums_fragment_name";

    @Bind(R.id.rv_topalbums)
    RecyclerView rvAlbums;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private String mbid;
    private String artistName;

    private RecyclerView.LayoutManager layoutManager;
    private TopAlbumsAdapter adapter;
    private ArtistTopAlbumsPresenter presenter;


    private boolean isLoading = true;
    private int currentPageNumber = 1;
    private int visibleItemCount, totalItemCount;
    private int lastVisibleItemPosition;


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
                    presenter.getArtistTopAlbums(artistName, mbid, currentPageNumber);
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
        srlRefresh.setOnRefreshListener(this);
        Bundle args = getArguments();
        mbid = args.getString(ARTIST_MBID_BUNDLE_KEY);
        artistName = args.getString(ARTIST_NAME_BUNDLE_KEY);
        presenter = new ArtistTopAlbumsScreenPresenterImpl(this);
        presenter.getArtistTopAlbums(artistName, mbid, currentPageNumber);
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
        rvAlbums.addOnScrollListener(recyclerViewOnScrollListener);
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
        srlRefresh.setRefreshing(false);
        pbProgress.setVisibility(View.GONE);
        adapter.addData(albumEntities);
        isLoading = false;
    }

    @Override
    public void showError(String errorMessage) {
        srlRefresh.setRefreshing(false);
        Snackbar.make(rvAlbums, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        currentPageNumber = 1;
        presenter.getArtistTopAlbums(artistName, mbid, currentPageNumber);
    }
}
