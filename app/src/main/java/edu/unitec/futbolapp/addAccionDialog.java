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
import java.util.List;

/**
 * Created by nivx1 on 09/03/2015.
 */
public class addAccionDialog extends DialogFragment {
    List<Accion> ACCION;
    MyListViewAdapter Adapter;

    EditText nameAccion;
    Spinner tipoAccion;
    Activity Actividad;

    public addAccionDialog(List<Accion> ACCION, MyListViewAdapter Adapter, Activity A) {
        this.ACCION = ACCION;
        this.Adapter = Adapter;
        this.Actividad = A;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.addaction_dialog, null);
        nameAccion = (EditText) view.findViewById(R.id.txtnameAccion);
        tipoAccion = (Spinner) view.findViewById(R.id.spTipoAccion);

        List<String> setTypes = new ArrayList();
        setTypes.add("Accion");
        setTypes.add("Falta");
        setTypes.add("Pase");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Actividad, android.R.layout.simple_spinner_item, setTypes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoAccion.setAdapter(dataAdapter);


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Agregar Accion");
        builder.setView(view);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                addAccionDialog.this.getDialog().cancel();
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
                    if (nameAccion.getText().toString().isEmpty())
                        nameAccion.requestFocus();
                    else{
                        close = true;
                        Accion tmp = null;
                        String Type = tipoAccion.getSelectedItem().toString();
                        if (Type.equals("Accion"))
                            tmp= new Accion(-1,nameAccion.getText().toString());
                        else if (Type.equals("Pase"))
                            tmp = new Pase(-1,nameAccion.getText().toString());
                        else if (Type.equals("Falta"))
                            tmp = new Falta(-1,nameAccion.getText().toString());

                        MyDatabaseHandler db = new MyDatabaseHandler(v.getContext());
                        db.addAccion(tmp);

                        ACCION = (db.getUserAccions());
                        db.close();
                        Adapter.notifyDataSetChanged();

                    }

                    if (close)
                        dismiss();

                }
            });
        }
    }
}
