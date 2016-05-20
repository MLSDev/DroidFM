package com.stafiiyevskyi.mlsdev.droidfm.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


/**
 * Created by oleksandr on 20.05.16.
 */
public class LyricsDialogFragment extends DialogFragment {

    private static final String LYRICS_TEXT_BUNDLE_KEY = "lyrics_text_bundle_key_lyrics_dialog_fragment";

    public static DialogFragment newInstance(String lyrics) {
        Bundle args = new Bundle();
        args.putString(LYRICS_TEXT_BUNDLE_KEY, lyrics);
        LyricsDialogFragment fragment = new LyricsDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Lyrics")
                .setMessage(getArguments().getString(LYRICS_TEXT_BUNDLE_KEY));
        return adb.create();
    }
}
