package com.stafiiyevskyi.mlsdev.droidfm.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.stafiiyevskyi.mlsdev.droidfm.JUnitTestHelper;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventCurrentTrackPause;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventDownloadCurrentTrack;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventPlayAllSavedTracks;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventPlaylistStart;
import com.stafiiyevskyi.mlsdev.droidfm.app.event.EventSynchronizingAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.MediaPlayerWrapper;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.TrackPlayerEntity;
import com.stafiiyevskyi.mlsdev.droidfm.app.service.DownloadService;
import com.stafiiyevskyi.mlsdev.droidfm.app.service.TracksPlayerService;
import com.stafiiyevskyi.mlsdev.droidfm.app.util.NetworkUtil;
import com.stafiiyevskyi.mlsdev.droidfm.app.util.PreferencesManager;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.adapter.AlbumPlaylistAdapter;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.AlbumsDetailsFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.ArtistContentDetailsFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.ArtistDetailFullFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.SavedTracksFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.SimilarArtistsFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.SimilarTracksFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.TrackDetailFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart.ArtistSearchListFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart.ChartTopTracksFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart.TopChartsContentFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.favorite.FavoriteContentFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.favorite.FavoriteTracksFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.signin.LoginVKDialogFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.tag.TagTopContentFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.MusicPlayerUtil;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.SeekBarUtils;
import com.stafiiyevskyi.mlsdev.droidfm.view.widget.MenuArrowDrawable;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

public class MainActivity extends BaseActivity implements Navigator, SeekBar.OnSeekBarChangeListener, AlbumPlaylistAdapter.OnPlaylistTrackClick {
    @Bind(R.id.drawer_layout)
    DrawerLayout drNavigation;
    @Bind(R.id.nav_view)
    NavigationView nvNavigation;

    @Bind(R.id.sb_player_seekbar)
    SeekBar sbSeekbar;
    @Bind(R.id.tv_play_track_name)
    AppCompatTextView tvPlayTrackName;
    @Bind(R.id.iv_album_image)
    AppCompatImageView ivAlbumsTrackImage;
    @Bind(R.id.iv_play_pause)
    AppCompatImageView ivPlayPause;
    @Bind(R.id.tv_current_track_position)
    AppCompatTextView tvCurrentTrackPosition;
    @Bind(R.id.tv_track_duration)
    AppCompatTextView tvTrackTotalDuration;
    @Bind(R.id.rv_playlist)
    RecyclerView rvPlaylist;

    private FragmentManager fragmentManager;
    private AlbumPlaylistAdapter playlistAdapter;

    private ActionBarDrawerToggle drawerToggle;
    private MenuArrowDrawable drawerArrowDrawable;
    private BaseFragment firstFragment;
    private Handler handler;
    private String albumImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        handler = new Handler();
        setupPlayerWidget();
        fragmentManager = getSupportFragmentManager();
        setupNavigation();
        startService(new Intent(this, TracksPlayerService.class));
        startService(new Intent(this, DownloadService.class));

        if (!JUnitTestHelper.getInstance().isJunitRunning())
            VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
                @Override
                public void onResult(VKSdk.LoginState res) {
                    switch (res) {
                        case LoggedOut:
                            getSupportActionBar().setSubtitle(getString(R.string.artists_section_title));
                            navigateToArtistsSearchScreen();
                            navigateToLoginVKDialog();
                            return;
                        case LoggedIn:
                            getSupportActionBar().setSubtitle(getString(R.string.artists_section_title));
                            navigateToArtistsSearchScreen();
                            return;
                        case Pending:
                            if (!NetworkUtil.isNetworkConnected(MainActivity.this)) {
                                Toast.makeText(MainActivity.this, "No Internet connection", Toast.LENGTH_LONG).show();
                            }
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onError(VKError error) {
                    Toast.makeText(MainActivity.this, error.errorMessage, Toast.LENGTH_LONG).show();
                }
            });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        handler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                PreferencesManager.getInstance().setAccessToken(res.accessToken);
                PreferencesManager.getInstance().setUserId(res.userId);
                navigateToArtistsSearchScreen();
            }

            @Override
            public void onError(VKError error) {

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            onBackPressed();
            return super.onOptionsItemSelected(item);
        }
    }

    private void setupNavigation() {
        drawerArrowDrawable = new MenuArrowDrawable(new ContextThemeWrapper(this, R.style.AppTheme_AppBarOverlay), getSupportActionBar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drNavigation,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        nvNavigation.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_tracks_item:
                    if (!(firstFragment instanceof ChartTopTracksFragment)) {
                        navigateToTopTracksScreen();
                        getSupportActionBar().setSubtitle(getString(R.string.tracks_section_title));
                    }
                    drNavigation.closeDrawers();
                    return true;
                case R.id.action_artists_item:
                    if (!(firstFragment instanceof ArtistSearchListFragment)) {
                        navigateToArtistsSearchScreen();
                        getSupportActionBar().setSubtitle(getString(R.string.artists_section_title));
                    }

                    drNavigation.closeDrawers();
                    return true;
                case R.id.action_charts_item:
                    if (!(firstFragment instanceof TopChartsContentFragment)) {
                        navigateToChartsContentScreen();
                        getSupportActionBar().setSubtitle(getString(R.string.charts_section_title));
                    }
                    drNavigation.closeDrawers();
                    return true;
                case R.id.action_favorite_item:
                    if (!(firstFragment instanceof FavoriteContentFragment)) {
                        navigateToFavoritesScreen();
                        getSupportActionBar().setSubtitle(getString(R.string.favorite_section_title));
                    }
                    drNavigation.closeDrawers();
                    return true;
                case R.id.action_saved_item:
                    if (!(firstFragment instanceof SavedTracksFragment)) {
                        navigateToSavedTracksScreen();
                        getSupportActionBar().setSubtitle(getString(R.string.saved_section_title));
                    }
                    drNavigation.closeDrawers();
                    return true;
                default:
                    return true;
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void setDrawerToggleEnabled() {
        backToMenu();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drNavigation.addDrawerListener(drawerToggle);
    }

    @Override
    public void setDrawerToggleNotEnabled() {
        menuToBack();
        drawerToggle.setDrawerIndicatorEnabled(false);
        drNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void menuToBack() {
        this.drawerArrowDrawable.animateDrawable(true);
    }

    public void backToMenu() {
        this.drawerArrowDrawable.animateDrawable(false);
    }

    @Override
    public void navigateToArtistsSearchScreen() {
        firstFragment = ArtistSearchListFragment.newInstance();
        AnimationUtil.detailTransition(firstFragment);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, firstFragment)
                .commit();
    }

    @Override
    public void navigateToSimilarArtistsScreen(String artistName) {
        BaseFragment fragment = SimilarArtistsFragment.newInstance(artistName);
        AnimationUtil.detailTransition(fragment);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, SimilarArtistsFragment.class.getName() + artistName)
                .addToBackStack(SimilarArtistsFragment.class.getName() + artistName)
                .commit();
    }

    @Override
    public void navigateToChartsContentScreen() {
        firstFragment = TopChartsContentFragment.newInstance();
        AnimationUtil.detailTransition(firstFragment);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, firstFragment)
                .commit();
    }

    @Override
    public void navigateToArtistContentDetailsScreen(String mbid, String artistName, String imageUrl, AppCompatImageView imageView) {
        BaseFragment fragment = ArtistContentDetailsFragment.newInstance(mbid, artistName, imageUrl);
        AnimationUtil.detailTransitionShared(fragment, imageView, getString(R.string.transition_artist_image));
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, ArtistContentDetailsFragment.class.getName() + mbid)
                .addSharedElement(imageView, getString(R.string.transition_artist_image))
                .addToBackStack(ArtistContentDetailsFragment.class.getName() + mbid)
                .commit();
    }

    public void navigateToArtistContentDetailsScreen(String mbid, String artistName, String imageUrl) {
        BaseFragment fragment = ArtistContentDetailsFragment.newInstance(mbid, artistName, imageUrl);
        AnimationUtil.detailTransition(firstFragment);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, ArtistContentDetailsFragment.class.getName() + mbid)
                .addToBackStack(ArtistContentDetailsFragment.class.getName() + mbid)
                .commit();
    }

    @Override
    public void navigateToTopTracksScreen() {
        firstFragment = ChartTopTracksFragment.newInstance(false);
        AnimationUtil.detailTransition(firstFragment);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, firstFragment)
                .commit();
    }

    @Override
    public void navigateToSimilarTracks(String artistName, String track) {
        BaseFragment fragment = SimilarTracksFragment.newInstance(artistName, track);
        AnimationUtil.detailTransition(fragment);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, SimilarTracksFragment.class.getName() + artistName + track)
                .addToBackStack(SimilarTracksFragment.class.getName() + artistName + track)
                .commit();
    }

    @Override
    public void navigateToArtistFullDetailsScreen(String mbid) {
        BaseFragment fragment = ArtistDetailFullFragment.newInstance(mbid);
        AnimationUtil.detailTransition(fragment);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, ArtistDetailFullFragment.class.getName() + mbid)
                .addToBackStack(ArtistDetailFullFragment.class.getName() + mbid)
                .commit();
    }

    @Override
    public void navigateToTagTopContent(String tag) {
        BaseFragment fragment = TagTopContentFragment.newInstance(tag);
        AnimationUtil.detailTransition(fragment);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, TagTopContentFragment.class.getName() + tag)
                .addToBackStack(TagTopContentFragment.class.getName() + tag)
                .commit();
    }

    @Override
    public void navigateToAlbumDetails(String artist, String album, String mbid, String albumImage) {
        BaseFragment fragment = AlbumsDetailsFragment.newInstance(artist, album, mbid, albumImage);
        AnimationUtil.detailTransition(fragment);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, AlbumsDetailsFragment.class.getName() + mbid)
                .addToBackStack(AlbumsDetailsFragment.class.getName() + mbid)
                .commit();
    }

    @Override
    public void navigateToTrackDetails(String artist, String track, String mbid) {
        BaseFragment fragment = TrackDetailFragment.newInstance(artist, track, mbid);
        AnimationUtil.detailTransition(fragment);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, TrackDetailFragment.class.getName() + mbid)
                .addToBackStack(TrackDetailFragment.class.getName() + mbid)
                .commit();
    }

    @Override
    public void navigateToSavedTracksScreen() {
        firstFragment = SavedTracksFragment.newInstance();
        AnimationUtil.detailTransition(firstFragment);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, firstFragment)
                .commit();
    }

    @Override
    public void navigateToLoginVKDialog() {
        LoginVKDialogFragment.newInstance().show(fragmentManager, LoginVKDialogFragment.class.getName());
    }

    @Override
    public void navigateToFavoritesScreen() {
        firstFragment = FavoriteContentFragment.newInstance();
        AnimationUtil.detailTransition(firstFragment);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, firstFragment)
                .commit();
    }

    @Override
    public void navigateBack() {
        int i = fragmentManager.getBackStackEntryCount();
        if (i >= 1) {
            FragmentManager.BackStackEntry backEntry = fragmentManager
                    .getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
            String str = backEntry.getName();
            BaseFragment currentFragment = (BaseFragment) fragmentManager.findFragmentByTag(str);
            if (currentFragment != null) {
                currentFragment.updateToolbar();
            }
        } else {
            if (firstFragment != null && firstFragment.isVisible())
                firstFragment.updateToolbar();
        }
    }


    public void updateProgressBar() {
        handler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            MusicPlayerUtil.setupPlayIconState(ivPlayPause);

            MediaPlayerWrapper.State state = MediaPlayerWrapper.getInstance().getCurrentState();
            if (!(state.equals(MediaPlayerWrapper.State.Retrieving)
                    || state.equals(MediaPlayerWrapper.State.Stopped) || state.equals(MediaPlayerWrapper.State.Preparing))) {
                long totalDuration = MediaPlayerWrapper.getInstance().getPlayerTotalDuration();
                long currentDuration = MediaPlayerWrapper.getInstance().getPlayerCurrentPosition();
                tvTrackTotalDuration.setText(String.valueOf(SeekBarUtils.milliSecondsToTimer(totalDuration)));
                tvCurrentTrackPosition.setText(String.valueOf(SeekBarUtils.milliSecondsToTimer(currentDuration)));
                if (MediaPlayerWrapper.getInstance().getCurrentTrack().getmArtistName() != null) {
                    tvPlayTrackName.setText(MediaPlayerWrapper.getInstance().getCurrentTrack().getmArtistName() + " - " + MediaPlayerWrapper.getInstance().getCurrentTrack().getmTrackName());
                } else {
                    tvPlayTrackName.setText(MediaPlayerWrapper.getInstance().getCurrentTrack().getmTrackName());
                }

                int progress = SeekBarUtils.getProgressPercentage(currentDuration, totalDuration);
                sbSeekbar.setProgress(progress);
            }
            handler.postDelayed(this, 100);
        }
    };


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        if (fromTouch) {
            int totalDuration = MediaPlayerWrapper.getInstance().getPlayerTotalDuration();
            int currentPosition = SeekBarUtils.progressToTimer(seekBar.getProgress(), totalDuration);
            MediaPlayerWrapper.getInstance().seekPlayerTo(currentPosition);
            tvCurrentTrackPosition.setText(String.valueOf(SeekBarUtils.milliSecondsToTimer(currentPosition)));
        }
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(mUpdateTimeTask);
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        updateProgressBar();
    }

    private void setupPlayerWidget() {
        playlistAdapter = new AlbumPlaylistAdapter(this);
        rvPlaylist = (RecyclerView) findViewById(R.id.rv_playlist);
        rvPlaylist.setLayoutManager(new LinearLayoutManager(this));
        rvPlaylist.setAdapter(playlistAdapter);

        switch (MediaPlayerWrapper.getInstance().getCurrentState()) {
            case Paused:
                mSmPlayer.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                updateProgressBar();
                break;
            case Playing:
                mSmPlayer.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                updateProgressBar();
                break;
            default:
                mSmPlayer.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                break;
        }

        sbSeekbar.setOnSeekBarChangeListener(this);
        updateProgressBar();
        ivPlayPause.setOnClickListener(view -> {

            if (MediaPlayerWrapper.getInstance().getCurrentTrack() != null) {
                MediaPlayerWrapper.getInstance().playTrack(MediaPlayerWrapper.getInstance().getCurrentTrack(), false);
                MusicPlayerUtil.setupPlayIconState(ivPlayPause);
            }
        });
    }

    private void notifyServiceTrackStateChanged() {
        Intent intent = new Intent(getApplicationContext(), TracksPlayerService.class);
        intent.setAction(TracksPlayerService.ACTION_NOTIFICATION_PLAY_PAUSE);
        intent.putExtra(TracksPlayerService.INTENT_PLAYER_KEY, TracksPlayerService.FLAG_FROM_WIDGET);
        startService(intent);
    }

    @Subscribe
    public void trackStartEvent(TrackPlayerEntity event) {
        if (!event.isFromNotification())
            notifyServiceTrackStateChanged();

        if (mSmPlayer.getPanelState().equals(SlidingUpPanelLayout.PanelState.HIDDEN))
            mSmPlayer.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

        if (!MediaPlayerWrapper.getInstance().isFromAlbum()) playlistAdapter.setData(null);

        if (event.getmArtistName() != null) {
            tvPlayTrackName.setText(event.getmArtistName() + " - " + event.getmTrackName());
        } else {
            tvPlayTrackName.setText(event.getmTrackName());
            ivAlbumsTrackImage.setImageResource(android.R.color.transparent);
        }

        if (!MediaPlayerWrapper.getInstance().isFromAlbum() || event.getmAlbumImageUrl() != null) {
            albumImage = event.getmAlbumImageUrl();
            Glide.with(this).load(albumImage).into(ivAlbumsTrackImage);
        }
        updateProgressBar();
    }

    @Subscribe
    public void trackStartPauseEvent(EventCurrentTrackPause eventCurrentTrackPause) {
        if (!eventCurrentTrackPause.getTrack().isFromNotification())
            notifyServiceTrackStateChanged();
        if (mSmPlayer.getPanelState().equals(SlidingUpPanelLayout.PanelState.HIDDEN))
            mSmPlayer.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        if (!MediaPlayerWrapper.getInstance().isFromAlbum()) {
            playlistAdapter.setData(null);
        }
    }

    @Subscribe
    public void playlistStartEvent(EventPlaylistStart event) {
        albumImage = event.getAlbumImageUrl();
        Glide.with(this).load(albumImage)/*.asBitmap().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Bitmap bitmapBlur = BlurEffect.fastblur(MainActivity.this, resource, 12);
                ivAlbumsTrackImage.setImageBitmap(bitmapBlur);
                return true;
            }
        })*/.into(ivAlbumsTrackImage);
        List<TrackPlayerEntity> trackPlayerEntities = Observable.from(event.getData()).map(trackEntity -> {
            TrackPlayerEntity trackPlayerEntity = new TrackPlayerEntity();
            trackPlayerEntity.setmTrackName(trackEntity.getName());
            trackPlayerEntity.setmArtistName(trackEntity.getArtistName());
            return trackPlayerEntity;
        }).toList().toBlocking().first();
        MediaPlayerWrapper.getInstance().setFromAlbum(true);
        playlistAdapter.setData(trackPlayerEntities);
        mSmPlayer.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        EventBus.getDefault().post(new EventSynchronizingAdapter());
    }

    @Subscribe
    public void playlistStartSavedTrackEvent(EventPlayAllSavedTracks event) {

        List<TrackPlayerEntity> trackPlayerEntities = Observable.from(event.getTracks()).map(trackEntity -> {
            TrackPlayerEntity trackPlayerEntity = new TrackPlayerEntity();
            trackPlayerEntity.setmTrackName(trackEntity.getName());
            trackPlayerEntity.setmTrackUrl(trackEntity.getPath());
            return trackPlayerEntity;
        }).toList().toBlocking().first();
        MediaPlayerWrapper.getInstance().setFromAlbum(true);
        ivAlbumsTrackImage.setImageResource(android.R.color.transparent);
        playlistAdapter.setData(trackPlayerEntities);
        mSmPlayer.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        EventBus.getDefault().post(new EventSynchronizingAdapter());
    }

    @Override
    public void onPlaylistTrackClick(TrackPlayerEntity trackPlayerEntity) {
        MediaPlayerWrapper.getInstance().playTrack(trackPlayerEntity, false);
    }

    @OnClick(R.id.iv_save_track)
    public void onSaveCurrentTrackClick() {
        EventBus.getDefault().post(new EventDownloadCurrentTrack());
    }
}
