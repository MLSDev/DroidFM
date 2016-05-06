package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventCurrentTrackPause;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.MediaPlayerWrapper;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.TrackPlayerEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TrackDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TagWithUrlEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.TrackDetailScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TrackDetailScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.LinkUtil;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.TimeFormatUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by oleksandr on 27.04.16.
 */
public class TrackDetailFragment extends BaseFragment implements TrackDetailScreenView {

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
    NestedScrollView mNsScrollContainer;

    private TrackDetailScreenPresenter mPresenter;

    private String mBid;
    private String mArtist;
    private String mTrack;
    private String mTrackUrl;
    private String mAlbumImage;

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
        Bundle args = getArguments();
        mBid = args.getString(MBID_BUNDLE_KEY);
        mTrack = args.getString(TRACK_BUNDLE_KEY);
        mArtist = args.getString(ARTIST_BUNDLE_KEY);
        mPresenter = new TrackDetailScreenPresenterImpl(this);
        mPresenter.getTrackDetails(mArtist, mTrack, mBid);
        mPresenter.getTrackStreamUrl(mArtist + " - " + mTrack);
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
    }

    @Override
    public void showTrackStreamUrl(String url, int trackDuration) {
        if (mAlbumImage != null) mIvPlayPause.setVisibility(View.VISIBLE);
        mTvTrackDuration.setText(String.format(getString(R.string.duration), TimeFormatUtil.getFormattedTimeSecondsToMinutes(trackDuration)));
        mTrackUrl = url;
        if (MediaPlayerWrapper.getInstance().isTrackPlaying(mTrack)) {
            switch (MediaPlayerWrapper.getInstance().getCurrentState()) {
                case Retrieving:
                    mIvPlayPause.setImageResource(R.drawable.ic_play_grey600_36dp);
                    break;
                case Stopped:
                    mIvPlayPause.setImageResource(R.drawable.ic_play_grey600_36dp);
                    break;
                case Preparing:
                    mIvPlayPause.setImageResource(R.drawable.ic_play_grey600_36dp);
                    break;
                case Playing:
                    mIvPlayPause.setImageResource(R.drawable.ic_pause_grey600_36dp);
                    break;
                case Paused:
                    break;
            }
        } else {
            mIvPlayPause.setImageResource(R.drawable.ic_play_grey600_36dp);
        }
    }

    @Override
    public void showError(String errorMessage) {
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
            setPlayIcon();
    }

    @OnClick(R.id.iv_play_pause)
    public void onPlayPauseClick() {
        TrackPlayerEntity track = new TrackPlayerEntity();
        track.setmAlbumImageUrl(mAlbumImage);
        track.setmTrackUrl(mTrackUrl);
        track.setmTrackName(mTrack);
        track.setmArtistName(mArtist);
        if (!MediaPlayerWrapper.getInstance().isTrackPlaying(mTrack))
            MediaPlayerWrapper.getInstance().setFromAlbum(false);

        MediaPlayerWrapper.getInstance().playTrack(track, false);
        setPlayIcon();
    }

    private void setPlayIcon() {
        switch (MediaPlayerWrapper.getInstance().getCurrentState()) {
            case Retrieving:
                mIvPlayPause.setImageResource(R.drawable.ic_play_grey600_36dp);
                break;
            case Stopped:
                mIvPlayPause.setImageResource(R.drawable.ic_play_grey600_36dp);
                break;
            case Preparing:
                mIvPlayPause.setImageResource(R.drawable.ic_pause_grey600_36dp);
                break;
            case Playing:
                mIvPlayPause.setImageResource(R.drawable.ic_pause_grey600_36dp);
                break;
            case Paused:
                mIvPlayPause.setImageResource(R.drawable.ic_play_grey600_36dp);
                break;
        }
    }
}
