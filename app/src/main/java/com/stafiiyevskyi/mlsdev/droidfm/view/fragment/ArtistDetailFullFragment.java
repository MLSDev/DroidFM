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
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 25.04.16.
 */
public class ArtistDetailFullFragment extends BaseFragment implements ArtistDetailScreenView, SwipeRefreshLayout.OnRefreshListener {
    private static final String MBID_BUNDLE_KEY = "mbid_bundle_key_artist_details_full_fragment";


    @Bind(R.id.tv_artist_published)
    AppCompatTextView mTvArtistPublishedInfo;
    @Bind(R.id.tv_artist_summary)
    AppCompatTextView mTvArtistSummary;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    private ArtistDetailScreenPresenter mPresenter;
    private String mMbid;
    private String mImageUrl;

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
        mMbid = getArguments().getString(MBID_BUNDLE_KEY);
        mPresenter = new ArtistDetailScreenPresenterImpl(this);
        mPresenter.getArtistInformation("", mMbid);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) menu.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mPresenter.stop();
    }

    private void setupSwipeRefresh() {
        mSrlRefresh.setOnRefreshListener(this);
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
        mSrlRefresh.setRefreshing(false);
        mPbProgress.setVisibility(View.GONE);
        mTvArtistPublishedInfo.setText(artist.getPublished());
        mTvArtistSummary.setMovementMethod(LinkMovementMethod.getInstance());
        mTvArtistSummary.setText(Html.fromHtml(artist.getSummary()));
    }

    @Override
    public void showError(String errorMessage) {
        mSrlRefresh.setRefreshing(false);
        Snackbar.make(mTvArtistPublishedInfo, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        mPresenter.getArtistInformation("", mMbid);
    }
}
