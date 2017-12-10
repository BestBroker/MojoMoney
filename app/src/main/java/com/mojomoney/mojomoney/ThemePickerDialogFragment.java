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

public class ThemePickerDialogFragment extends DialogFragment {

    int mThemeToggle;
    Context mContext;
    NoticeDialogListener mListener;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mContext = getContext();
        final SharedPreferences settings = mContext.getSharedPreferences("theme_toggle", Context.MODE_PRIVATE);
        mThemeToggle = settings.getInt("theme_toggle", 0);

        final SharedPreferences.Editor editor = settings.edit();

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thema auswählen").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editor.apply();
                mListener.onDialogPositiveClick(ThemePickerDialogFragment.this);
                Intent intent = new Intent(mContext, MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogNegativeClick(ThemePickerDialogFragment.this);
            }
        });

        CharSequence[] options = {"Standard Thema", "Dunkles Thema"};
        builder.setSingleChoiceItems(options, mThemeToggle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editor.putInt("theme_toggle", i);
            }
        });

        return builder.create();
    }
}
