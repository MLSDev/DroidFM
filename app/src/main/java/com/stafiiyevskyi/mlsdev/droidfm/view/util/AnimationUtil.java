package com.stafiiyevskyi.mlsdev.droidfm.view.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.stafiiyevskyi.mlsdev.droidfm.view.fragment.BaseFragment;
import com.stafiiyevskyi.mlsdev.droidfm.view.transition.DetailsTransition;

/**
 * Created by oleksandr on 26.04.16.
 */
public final class AnimationUtil {
    private static Point screenSize;

    public static void detailTransitionShared(BaseFragment fragment, View sharedView, String transitionName) {
        ViewCompat.setTransitionName(sharedView, transitionName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());
        }
    }

    public static void detailTransition(BaseFragment fragment) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
        }
    }


    public static Point getScreenSize(Context context) {
        if (screenSize != null) {
            return screenSize;
        }
        int pxWidth;
        int pxHeight;

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        pxWidth = display.getWidth();
        pxHeight = display.getHeight();


        screenSize = new Point(pxWidth, pxHeight);
        return screenSize;
    }


    public static int getScreenWidth(Context context) {
        return getScreenSize(context).x;
    }

    public static int getScreenHeight(Context context) {
        return getScreenSize(context).y;
    }

    public static int getProportionFullWidthHeight(int screenWidth, Bitmap bitmap, Context context){
        double width = bitmap.getWidth();
        double height = bitmap.getHeight();
        double proportion = screenWidth / width;
        double proportionHeight = height * proportion;
        return (int)proportionHeight;
    }
}
