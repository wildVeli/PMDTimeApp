package com.example.a2dam.aplicacionproyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.widget.Chronometer;

/**
 * Created by casa on 08/11/2017.
 */

public class DialogFragmentCancelSesion extends DialogFragment {

    answerDialogFragmentCancelSesion answer;


    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.black);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.attention);
        builder.setMessage(R.string.cancelChrono);
        builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                answer.onAnswer("si");

            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                answer.onAnswer("no");

            }
        });

        return builder.create();
    }
    public interface answerDialogFragmentCancelSesion{
        public void onAnswer (String s);
    }

    public void  onAttach(Activity activity){
        super.onAttach(activity);
        answer=(answerDialogFragmentCancelSesion)activity;
    }
}
