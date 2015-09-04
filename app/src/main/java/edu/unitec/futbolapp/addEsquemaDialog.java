package edu.unitec.futbolapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nivx1 on 09/03/2015.
 */
public class addEsquemaDialog extends DialogFragment {
    List<Esquema> ESQUEMAS;
    MyListViewAdapter Adapter;

    Spinner numOfensivo;
    Spinner numDefensivo;
    Spinner numMedio;

    EditText nameClub;
    Activity Actividad;

    public addEsquemaDialog(List<Esquema> ESQUEMAS, MyListViewAdapter Adapter,Activity A) {
        this.ESQUEMAS = ESQUEMAS;
        this.Adapter = Adapter;
        Actividad = A;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.addesquema_dialog, null);

        numOfensivo = (Spinner)view.findViewById(R.id.numOfensiva);
        numDefensivo = (Spinner)view.findViewById(R.id.numDefensiva);
        numMedio = (Spinner)view.findViewById(R.id.numMedio);


        List setNums = new ArrayList();
        for (int i = 1 ; i <6;i++)
            setNums.add(i);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Actividad, android.R.layout.simple_spinner_item, setNums);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        numOfensivo.setAdapter(dataAdapter);
        numDefensivo.setAdapter(dataAdapter);
        numMedio.setAdapter(dataAdapter);

        //nameClub = (EditText) view.findViewById(R.id.txtnameClub);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Agregar Esquema");
        builder.setView(view);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                addEsquemaDialog.this.getDialog().cancel();
            }
        });

        builder.setPositiveButton("Add",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
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
        AlertDialog dialog = (AlertDialog) getDialog();

        if (dialog != null) {
            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);

            assert positiveButton != null;
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean close = false;
                    int Of = Integer.parseInt(numOfensivo.getSelectedItem().toString());
                    int Me = Integer.parseInt(numMedio.getSelectedItem().toString());
                    int Def = Integer.parseInt(numDefensivo.getSelectedItem().toString());

                    if ((Of+Me+Def) !=10){
                        AlertDialog.Builder alert = new AlertDialog.Builder(Actividad);
                        alert.setTitle("Error");
                        alert.setMessage("Datos errones, no cumple con los 11 jugadores");
                        alert.setPositiveButton("OK",null);
                        alert.show();
                    }else{
                        Esquema newEsquema = new Esquema(Of,Me,Def);
                        MyDatabaseHandler db = new MyDatabaseHandler(v.getContext());
                        db.addEsquema(newEsquema);
                        db.close();
                        ESQUEMAS.add(newEsquema);
                        Adapter.notifyDataSetChanged();

                        close = true;
                    }

                    if (close)
                        dismiss();

                }
            });
        }
    }
}
