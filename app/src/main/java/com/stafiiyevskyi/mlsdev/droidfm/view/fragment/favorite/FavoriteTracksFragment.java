package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.FavoriteTracksScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.FavoriteTrackScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.FavoriteTrackScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.FavoriteTracksAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteTracksFragment extends BaseFragment implements FavoriteTrackScreenView, FavoriteTracksAdapter.OnFavoriteTrackClickListener {

    @Bind(R.id.rv_tracks)
    RecyclerView rvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;

    private FavoriteTracksAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FavoriteTracksScreenPresenter presenter;

    public static BaseFragment newInstance() {
        FavoriteTracksFragment fragment = new FavoriteTracksFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRvTracks();
        presenter = new FavoriteTrackScreenPresenterImpl(this);
        presenter.getFavoritesTrack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.stop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) menu.clear();
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_favorite_tracks;
    }

    @Override
    public void updateToolbar() {
        getActivity().supportInvalidateOptionsMenu();

    }

    private void setupRvTracks() {
        adapter = new FavoriteTracksAdapter(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rvTracks.setLayoutManager(layoutManager);
        rvTracks.setAdapter(adapter);
    }

    @Override
    public void showFavoriteTrack(List<FavoriteTrackEntity> tracks) {
        adapter.setData(tracks);
        pbProgress.setVisibility(View.GONE);
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
