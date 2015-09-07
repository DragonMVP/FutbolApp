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

import java.util.List;

/**
 * Created by daemonaeon on 09/05/2015.
 */
public class addEquipoDialog extends DialogFragment {
    List<Equipo> Equipos;
    MyListViewAdapter Adapter;

    EditText nameEquipo;
    int IDCLUB;

    public addEquipoDialog(List<Equipo> Equipos,MyListViewAdapter Adapter, int IDCLUB) {
        this.Equipos = Equipos;
        this.Adapter = Adapter;
        this.IDCLUB=IDCLUB;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_add_equipo_dialog, null);
        nameEquipo = (EditText) view.findViewById(R.id.txtnameEquipo);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Student");
        builder.setView(view);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                addEquipoDialog.this.getDialog().cancel();
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
        AlertDialog dialog = (AlertDialog) getDialog();

        if (dialog != null) {
            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);

            assert positiveButton != null;
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean close = false;
                    if (nameEquipo.getText().toString().isEmpty())
                        nameEquipo.requestFocus();
                    else{
                        close = true;
                        Equipo tmp = new Equipo(-1,nameEquipo.getText().toString(),IDCLUB);
                        MyDatabaseHandler db = new MyDatabaseHandler(v.getContext());
                        db.addEquipo(tmp);

                        (Adapter).setNewLista(db.getAllEquipos(IDCLUB));
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
