package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.ArtistContentDetailsScreenPresenter;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.FavoriteArtistEntity;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.impl.ArtistContentDetailsScreenPresenterImp;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.view.ArtistContentScreenView;
import com.stafiiyevskyi.mlsdev.droidfm.view.Navigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by oleksandr on 21.04.16.
 */
public class ArtistContentDetailsFragment extends BaseFragment implements ViewPager.OnPageChangeListener, ArtistContentScreenView {

    private static final String ARTIST_MBID_BUNDLE_KEY = "artist_conten_details_fragment_mbid";
    private static final String ARTIST_NAME_BUNDLE_KEY = "artist_conten_details_fragment_name";
    private static final String ARTIST_IMAGE_URL_BUNDLE_KEY = "artist_conten_details_fragment_image_url";
    @Bind(R.id.iv_artist)
    CircleImageView mIvArtistPhoto;
    @Bind(R.id.vp_content)
    ViewPager mVpTabContent;
    @Bind(R.id.tabs)
    TabLayout mTlTabs;
    @Bind(R.id.tv_artist_name)
    AppCompatTextView mTvArtistName;
    @Bind(R.id.iv_add_to_favorite)
    AppCompatImageView mIvAddToFavorite;

    private ArtistContentDetailsScreenPresenter mPresenter;

    private String mMbid;
    private String mArtistName;
    private String mImageUrl;
    FavoriteArtistEntity artistEntity;

    private FragmentViewPagerAdapter mAdapter;
    private boolean mIsFavorite = false;

    public static BaseFragment newInstance(@NonNull String artistMbid, @NonNull String artistName, @NonNull String imageUrl) {

        Bundle args = new Bundle();
        args.putString(ARTIST_MBID_BUNDLE_KEY, artistMbid);
        args.putString(ARTIST_NAME_BUNDLE_KEY, artistName);
        args.putString(ARTIST_IMAGE_URL_BUNDLE_KEY, imageUrl);
        BaseFragment fragment = new ArtistContentDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ArtistContentDetailsScreenPresenterImp(this);
        Bundle args = getArguments();
        mMbid = args.getString(ARTIST_MBID_BUNDLE_KEY);
        mArtistName = args.getString(ARTIST_NAME_BUNDLE_KEY);
        mImageUrl = args.getString(ARTIST_IMAGE_URL_BUNDLE_KEY);
        setupGeneralInfo();
        setupViewPager(mVpTabContent);
        mTlTabs.setupWithViewPager(mVpTabContent);
        ((Navigator) getActivity()).setDrawerToggleNotEnabled();
        artistEntity = new FavoriteArtistEntity();
        artistEntity.setImage(mImageUrl);
        artistEntity.setName(mArtistName);
        artistEntity.setMbid(mMbid);
        mPresenter.isArtistFavorite(artistEntity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setupGeneralInfo() {
        Glide.with(this).load(mImageUrl).into(mIvArtistPhoto);
        mTvArtistName.setText(mArtistName);
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new FragmentViewPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(ArtistTopAlbumsFragment.newInstance(mMbid, mArtistName), getActivity().getString(R.string.tab_title_top_albums));
        mAdapter.addFragment(ArtistTopTracksFragment.newInstance(mMbid, mArtistName), getActivity().getString(R.string.tab_title_top_tracks));
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @OnClick(R.id.tv_artist_name)
    public void OnDetailsClick() {
        ((Navigator) getActivity()).navigateToArtistFullDetailsScreen(mMbid);
    }


    @Override
    protected int getResourceId() {
        return R.layout.fragment_artist_content_details;
    }

    @Override
    public void updateToolbar() {

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

    @Override
    public void showArtistIsFavorite(boolean isFavorite) {
        this.mIsFavorite = isFavorite;
        if (isFavorite) {
            mIvAddToFavorite.setImageResource(R.drawable.ic_star_white_48dp);
        } else mIvAddToFavorite.setImageResource(R.drawable.ic_star_outline_white_48dp);
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @OnClick(R.id.iv_add_to_favorite)
    public void onAddToFavoriteClick() {
        if (mIsFavorite) {
            mPresenter.deleteFromFavorite(artistEntity);
        } else {
            mPresenter.addArtistToFavorite(artistEntity);
        }
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
