package com.stafiiyevskyi.mlsdev.droidfm.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.NestedScrollableViewHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 20.04.16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @Bind(R.id.sm_player)
    SlidingUpPanelLayout mSmPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (mSmPlayer != null) mSmPlayer.setScrollableViewHelper(new NestedScrollableViewHelper());

    }

    @Override
    public void onBackPressed() {
        if (mSmPlayer != null && mSmPlayer.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)) {
            mSmPlayer.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
            navigateBack();
        }

    }

    public abstract int getContentViewId();

    public abstract void navigateBack();
}
