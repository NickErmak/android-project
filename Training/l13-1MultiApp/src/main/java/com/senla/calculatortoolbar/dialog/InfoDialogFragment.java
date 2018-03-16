package com.senla.calculatortoolbar.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.senla.calculatortoolbar.R;

public class InfoDialogFragment extends DialogFragment {

    private static final String KEY_TITLE = "KEY_TITLE";
    private static final String KEY_MESSAGE = "KEY_MESSAGE";
    private String title;
    private String message;

    public static InfoDialogFragment newInstance(String title, String message) {
        InfoDialogFragment result = new InfoDialogFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        result.setArguments(args);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(KEY_TITLE);
        message = getArguments().getString(KEY_MESSAGE);
    }

    public InfoDialogFragment() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_positive_btn, null)
                .create();
    }
}
