package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventPlaylistStart;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.AlbumsDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.AlbumsDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.AlbumsDetailScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.AlbumDetailsScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.AlbumsTracksAdapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by oleksandr on 26.04.16.
 */
public class AlbumsDetailsFragment extends BaseFragment implements AlbumDetailsScreenView, AlbumsTracksAdapter.OnTopTrackClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String MBID_BUNDLE_KEY = "mbid_bundle_key_albums_detail_fragment";
    private static final String ARTIST_BUNDLE_KEY = "artist_bundle_key_albums_detail_fragment";
    private static final String ALBUM_BUNDLE_KEY = "album_bundle_key_albums_detail_fragment";
    private static final String ALBUM_IMAGE_BUNDLE_KEY = "album_image_bundle_key_albums_detail_fragment";

    @Bind(R.id.tv_album_name)
    AppCompatTextView tvAlbumName;
    @Bind(R.id.tv_artist_name)
    AppCompatTextView tvAlbumArtistName;
    @Bind(R.id.tv_album_content)
    AppCompatTextView tvAlbumContent;
    @Bind(R.id.tv_album_published)
    AppCompatTextView tvAlbumPublished;
    @Bind(R.id.rv_tracks)
    RecyclerView rvTracks;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.iv_play_album)
    AppCompatImageView ivPlayAlbum;
    @Bind(R.id.iv_add_to_favorite)
    AppCompatImageView ivAddToFavorite;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private AlbumsDetailScreenPresenter presenter;
    private RecyclerView.LayoutManager layoutManager;
    private AlbumsTracksAdapter adapter;
    private AlbumsDetailEntity albumsDetailEntity;

    private String mbid;
    private String artist;
    private String album;
    private String albumImage;

    private boolean isFavorite = false;


    public static AlbumsDetailsFragment newInstance(String artist, String album, String mbid, String albumImage) {

        Bundle args = new Bundle();
        args.putString(MBID_BUNDLE_KEY, mbid);
        args.putString(ARTIST_BUNDLE_KEY, artist);
        args.putString(ALBUM_BUNDLE_KEY, album);
        args.putString(ALBUM_IMAGE_BUNDLE_KEY, albumImage);
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
        Bundle arg = getArguments();
        artist = arg.getString(ARTIST_BUNDLE_KEY);
        album = arg.getString(ALBUM_BUNDLE_KEY);
        mbid = arg.getString(MBID_BUNDLE_KEY);
        albumImage = arg.getString(ALBUM_IMAGE_BUNDLE_KEY);
        setupRvTracks();
        setupSwipeRefresh();
        presenter = new AlbumsDetailScreenPresenterImpl(this);
        presenter.getAlbumsDetails(artist, album, mbid);
        ((Navigator) getActivity()).setDrawerToggleNotEnabled();
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(album);
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

    private void setupRvTracks() {
        adapter = new AlbumsTracksAdapter(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rvTracks.setAdapter(adapter);
        rvTracks.setLayoutManager(layoutManager);
    }

    private void setupSwipeRefresh() {
        srlRefresh.setOnRefreshListener(this);
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
        srlRefresh.setRefreshing(false);
        albumsDetailEntity = album;
        pbProgress.setVisibility(View.GONE);
        tvAlbumArtistName.setText(album.getArtistName());
        if (album.getContent() != null) {
            tvAlbumPublished.setText(album.getPublished());
            tvAlbumContent.setMovementMethod(LinkMovementMethod.getInstance());
            tvAlbumContent.setText(Html.fromHtml(album.getContent()));
        }

        tvAlbumName.setText(album.getName());
        adapter.setData(album.getTracks());
        presenter.isTrackFavorite(albumsDetailEntity);
    }

    @Override
    public void showAlbumIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
        if (isFavorite) {
            ivAddToFavorite.setImageResource(R.drawable.ic_star_grey600_36dp);
        } else ivAddToFavorite.setImageResource(R.drawable.ic_star_outline_grey600_36dp);
    }

    @Override
    public void onSuccess() {

    }


    @Override
    public void showError(String errorMessage) {
        srlRefresh.setRefreshing(false);
        pbProgress.setVisibility(View.GONE);
        Log.e("AlbumsDetail", errorMessage);
        Snackbar.make(rvTracks, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onTrackClick(TrackEntity topTrack) {
        ((Navigator) getActivity()).navigateToTrackDetails(topTrack.getArtistName(), topTrack.getName(), "");
    }

    @OnClick(R.id.iv_play_album)
    public void onPlayAlbumClick() {
        if (albumsDetailEntity != null && albumsDetailEntity.getTracks() != null && albumsDetailEntity.getTracks().size() > 0) {
            EventPlaylistStart event = new EventPlaylistStart();
            event.setData(albumsDetailEntity.getTracks());
            event.setAlbumImageUrl(albumImage);
            EventBus.getDefault().post(event);
        }

    }

    @OnClick(R.id.iv_add_to_favorite)
    public void onAddToFavoriteClick() {
        if (albumsDetailEntity != null) {
            if (isFavorite) {
                presenter.deleteFromFavorite(albumsDetailEntity);
            } else {
                presenter.addAlbumToFavorite(albumsDetailEntity);
            }
        }

    }

    @Override
    public void onRefresh() {
        presenter.getAlbumsDetails(artist, album, mbid);
    }
}
