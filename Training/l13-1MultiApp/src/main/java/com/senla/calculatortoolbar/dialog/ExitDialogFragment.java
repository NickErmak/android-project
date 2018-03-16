package com.senla.calculatortoolbar.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.senla.calculatortoolbar.R;

public class ExitDialogFragment extends DialogFragment {

    public interface OkHandler {
        void onOkPressed();
    }

    public static ExitDialogFragment newInstance() {
        return new ExitDialogFragment();
    }

    public ExitDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setMessage(R.string.dialog_exit_message)
                .setNegativeButton(R.string.dialog_negative_btn, null)
                .setPositiveButton(R.string.dialog_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((OkHandler) getParentFragment()).onOkPressed();
                    }
                })
                .create();
    }
}
