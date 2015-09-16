package edu.unitec.futbolapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


public class JugadorStatsDialog extends DialogFragment {

    Jugador j;
    PartidoMemoria pm;
    TextView pasesCortos;
    TextView pasesMedios;
    TextView pasesLargos;
    TextView faltas;

    public JugadorStatsDialog(Jugador j, PartidoMemoria pm) {
        this.j = j;
        this.pm = pm;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_jugador_stats_dialog, null);
        pasesCortos = (TextView) view.findViewById(R.id.textViewPasesCortos);
        pasesLargos = (TextView) view.findViewById(R.id.textViewPasesLargos);
        pasesMedios = (TextView) view.findViewById(R.id.textViewPasesMedios);
        faltas = (TextView) view.findViewById(R.id.textViewFaltas);

        pasesCortos.setText(pm.getCountJugadorPasesCortos(j)+"");
        pasesMedios.setText(pm.getCountJugadorPasesMedios(j)+"");
        pasesLargos.setText(pm.getCountJugadorPasesLargos(j)+"");
        faltas.setText(pm.getCountFaltasCometidasPartido(j)+"");

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Estadísticas");
        builder.setView(view);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                JugadorStatsDialog.this.getDialog().cancel();
            }
        });

        builder.setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing here because we override this button later to change the close behaviour.
                        //However, we still need this because on older versions of Android unless we
                        //pass a handler the button doesn't get instantiated
                    }
                });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
    }
}
