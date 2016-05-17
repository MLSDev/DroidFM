package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.ArtistDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.ArtistDetailScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistDetailScreenView;

import butterknife.Bind;

/**
 * Created by oleksandr on 25.04.16.
 */
public class ArtistDetailFullFragment extends BaseFragment implements ArtistDetailScreenView, SwipeRefreshLayout.OnRefreshListener {
    private static final String MBID_BUNDLE_KEY = "mbid_bundle_key_artist_details_full_fragment";


    @Bind(R.id.tv_artist_published)
    AppCompatTextView tvArtistPublishedInfo;
    @Bind(R.id.tv_artist_summary)
    AppCompatTextView tvArtistSummary;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private ArtistDetailScreenPresenter presenter;
    private String mbid;
    private String imageUrl;

    public static BaseFragment newInstance(@NonNull String mbid) {

        Bundle args = new Bundle();
        args.putString(MBID_BUNDLE_KEY, mbid);
        BaseFragment fragment = new ArtistDetailFullFragment();
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
        setupSwipeRefresh();
        mbid = getArguments().getString(MBID_BUNDLE_KEY);
        presenter = new ArtistDetailScreenPresenterImpl(this);
        presenter.getArtistInformation("", mbid);
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

    private void setupSwipeRefresh() {
        srlRefresh.setOnRefreshListener(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_artist_detail_info;
    }

    @Override
    public void updateToolbar() {
        getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public void showArtistDetailInformation(ArtistDetailEntity artist) {
        srlRefresh.setRefreshing(false);
        pbProgress.setVisibility(View.GONE);
        tvArtistPublishedInfo.setText(artist.getPublished());
        tvArtistSummary.setMovementMethod(LinkMovementMethod.getInstance());
        tvArtistSummary.setText(Html.fromHtml(artist.getSummary()));
    }

    @Override
    public void showError(String errorMessage) {
        srlRefresh.setRefreshing(false);
        Snackbar.make(tvArtistPublishedInfo, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        presenter.getArtistInformation("", mbid);
    }
}
