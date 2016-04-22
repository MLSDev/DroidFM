package com.stafiiyevskyi.mlsdev.droidfm.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;

/**
 * Created by oleksandr on 22.04.16.
 */
public class MenuArrowDrawable extends DrawerArrowDrawable {
    private final ValueAnimator mMenuToArrowAnimator;
    private final ValueAnimator mArrowToMenuAnimator;
    private ActionBar actionBar;

    public MenuArrowDrawable(Context context, final ActionBar actionBar) {
        super(context);
        this.actionBar = actionBar;

        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = animation -> {
            setPosition((float) animation.getAnimatedValue());
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(MenuArrowDrawable.this);
            }
        };

        mMenuToArrowAnimator = ValueAnimator.ofFloat(0f, 1f);
        mMenuToArrowAnimator.setDuration(250);
        mMenuToArrowAnimator.addUpdateListener(animatorUpdateListener);

        mArrowToMenuAnimator = ValueAnimator.ofFloat(1f, 0f);
        mArrowToMenuAnimator.setDuration(250);
        mArrowToMenuAnimator.addUpdateListener(animatorUpdateListener);
    }

    public void setPosition(float position) {
        if (position >= 1f) {
            setVerticalMirror(true);
        } else if (position <= 0f) {
            setVerticalMirror(false);
        }
        setProgress(position);
    }

    public float getPosition() {
        return getProgress();
    }

    public void animateDrawable(boolean menuToArrow) {
        if (menuToArrow && getPosition() >= 1f) return;
        if (!menuToArrow && getPosition() <= 0f) return;

        ValueAnimator animator = menuToArrow ? mMenuToArrowAnimator : mArrowToMenuAnimator;
        if (animator.isRunning()) animator.end();
        animator.start();
    }
}
