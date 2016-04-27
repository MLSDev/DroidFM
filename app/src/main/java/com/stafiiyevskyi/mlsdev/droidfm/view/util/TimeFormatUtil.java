package com.stafiiyevskyi.mlsdev.droidfm.view.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by oleksandr on 27.04.16.
 */
public final class TimeFormatUtil {

    private TimeFormatUtil() {
    }

    public static String getFormattedTimeMillisToMinutes(int milliseconds) {
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds))
        );
    }
}
