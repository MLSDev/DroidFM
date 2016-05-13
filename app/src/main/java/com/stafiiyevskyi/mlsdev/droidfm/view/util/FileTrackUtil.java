package com.stafiiyevskyi.mlsdev.droidfm.view.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Pair;

import java.io.File;

/**
 * Created by oleksandr on 13.05.16.
 */
public final class FileTrackUtil {

    public static Pair<Boolean, Uri> isTrackExist(Context context, String artist, String track) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), artist + " - " + track + ".mp3");
        return new Pair<>(file.exists(), Uri.fromFile(file));
    }


}
