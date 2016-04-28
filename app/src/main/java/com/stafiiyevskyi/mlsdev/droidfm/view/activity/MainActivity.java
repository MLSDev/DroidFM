package com.stafiiyevskyi.mlsdev.droidfm.view.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatImageView;
import android.view.MenuItem;

import com.stafiiyevskyi.mlsdev.droidfm.JUnitTestHelper;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.AlbumsDetailsFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.ArtistContentDetailsFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.ArtistDetailFullFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.TopChartsContentFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.TrackDetailFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart.ArtistSearchListFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart.ChartTopTracksFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.tag.TagTopContentFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;
import com.stafiiyevskyi.mlsdev.droidfm.view.widget.MenuArrowDrawable;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements Navigator {
    @Bind(R.id.drawer_layout)
    DrawerLayout drNavigation;
    @Bind(R.id.nav_view)
    NavigationView nvNavigation;

    private FragmentManager mFragmentManager;


    private ActionBarDrawerToggle mDrawerToggle;
    private MenuArrowDrawable mDrawerArrowDrawable;
    private BaseFragment mFirstFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        setupNavigation();
        getSupportActionBar().setSubtitle(getString(R.string.artists_section_title));
        if (!JUnitTestHelper.getInstance().isJunitRunning())
            navigateToArtistsSearchScreen();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            onBackPressed();
            return super.onOptionsItemSelected(item);
        }
    }

    private void setupNavigation() {
        mDrawerArrowDrawable = new MenuArrowDrawable(new ContextThemeWrapper(this, R.style.AppTheme_AppBarOverlay), getSupportActionBar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, drNavigation,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        nvNavigation.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_tracks_item:
                    if (!(mFirstFragment instanceof ChartTopTracksFragment)) {
                        navigateToTopTracksScreen();
                        getSupportActionBar().setSubtitle(getString(R.string.tracks_section_title));
                    }
                    drNavigation.closeDrawers();
                    return true;
                case R.id.action_artists_item:
                    if (!(mFirstFragment instanceof ArtistSearchListFragment)) {
                        navigateToArtistsSearchScreen();
                        getSupportActionBar().setSubtitle(getString(R.string.artists_section_title));
                    }

                    drNavigation.closeDrawers();
                    return true;
                case R.id.action_charts_item:
                    if (!(mFirstFragment instanceof TopChartsContentFragment)) {
                        navigateToChartsContentScreen();
                        getSupportActionBar().setSubtitle(getString(R.string.charts_section_title));
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
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drNavigation.addDrawerListener(mDrawerToggle);
    }

    @Override
    public void setDrawerToggleNotEnabled() {
        menuToBack();
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        drNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void menuToBack() {
        this.mDrawerArrowDrawable.animateDrawable(true);
    }

    public void backToMenu() {
        this.mDrawerArrowDrawable.animateDrawable(false);
    }

    @Override
    public void navigateToArtistsSearchScreen() {
        mFirstFragment = ArtistSearchListFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mFirstFragment)
                .commit();
    }

    @Override
    public void navigateToChartsContentScreen() {
        mFirstFragment = TopChartsContentFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mFirstFragment)
                .commit();
    }

    @Override
    public void navigateToArtistContentDetailsScreen(String mbid, String artistName, String imageUrl, AppCompatImageView imageView) {
        BaseFragment fragment = ArtistContentDetailsFragment.newInstance(mbid, artistName, imageUrl);
        AnimationUtil.detailTransition(fragment, imageView, getString(R.string.transition_artist_image));
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addSharedElement(imageView, getString(R.string.transition_artist_image))
                .addToBackStack(ArtistContentDetailsFragment.class.getName() + mbid)
                .commit();
    }

    @Override
    public void navigateToTopTracksScreen() {
        mFirstFragment = ChartTopTracksFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mFirstFragment)
                .commit();
    }

    @Override
    public void navigateToArtistFullDetailsScreen(String mbid) {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ArtistDetailFullFragment.newInstance(mbid))
                .addToBackStack(ArtistDetailFullFragment.class.getName() + mbid)
                .commit();
    }

    @Override
    public void navigateToTagTopContent(String tag) {
        getSupportActionBar().setSubtitle(tag);
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, TagTopContentFragment.newInstance(tag))
                .addToBackStack(TagTopContentFragment.class.getName() + tag)
                .commit();
    }

    @Override
    public void navigateToAlbumDetails(String artist, String album, String mbid) {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, AlbumsDetailsFragment.newInstance(artist, album, mbid))
                .addToBackStack(AlbumsDetailsFragment.class.getName() + mbid)
                .commit();
    }

    @Override
    public void navigateToTrackDetails(String artist, String track, String mbid) {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, TrackDetailFragment.newInstance(artist, track, mbid))
                .addToBackStack(TrackDetailFragment.class.getName() + mbid)
                .commit();
    }

    @Override
    public void navigateBack() {
        int i = mFragmentManager.getBackStackEntryCount();
        if (i >= 1) {
            FragmentManager.BackStackEntry backEntry = mFragmentManager
                    .getBackStackEntryAt(mFragmentManager.getBackStackEntryCount() - 1);
            String str = backEntry.getName();
            BaseFragment currentFragment = (BaseFragment) mFragmentManager.findFragmentByTag(str);
            if (currentFragment != null) {
                currentFragment.updateToolbar();
            }
        } else {
            mFirstFragment.updateToolbar();
        }
    }
}
