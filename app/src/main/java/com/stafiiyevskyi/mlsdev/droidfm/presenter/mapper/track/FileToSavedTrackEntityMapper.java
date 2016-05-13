package com.stafiiyevskyi.mlsdev.droidfm.presenter.mapper.track;

import android.net.Uri;

import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.SavedTrackEntity;

import java.io.File;

import rx.functions.Func1;

/**
 * Created by oleksandr on 13.05.16.
 */
public class FileToSavedTrackEntityMapper implements Func1<File, SavedTrackEntity> {
    @Override
    public SavedTrackEntity call(File file) {
        SavedTrackEntity track = new SavedTrackEntity();
        track.setName(file.getName());
        track.setPath(file.getPath());
        track.setUri(Uri.fromFile(file));
        return track;
    }
}
