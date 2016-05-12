package com.stafiiyevskyi.mlsdev.droidfm.view.util;

import android.support.v7.widget.AppCompatImageView;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.app.player.MediaPlayerWrapper;

/**
 * Created by oleksandr on 10.05.16.
 */
public final class MusicPlayerUtil {

    public static void setupPlayIconState(AppCompatImageView iconView) {
        switch (MediaPlayerWrapper.getInstance().getCurrentState()) {
            case Retrieving:
                iconView.setImageResource(R.drawable.ic_play_grey600_36dp);
                break;
            case Stopped:
                iconView.setImageResource(R.drawable.ic_play_grey600_36dp);
                break;
            case Preparing:
                iconView.setImageResource(R.drawable.ic_pause_grey600_36dp);
                break;
            case Playing:
                iconView.setImageResource(R.drawable.ic_pause_grey600_36dp);
                break;
            case Paused:
                iconView.setImageResource(R.drawable.ic_play_grey600_36dp);
                break;
        }
    }
}

