package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.util.Genre;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.VkPopularTracksPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.VkPopularTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.VkPopularTracksPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.VkPopularTracksScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.VkPopularTracksAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by oleksandr on 19.05.16.
 */
public class VkPopularTracksFragment extends BaseFragment implements VkPopularTracksAdapter.OnPopularTrackClickListener, VkPopularTracksScreenView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv_tracks)
    RecyclerView rvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.sp_genres)
    AppCompatSpinner spGenres;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private final int count = 30;
    private int offset = 0;
    private Genre genre;

    private int totalItemCount;
    private int lastVisibleItemPosition;
    private boolean isLoading;
    private boolean isFirstCall = true;
    private boolean isCanLoadMore = true;

    private VkPopularTracksPresenter presenter;
    private VkPopularTracksAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    public static BaseFragment newInstance() {
        VkPopularTracksFragment fragment = new VkPopularTracksFragment();
        return fragment;
    }


    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            totalItemCount = adapter.getItemCount();
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

            if (!isLoading) {
                if (((lastVisibleItemPosition + 3) >= totalItemCount) && lastVisibleItemPosition >= 0 && isCanLoadMore) {
                    isLoading = true;
                    offset += count;
                    pbProgress.setVisibility(View.VISIBLE);
                    presenter.getVkPopularTrack(genre, offset, count);

                }
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Navigator) getActivity()).setDrawerToggleEnabled();
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.popular_vk_tracks_section_title));
        presenter = new VkPopularTracksPresenterImpl(this);
        setupGenresSpinner();
        setupRvTracks();
        setupRefresh();
    }

    private void setupGenresSpinner() {
        spGenres.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Genre.values()));
        spGenres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genre = (Genre) adapterView.getAdapter().getItem(i);
                offset = 0;
                isFirstCall = true;
                isCanLoadMore = true;
                pbProgress.setVisibility(View.VISIBLE);
                presenter.getVkPopularTrack(genre, offset, count);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupRvTracks() {
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new VkPopularTracksAdapter(this);
        rvTracks.setLayoutManager(layoutManager);
        rvTracks.setAdapter(adapter);
        rvTracks.addOnScrollListener(recyclerViewOnScrollListener);
    }

    private void setupRefresh() {
        srlRefresh.setOnRefreshListener(this);
    }


    @Override
    protected int getResourceId() {
        return R.layout.fragment_popular_vk_track;
    }

    @Override
    public void updateToolbar() {
        getActivity().supportInvalidateOptionsMenu();
        ((Navigator) getActivity()).setDrawerToggleEnabled();
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.popular_vk_tracks_section_title));
    }

    @Override
    public void onTopTrackClick(VkPopularTrackEntity topTrack) {
        ((Navigator) getActivity()).navigateToTrackDetails(topTrack.getArtist(), topTrack.getTitle(), topTrack.getUrl(), topTrack.getDuration());
    }

    @Override
    public void showVkPopularTracks(List<VkPopularTrackEntity> tracks) {
        if (tracks.size() < count) isCanLoadMore = false;
        pbProgress.setVisibility(View.GONE);
        srlRefresh.setRefreshing(false);
        isLoading = false;
        if (isFirstCall) {
            adapter.setData(tracks);
            isFirstCall = false;
        } else {
            adapter.addData(tracks);
        }
    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void onRefresh() {
        isFirstCall = true;
        isLoading = true;
        offset = 0;
        isCanLoadMore = true;
        presenter.getVkPopularTrack(genre, offset, count);
    }
}
