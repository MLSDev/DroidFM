package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.tag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;
import com.stafiiyevskyi.mlsdev.droidfm.view.activity.MainActivity;
import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by oleksandr on 26.04.16.
 */
public class TagTopContentFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private static final String TAG_BUNDLE_KEY = "tag_bundle_key_tag_top_content_fragment";

    @Bind(R.id.vp_content)
    ViewPager vpTabContent;
    @Bind(R.id.tabs)
    TabLayout tlTabs;

    private String tag;

    private FragmentViewPagerAdapter adapter;

    public static BaseFragment newInstance(@NonNull String tag) {
        Bundle args = new Bundle();
        args.putString(TAG_BUNDLE_KEY, tag);
        BaseFragment fragment = new TagTopContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        tag = args.getString(TAG_BUNDLE_KEY);
        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(tag);
        setupViewPager();
        ((Navigator) getActivity()).setDrawerToggleNotEnabled();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void setupViewPager() {
        adapter = new FragmentViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(TagTopAlbumsFragment.newInstance(tag), getActivity().getString(R.string.tab_title_top_albums));
        adapter.addFragment(TagTopArtistsFragment.newInstance(tag), getActivity().getString(R.string.tab_title_top_artists));
        adapter.addFragment(TagTopTracksFragment.newInstance(tag), getActivity().getString(R.string.tab_title_top_tracks));
        vpTabContent.setAdapter(adapter);
        vpTabContent.addOnPageChangeListener(this);
        tlTabs.setupWithViewPager(vpTabContent);
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_tag_top_content;
    }

    @Override
    public void updateToolbar() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        adapter.getItem(position).updateToolbar();
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
