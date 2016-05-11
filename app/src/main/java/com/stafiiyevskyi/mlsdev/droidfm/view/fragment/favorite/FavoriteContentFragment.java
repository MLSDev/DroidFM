package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.BaseActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 10.05.16.
 */
public class FavoriteContentFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @Bind(R.id.vp_content)
    ViewPager mVpTabContent;
    @Bind(R.id.tabs)
    TabLayout mTlTabs;

    private FragmentViewPagerAdapter mAdapter;

    public static BaseFragment newInstance() {
        BaseFragment fragment = new FavoriteContentFragment();
        return fragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void setupViewPager() {
        mAdapter = new FragmentViewPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(FavoriteAlbumsFragment.newInstance(), getString(R.string.tab_title_top_albums));
        mAdapter.addFragment(FavoriteTracksFragment.newInstance(), getString(R.string.tab_title_top_tracks));
        mAdapter.addFragment(FavoriteArtistsFragment.newInstance(), getString(R.string.tab_title_top_artists));
        mVpTabContent.setAdapter(mAdapter);
        mVpTabContent.addOnPageChangeListener(this);
        mTlTabs.setupWithViewPager(mVpTabContent);
    }


    @Override
    protected int getResourceId() {
        return R.layout.fragment_favorite_content;
    }

    @Override
    public void updateToolbar() {
        ((BaseActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.favorite_section_title);
        getActivity().supportInvalidateOptionsMenu();
        ((Navigator) getActivity()).setDrawerToggleEnabled();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mAdapter.getItem(position).updateToolbar();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<BaseFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public FragmentViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public BaseFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(BaseFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
