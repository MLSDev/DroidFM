package com.stafiiyevskyi.mlsdev.droidfm.view.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by oleksandr on 27.04.16.
 */
public final class TimeFormatUtil {

    private TimeFormatUtil() {
    }

    public static String getFormattedTimeSecondsToMinutes(int seconds) {
        return String.format("%d min, %d sec",
                TimeUnit.SECONDS.toMinutes(seconds),
                seconds -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds))
        );
    }
}
