package com.mojomoney.mojomoney;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


public class ResetPasswordDialogFragment extends DialogFragment {

    Context mContext;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mContext = getContext();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        SharedPreferences settings = mContext.getSharedPreferences("PREFS", 0);
        final SharedPreferences.Editor editor = settings.edit();

        builder.setMessage(R.string.dialog_reset_password)
                .setPositiveButton(R.string.reset_password, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editor.putString("password", "");
                        editor.apply();
                        Intent intent = new Intent(mContext, SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
