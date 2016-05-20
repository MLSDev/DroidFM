package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventCurrentTrackPause;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventDownloadTrack;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.MediaPlayerWrapper;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.TrackPlayerEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TrackDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TagWithUrlEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.TrackDetailScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TrackDetailScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.FileTrackUtil;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.LinkUtil;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.MusicPlayerUtil;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.TimeFormatUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by oleksandr on 27.04.16.
 */
public class TrackDetailFragment extends BaseFragment implements TrackDetailScreenView, SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = TrackDetailFragment.class.getSimpleName();

    private static final String MBID_BUNDLE_KEY = "mbid_bundle_key_track_detail_fragment";
    private static final String ARTIST_BUNDLE_KEY = "artist_bundle_key_track_detail_fragment";
    private static final String TRACK_BUNDLE_KEY = "track_bundle_key_track_detail_fragment";
    private static final String TRACK_URL_BUNDLE_KEY = "track_url_bundle_key_detail_fragment";
    private static final String TRACK_DURATION_BUNDLE_KEY = "track_duration_bundle_key_detail_fragment";

    @Bind(R.id.tv_track_name)
    AppCompatTextView tvTrackName;
    @Bind(R.id.tv_track_duration)
    AppCompatTextView tvTrackDuration;
    @Bind(R.id.tv_artist_name)
    AppCompatTextView tvArtistName;
    @Bind(R.id.tv_track_content)
    AppCompatTextView tvTrackContent;
    @Bind(R.id.tv_track_published)
    AppCompatTextView tvTrackPublished;
    @Bind(R.id.tv_track_tags)
    AppCompatTextView tvTrackTags;
    @Bind(R.id.pb_progress)
    ProgressBar pbProgress;
    @Bind(R.id.iv_play_pause)
    AppCompatImageView ivPlayPause;
    @Bind(R.id.nested_scroll)
    ScrollView nsScrollContainer;
    @Bind(R.id.iv_add_to_favorite)
    AppCompatImageView ivAddToFavorite;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @Bind(R.id.iv_save_track)
    AppCompatImageView ivSaveTrack;

    private TrackDetailScreenPresenter presenter;

    private String mbid;
    private String artist;
    private String track;
    private String trackUrl;
    private String albumImage;
    private int duration = 0;
    private boolean isFavorite = false;

    private TrackDetailEntity detailEntity;

    public static TrackDetailFragment newInstance(String artist, String track, String mbid) {

        Bundle args = new Bundle();
        args.putString(TRACK_BUNDLE_KEY, track);
        args.putString(ARTIST_BUNDLE_KEY, artist);
        args.putString(MBID_BUNDLE_KEY, mbid);
        TrackDetailFragment fragment = new TrackDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static TrackDetailFragment newInstance(String artist, String track, String trackUrl, int duration) {

        Bundle args = new Bundle();
        args.putString(TRACK_BUNDLE_KEY, track);
        args.putString(ARTIST_BUNDLE_KEY, artist);
        args.putInt(TRACK_DURATION_BUNDLE_KEY, duration);
        args.putString(TRACK_URL_BUNDLE_KEY, trackUrl);
        TrackDetailFragment fragment = new TrackDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isVisible()) menu.clear();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Navigator) getActivity()).setDrawerToggleNotEnabled();
        srlRefresh.setOnRefreshListener(this);
        Bundle args = getArguments();
        mbid = args.getString(MBID_BUNDLE_KEY);
        track = args.getString(TRACK_BUNDLE_KEY);
        artist = args.getString(ARTIST_BUNDLE_KEY);
        trackUrl = args.getString(TRACK_URL_BUNDLE_KEY);
        duration = args.getInt(TRACK_DURATION_BUNDLE_KEY);
        presenter = new TrackDetailScreenPresenterImpl(this);
        presenter.getTrackDetails(artist, track, mbid);

        if (trackUrl != null)
            presenter.getTrackStreamUrl(artist + " - " + track);
        else showTrackStreamUrl(trackUrl, duration);

        tvArtistName.setText(artist);
        tvTrackName.setText(track);
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(artist + " - " + track);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.stop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_track_detail;
    }

    @Override
    public void updateToolbar() {
        if (isVisible()) getActivity().supportInvalidateOptionsMenu();
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(artist + " - " + track);
    }

    @Override
    public void showTrackDetail(TrackDetailEntity track) {
        srlRefresh.setRefreshing(false);
        detailEntity = track;
        if (trackUrl != null) ivPlayPause.setVisibility(View.VISIBLE);
        albumImage = track.getAlbumImage();
        pbProgress.setVisibility(View.GONE);
        tvArtistName.setText(track.getArtistName());
        tvTrackName.setText(track.getName());


        if (track.getContent() != null) {
            tvTrackContent.setMovementMethod(LinkMovementMethod.getInstance());
            tvTrackContent.setText(Html.fromHtml(track.getContent()));
            tvTrackPublished.setText(track.getPublished());
        }


        StringBuilder builder = new StringBuilder();
        for (TagWithUrlEntity tagWithUrlEntity : track.getTags()) {
            String link = LinkUtil.getHtmlLink(tagWithUrlEntity.getName(), tagWithUrlEntity.getUrl());
            builder.append(link + ";");
        }
        String tagsString = builder.toString();
        tvTrackTags.setMovementMethod(LinkMovementMethod.getInstance());
        tvTrackTags.setText(Html.fromHtml(tagsString));
        presenter.isTrackFavorite(track);
    }

    @Override
    public void showTrackStreamUrl(String url, int trackDuration) {
        ivPlayPause.setVisibility(View.VISIBLE);
        ivSaveTrack.setVisibility(View.VISIBLE);
        tvTrackDuration.setText(String.format(getString(R.string.duration), TimeFormatUtil.getFormattedTimeSecondsToMinutes(trackDuration)));
        trackUrl = url;
        if (MediaPlayerWrapper.getInstance().isTrackPlaying(track)) {
            MusicPlayerUtil.setupPlayIconState(ivPlayPause);
        } else {
            ivPlayPause.setImageResource(R.drawable.ic_play_grey600_36dp);
        }
    }

    @Override
    public void showTrackIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
        if (isFavorite) {
            ivAddToFavorite.setImageResource(R.drawable.ic_star_grey600_36dp);
        } else ivAddToFavorite.setImageResource(R.drawable.ic_star_outline_grey600_36dp);
    }

    @Override
    public void onSuccess() {
        presenter.isTrackFavorite(detailEntity);
    }

    @Override
    public void showError(String errorMessage) {
        srlRefresh.setRefreshing(false);
        pbProgress.setVisibility(View.GONE);
        Snackbar.make(nsScrollContainer, R.string.not_track_error_message, Snackbar.LENGTH_LONG).show();
    }

    @Subscribe
    public void trackStartEvent(TrackPlayerEntity event) {
        track = event.getmTrackName();
        albumImage = event.getmAlbumImageUrl();
        artist = event.getmArtistName();
        trackUrl = event.getmTrackUrl();
        ivPlayPause.setImageResource(R.drawable.ic_pause_grey600_36dp);
    }

    @Subscribe
    public void trackPauseEvent(EventCurrentTrackPause event) {
        if (MediaPlayerWrapper.getInstance().isTrackPlaying(track))
            MusicPlayerUtil.setupPlayIconState(ivPlayPause);
    }

    @OnClick(R.id.iv_play_pause)
    public void onPlayPauseClick() {
        TrackPlayerEntity track = new TrackPlayerEntity();
        track.setmAlbumImageUrl(albumImage);
        track.setmTrackName(this.track);
        track.setmArtistName(artist);
        Pair<Boolean, Uri> pair = FileTrackUtil.isTrackExist(getActivity(), track.getmArtistName(), track.getmTrackName());
        if (pair.first) {
            track.setmTrackUrl(pair.second.getPath());
        } else {
            track.setmTrackUrl(trackUrl);
        }
        if (!MediaPlayerWrapper.getInstance().isTrackPlaying(this.track))
            MediaPlayerWrapper.getInstance().setFromAlbum(false);

        MediaPlayerWrapper.getInstance().playTrack(track, false);
        MusicPlayerUtil.setupPlayIconState(ivPlayPause);
    }

    @OnClick(R.id.iv_add_to_favorite)
    public void onAddToFavoriteClick() {
        FavoriteTrackEntity track = new FavoriteTrackEntity();
        track.setTrackName(this.track);
        track.setArtistName(artist);
        if (isFavorite) {
            presenter.deleteFromFavorite(track);
        } else {
            presenter.addTrackToFavorite(track);
        }
    }

    @OnClick(R.id.iv_save_track)
    public void onSaveTrackClick() {
        EventBus.getDefault().post(new EventDownloadTrack(trackUrl, detailEntity));
    }

    @OnClick(R.id.tv_similar_tracks)
    public void onSimilarTracksClick() {
        ((Navigator) getActivity()).navigateToSimilarTracks(artist, track);
    }

    @Override
    public void onRefresh() {
        presenter.getTrackDetails(artist, track, mbid);
        presenter.getTrackStreamUrl(artist + " - " + track);
    }
}
