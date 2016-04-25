package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart.ArtistSearchListFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart.ChartTopTagsFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.chart.ChartTopTracksFragment;

import butterknife.Bind;

/**
 * Created by oleksandr on 22.04.16.
 */
public class TopChartsContentFragment extends BaseFragment {
    @Bind(R.id.nb_navigation)
    AHBottomNavigation mBnNavigation;

    private FragmentManager mFragmentManager;

    public static BaseFragment newInstance() {

        Bundle args = new Bundle();

        BaseFragment fragment = new TopChartsContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentManager = getChildFragmentManager();
        prepareBottomNavigationItems();
    }

    private void prepareBottomNavigationItems() {
        AHBottomNavigationItem itemTopArtist = new AHBottomNavigationItem("Artists", R.mipmap.ic_launcher);
        AHBottomNavigationItem itemTopTags = new AHBottomNavigationItem("Tags", R.mipmap.ic_launcher);
        AHBottomNavigationItem itemTopTracks = new AHBottomNavigationItem("Tracks", R.mipmap.ic_launcher);
        mBnNavigation.addItem(itemTopArtist);
        mBnNavigation.addItem(itemTopTracks);
        mBnNavigation.addItem(itemTopTags);
        mBnNavigation.setAccentColor(getActivity().getResources().getColor(R.color.colorAccent));
        mBnNavigation.setInactiveColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mBnNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            switch (position) {
                case 0:
                    selectTopArtist();
                    break;
                case 1:
                    selectTopTracks();
                    break;
                case 2:
                    selectTopTags();
                default:
                    break;
            }
        });
        selectTopArtist();
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_charts_content;
    }

    @Override
    public void updateToolbar() {
        getActivity().supportInvalidateOptionsMenu();
        ((Navigator) getActivity()).setDrawerToggleEnabled();
    }

    private void selectTopArtist() {
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_chart_content, ArtistSearchListFragment.newInstance())
                .commit();
    }

    private void selectTopTracks() {
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_chart_content, ChartTopTracksFragment.newInstance())
                .commit();
    }

    private void selectTopTags() {
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_chart_content, ChartTopTagsFragment.newInstance())
                .commit();
    }
}
