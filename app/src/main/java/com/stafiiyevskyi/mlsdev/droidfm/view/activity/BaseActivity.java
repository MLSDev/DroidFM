package com.stafiiyevskyi.mlsdev.droidfm.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.stafiiyevskyi.mlsdev.droidfm.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 20.04.16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        if (toolbar!=null){
            setSupportActionBar(toolbar);
        }

    }

    public abstract int getContentViewId();
}
