package com.stafiiyevskyi.mlsdev.droidfm.view.fragment.signin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

/**
 * Created by oleksandr on 29.04.16.
 */
public class LoginVKDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String[] scope = new String[]{
            VKScope.AUDIO
    };

    public static DialogFragment newInstance() {
        return new LoginVKDialogFragment();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Login VK")
                .setPositiveButton("Login", this)
                .setMessage("Please login to VK");
        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                VKSdk.login(getActivity(), scope);
                dismiss();
                break;
            default:
                break;
        }
    }

}


