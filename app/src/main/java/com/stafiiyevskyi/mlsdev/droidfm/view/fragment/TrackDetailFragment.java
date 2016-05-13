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

    @Bind(R.id.tv_track_name)
    AppCompatTextView mTvTrackName;
    @Bind(R.id.tv_track_duration)
    AppCompatTextView mTvTrackDuration;
    @Bind(R.id.tv_artist_name)
    AppCompatTextView mTvArtistName;
    @Bind(R.id.tv_track_content)
    AppCompatTextView mTvTrackContent;
    @Bind(R.id.tv_track_published)
    AppCompatTextView mTvTrackPublished;
    @Bind(R.id.tv_track_tags)
    AppCompatTextView mTvTrackTags;
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;
    @Bind(R.id.iv_play_pause)
    AppCompatImageView mIvPlayPause;
    @Bind(R.id.nested_scroll)
    ScrollView mNsScrollContainer;
    @Bind(R.id.iv_add_to_favorite)
    AppCompatImageView mIvAddToFavorite;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    @Bind(R.id.iv_save_track)
    AppCompatImageView mIvSaveTrack;

    private TrackDetailScreenPresenter mPresenter;

    private String mBid;
    private String mArtist;
    private String mTrack;
    private String mTrackUrl;
    private String mAlbumImage;
    private boolean mIsFavorite = false;

    private TrackDetailEntity mDetailEntity;

    public static TrackDetailFragment newInstance(String artist, String track, String mbid) {

        Bundle args = new Bundle();
        args.putString(TRACK_BUNDLE_KEY, track);
        args.putString(ARTIST_BUNDLE_KEY, artist);
        args.putString(MBID_BUNDLE_KEY, mbid);
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
        mSrlRefresh.setOnRefreshListener(this);
        Bundle args = getArguments();
        mBid = args.getString(MBID_BUNDLE_KEY);
        mTrack = args.getString(TRACK_BUNDLE_KEY);
        mArtist = args.getString(ARTIST_BUNDLE_KEY);
        mPresenter = new TrackDetailScreenPresenterImpl(this);
        mPresenter.getTrackDetails(mArtist, mTrack, mBid);
        mPresenter.getTrackStreamUrl(mArtist + " - " + mTrack);
        mTvArtistName.setText(mArtist);
        mTvTrackName.setText(mTrack);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.stop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_track_detail;
    }

    @Override
    public void updateToolbar() {
        if (isVisible()) getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public void showTrackDetail(TrackDetailEntity track) {
        mSrlRefresh.setRefreshing(false);
        mDetailEntity = track;
        if (mTrackUrl != null) mIvPlayPause.setVisibility(View.VISIBLE);
        mAlbumImage = track.getAlbumImage();
        mPbProgress.setVisibility(View.GONE);
        mTvArtistName.setText(track.getArtistName());
        mTvTrackName.setText(track.getName());


        if (track.getContent() != null) {
            mTvTrackContent.setMovementMethod(LinkMovementMethod.getInstance());
            mTvTrackContent.setText(Html.fromHtml(track.getContent()));
            mTvTrackPublished.setText(track.getPublished());
        }


        StringBuilder builder = new StringBuilder();
        for (TagWithUrlEntity tagWithUrlEntity : track.getTags()) {
            String link = LinkUtil.getHtmlLink(tagWithUrlEntity.getName(), tagWithUrlEntity.getUrl());
            builder.append(link + ";");
        }
        String tagsString = builder.toString();
        mTvTrackTags.setMovementMethod(LinkMovementMethod.getInstance());
        mTvTrackTags.setText(Html.fromHtml(tagsString));
        mPresenter.isTrackFavorite(track);
    }

    @Override
    public void showTrackStreamUrl(String url, int trackDuration) {
        mIvPlayPause.setVisibility(View.VISIBLE);
        mIvSaveTrack.setVisibility(View.VISIBLE);
        mTvTrackDuration.setText(String.format(getString(R.string.duration), TimeFormatUtil.getFormattedTimeSecondsToMinutes(trackDuration)));
        mTrackUrl = url;
        if (MediaPlayerWrapper.getInstance().isTrackPlaying(mTrack)) {
            MusicPlayerUtil.setupPlayIconState(mIvPlayPause);
        } else {
            mIvPlayPause.setImageResource(R.drawable.ic_play_grey600_36dp);
        }
    }

    @Override
    public void showTrackIsFavorite(boolean isFavorite) {
        this.mIsFavorite = isFavorite;
        if (isFavorite) {
            mIvAddToFavorite.setImageResource(R.drawable.ic_star_grey600_36dp);
        } else mIvAddToFavorite.setImageResource(R.drawable.ic_star_outline_grey600_36dp);
    }

    @Override
    public void onSuccess() {
        mPresenter.isTrackFavorite(mDetailEntity);
    }

    @Override
    public void showError(String errorMessage) {
        mSrlRefresh.setRefreshing(false);
        mPbProgress.setVisibility(View.GONE);
        Snackbar.make(mNsScrollContainer, R.string.not_track_error_message, Snackbar.LENGTH_LONG).show();
    }

    @Subscribe
    public void trackStartEvent(TrackPlayerEntity event) {
        mTrack = event.getmTrackName();
        mAlbumImage = event.getmAlbumImageUrl();
        mArtist = event.getmArtistName();
        mTrackUrl = event.getmTrackUrl();
        mIvPlayPause.setImageResource(R.drawable.ic_pause_grey600_36dp);
    }

    @Subscribe
    public void trackPauseEvent(EventCurrentTrackPause event) {
        if (MediaPlayerWrapper.getInstance().isTrackPlaying(mTrack))
            MusicPlayerUtil.setupPlayIconState(mIvPlayPause);
    }

    @OnClick(R.id.iv_play_pause)
    public void onPlayPauseClick() {
        TrackPlayerEntity track = new TrackPlayerEntity();
        track.setmAlbumImageUrl(mAlbumImage);
        track.setmTrackName(mTrack);
        track.setmArtistName(mArtist);
        Pair<Boolean, Uri> pair = FileTrackUtil.isTrackExist(getActivity(), track.getmArtistName(), track.getmTrackName());
        if (pair.first) {
            track.setmTrackUrl(pair.second.getPath());
        } else {
            track.setmTrackUrl(mTrackUrl);
        }
        if (!MediaPlayerWrapper.getInstance().isTrackPlaying(mTrack))
            MediaPlayerWrapper.getInstance().setFromAlbum(false);

        MediaPlayerWrapper.getInstance().playTrack(track, false);
        MusicPlayerUtil.setupPlayIconState(mIvPlayPause);
    }

    @OnClick(R.id.iv_add_to_favorite)
    public void onAddToFavoriteClick() {
        FavoriteTrackEntity track = new FavoriteTrackEntity();
        track.setTrackName(mTrack);
        track.setArtistName(mArtist);
        if (mIsFavorite) {
            mPresenter.deleteFromFavorite(track);
        } else {
            mPresenter.addTrackToFavorite(track);
        }
    }

    @OnClick(R.id.iv_save_track)
    public void onSaveTrackClick() {
        EventBus.getDefault().post(new EventDownloadTrack(mTrackUrl, mDetailEntity));
    }

    @Override
    public void onRefresh() {
        mPresenter.getTrackDetails(mArtist, mTrack, mBid);
        mPresenter.getTrackStreamUrl(mArtist + " - " + mTrack);
    }
}
