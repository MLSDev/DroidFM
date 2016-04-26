package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.AlbumsDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumsDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.AlbumsDetailScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.AlbumDetailsScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.AlbumsTracksAdapter;

import butterknife.Bind;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsDetailsFragment extends BaseFragment implements AlbumDetailsScreenView, AlbumsTracksAdapter.OnTopTrackClickListener {

    private static final String MBID_BUNDLE_KEY = "mbid_bundle_key_albums_detail_fragment";
    @Bind(R.id.tv_album_name)
    AppCompatTextView mTvAlbumName;
    @Bind(R.id.tv_artist_name)
    AppCompatTextView mTvAlbumArtistName;
    @Bind(R.id.tv_album_content)
    AppCompatTextView mTvAlbumContent;
    @Bind(R.id.tv_album_published)
    AppCompatTextView mTvAlbumPublished;
    @Bind(R.id.rv_tracks)
    RecyclerView mRvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;

    private AlbumsDetailScreenPresenter mPresenter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AlbumsTracksAdapter mAdapter;

    private String mMbid;

    public static AlbumsDetailsFragment newInstance(String mbid) {

        Bundle args = new Bundle();
        args.putString(MBID_BUNDLE_KEY, mbid);
        AlbumsDetailsFragment fragment = new AlbumsDetailsFragment();
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
        mMbid = getArguments().getString(MBID_BUNDLE_KEY);
        setupRvTracks();
        mPresenter = new AlbumsDetailScreenPresenterImpl(this);
        mPresenter.getAlbumsDetails(mMbid);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) menu.clear();
    }

    private void setupRvTracks() {
        mAdapter = new AlbumsTracksAdapter(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRvTracks.setAdapter(mAdapter);
        mRvTracks.setLayoutManager(mLayoutManager);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_album_details;
    }

    @Override
    public void updateToolbar() {
        if (isVisible()) getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public void showAlbumsDetails(AlbumsDetailEntity album) {
        mPbProgress.setVisibility(View.GONE);
        mTvAlbumArtistName.setText(album.getArtistName());
        mTvAlbumContent.setMovementMethod(LinkMovementMethod.getInstance());
        mTvAlbumContent.setText(Html.fromHtml(album.getContent()));
        mTvAlbumName.setText(album.getName());
        mTvAlbumPublished.setText(album.getPublished());
        mAdapter.setData(album.getTracks());
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(mRvTracks, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onTrackClick(TrackEntity topTrack) {

    }
}
