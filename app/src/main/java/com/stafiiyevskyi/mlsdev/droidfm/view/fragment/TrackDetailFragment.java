package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.TrackDetailScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TagWithUrlEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.TrackDetailEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.TrackDetailScreenPresenterImpl;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.TrackDetailScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.LinkUtil;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.TimeFormatUtil;

import butterknife.Bind;

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

    private TrackDetailScreenPresenter mPresenter;

    private String mBid;
    private String mArtist;
    private String mTrack;

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

        mTvTrackDuration.setText(String.format(getString(R.string.duration), TimeFormatUtil.getFormattedTimeMillisToMinutes(Integer.valueOf(track.getDuration()))));
    }

    @Override
    public void showError(String errorMessage) {
        Log.e("TrackDetail", errorMessage);
        mPbProgress.setVisibility(View.GONE);
        Snackbar.make(mTvTrackContent, errorMessage, Snackbar.LENGTH_LONG).show();
    }
}
