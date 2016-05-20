package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.stafiiyevskyi.mlsdev.droidfm.R;

import butterknife.Bind;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by oleksandr on 20.05.16.
 */
public class FullImageFragment extends BaseFragment {

    private static final String IMAGE_URL_BUNDLE = "image_url_bundle";

    @Bind(R.id.iv_image)
    ImageViewTouch ivPhoto;
    private String imageUrl;

    public static BaseFragment newInstance(String imageUrl) {
        Bundle args = new Bundle();
        args.putString(IMAGE_URL_BUNDLE, imageUrl);
        FullImageFragment fragment = new FullImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivPhoto.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        imageUrl = getArguments().getString(IMAGE_URL_BUNDLE);
        Glide.with(getActivity())
                .load(imageUrl)
                .into(ivPhoto);
    }


    @Override
    protected int getResourceId() {
        return R.layout.fragment_full_imageview;
    }

    @Override
    public void updateToolbar() {

    }
}