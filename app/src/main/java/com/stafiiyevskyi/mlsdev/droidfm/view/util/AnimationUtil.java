package com.stafiiyevskyi.mlsdev.droidfm.view.util;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.view.View;

import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.transition.DetailsTransition;

/**
 * Created by oleksandr on 26.04.16.
 */
public final class AnimationUtil {

    public static void detailTransition(BaseFragment fragment, View sharedView, String transitionName) {
        ViewCompat.setTransitionName(sharedView, transitionName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());
        }
    }
}
