package com.example.a2dam.aplicacionproyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Chronometer;

/**
 * Created by casa on 08/11/2017.
 */

public class DialogFragmentCancelSesion extends DialogFragment {

    answerDialogFragmentCancelSesion answer;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿Seguro?");
        builder.setMessage("Cancelar una sesión no guarda los tiempos ¿Está seguro?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                answer.onAnswer("si");

            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                answer.onAnswer("Es un chico");

            }
        });

        return builder.create();
    }
    public interface answerDialogFragmentCancelSesion{
        public void onAnswer (String s);
    }

    public void  onAttach(Activity activity){
        super.onAttach(activity);
//        answer=(answerDialogFragmentCancelSesion)activity;
    }
}
