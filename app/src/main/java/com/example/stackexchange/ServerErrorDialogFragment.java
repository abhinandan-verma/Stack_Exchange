package com.example.stackexchange;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ServerErrorDialogFragment extends DialogFragment {
    public static ServerErrorDialogFragment newInstance(){
        return new ServerErrorDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(R.string.server_error_dialog_title);

        alertDialogBuilder.setMessage(R.string.server_error_dialog_message);

        alertDialogBuilder.setPositiveButton(
                R.string.server_error_dialog_title_caption,
                (dialog, which) -> {
                    dismiss();
                }
        );
        return alertDialogBuilder.create();

    }
}
