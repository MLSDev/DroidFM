package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.FavoriteTracksScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.FavoriteTrackScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.FavoriteTrackScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.BaseActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.FavoriteTracksAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteTracksFragment extends BaseFragment implements FavoriteTrackScreenView, FavoriteTracksAdapter.OnFavoriteTrackClickListener {

    @Bind(R.id.rv_tracks)
    RecyclerView mRvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;

    private FavoriteTracksAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FavoriteTracksScreenPresenter mPresenter;

    public static BaseFragment newInstance() {
        FavoriteTracksFragment fragment = new FavoriteTracksFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRvTracks();
        mPresenter = new FavoriteTrackScreenPresenterImpl(this);
        mPresenter.getFavoritesTrack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.stop();
        ButterKnife.unbind(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_favorite_tracks;
    }

    @Override
    public void updateToolbar() {
        ((BaseActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.favorite_section_title);
        getActivity().supportInvalidateOptionsMenu();
        ((Navigator) getActivity()).setDrawerToggleEnabled();
    }

    private void setupRvTracks() {
        mAdapter = new FavoriteTracksAdapter(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRvTracks.setLayoutManager(mLayoutManager);
        mRvTracks.setAdapter(mAdapter);
    }

    @Override
    public void showFavoriteTrack(List<FavoriteTrackEntity> tracks) {
        mAdapter.setData(tracks);
        mPbProgress.setVisibility(View.GONE);
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void onFavoriteTrackClick(FavoriteTrackEntity track) {
        ((Navigator) getActivity()).navigateToTrackDetails(track.getArtistName(), track.getTrackName(), "");
    }
}
