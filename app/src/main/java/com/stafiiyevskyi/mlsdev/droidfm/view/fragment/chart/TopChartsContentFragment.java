package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.stafiiyevskyi.mlsdev.droidfm.JUnitTestHelper;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.BaseActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;

import butterknife.Bind;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TopChartsContentFragment extends BaseFragment {
    @Bind(R.id.nb_navigation)
    AHBottomNavigation bnNavigation;

    private FragmentManager fragmentManager;
    private BaseFragment currentFragment;

    public static BaseFragment newInstance() {

        Bundle args = new Bundle();

        BaseFragment fragment = new TopChartsContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getChildFragmentManager();
        prepareBottomNavigationItems();
    }

    private void prepareBottomNavigationItems() {
        AHBottomNavigationItem itemTopArtist = new AHBottomNavigationItem("Artists", R.drawable.ic_person_black_48dp);
        AHBottomNavigationItem itemTopTags = new AHBottomNavigationItem("Tags", R.drawable.ic_bubble_chart_black_48dp);
        AHBottomNavigationItem itemTopTracks = new AHBottomNavigationItem("Tracks", R.drawable.ic_music_circle_black_48dp);
        bnNavigation.addItem(itemTopArtist);
        bnNavigation.addItem(itemTopTracks);
        bnNavigation.addItem(itemTopTags);
        bnNavigation.setAccentColor(getActivity().getResources().getColor(R.color.colorAccent));
        bnNavigation.setInactiveColor(getActivity().getResources().getColor(R.color.colorPrimary));
        bnNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            switch (position) {
                case 0:
                    if (!(currentFragment instanceof ArtistSearchListFragment))
                        selectTopArtist();
                    break;
                case 1:
                    if (!(currentFragment instanceof ChartTopTracksFragment))
                        selectTopTracks();
                    break;
                case 2:
                    if (!(currentFragment instanceof ChartTopTagsFragment))
                        selectTopTags();
                default:
                    break;
            }
        });
        if (!JUnitTestHelper.getInstance().isJunitRunning())
            selectTopArtist();
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_charts_content;
    }

    @Override
    public void updateToolbar() {
        ((BaseActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.charts_section_title);
        getActivity().supportInvalidateOptionsMenu();
        ((Navigator) getActivity()).setDrawerToggleEnabled();
    }

    private void selectTopArtist() {
        currentFragment = ArtistSearchListFragment.newInstance();
        AnimationUtil.detailTransition(currentFragment);
        fragmentManager.beginTransaction()
                .replace(R.id.fl_chart_content, currentFragment)
                .commit();
    }

    private void selectTopTracks() {
        currentFragment = ChartTopTracksFragment.newInstance();
        AnimationUtil.detailTransition(currentFragment);
        fragmentManager.beginTransaction()
                .replace(R.id.fl_chart_content, currentFragment)
                .commit();
    }

    private void selectTopTags() {
        currentFragment = ChartTopTagsFragment.newInstance();
        AnimationUtil.detailTransition(currentFragment);
        fragmentManager.beginTransaction()
                .replace(R.id.fl_chart_content, currentFragment)
                .commit();
    }
}
