package edu.unitec.futbolapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/**
 * Created by nivx1 on 09/03/2015.
 */
public class addClubDialog extends DialogFragment {
    List<Club> CLUBS;
    MyListViewAdapter Adapter;

    EditText nameClub;

    public addClubDialog(List<Club> CLUBS,MyListViewAdapter Adapter) {
        this.CLUBS = CLUBS;
        this.Adapter = Adapter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.addclub_dialog, null);
        nameClub = (EditText) view.findViewById(R.id.txtnameClub);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Club");
        builder.setView(view);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                addClubDialog.this.getDialog().cancel();
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
                    if (nameClub.getText().toString().isEmpty())
                        nameClub.requestFocus();
                    else{
                        close = true;
                        Club tmp = new Club(-1,nameClub.getText().toString());
                        MyDatabaseHandler db = new MyDatabaseHandler(v.getContext());
                        db.addClub(tmp);

                        CLUBS = (db.getAllClubs());
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
