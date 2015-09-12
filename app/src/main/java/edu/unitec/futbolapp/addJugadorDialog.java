package edu.unitec.futbolapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


/**
 * Created by daemonaeon on 09/05/2015.
 */
public class addJugadorDialog extends DialogFragment {

    List<Jugador> Jugadores;
    MyListViewAdapter Adapter;

    EditText nameJugador, numeroJugador,imgPath;
    Spinner Posicion;
    Button btnSeleccionar,btnTomar;
    Activity Actividad;
    int IDEQUIPO;
    String IMGPATH;

    public addJugadorDialog(List<Jugador> Jugadores,MyListViewAdapter Adapter, int IDEQUIPO,Activity Actividad) {
        this.Jugadores = Jugadores;
        this.Adapter = Adapter;
        this.IDEQUIPO=IDEQUIPO;
        this.Actividad = Actividad;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_add_jugador_dialog, null);
        nameJugador = (EditText) view.findViewById(R.id.txtnameJugador);
        numeroJugador = (EditText) view.findViewById(R.id.numeroJugador);
        Posicion = (Spinner)view.findViewById(R.id.spPosicion);
        btnSeleccionar = (Button)view.findViewById(R.id.btnSeleccionar);
        btnTomar = (Button)view.findViewById(R.id.btnTomar);
       // imgPath = (EditText)view.findViewById(R.id.imgPath);
        IMGPATH = "";
        String[] P={"Portero","Defensa","Medio","Delantero"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Actividad, android.R.layout.simple_spinner_item, P);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Posicion.setAdapter(dataAdapter);

        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        btnTomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 1);
            }
        });



        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Student");
        builder.setView(view);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                addJugadorDialog.this.getDialog().cancel();
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
                    if (nameJugador.getText().toString().isEmpty())
                        nameJugador.requestFocus();
                    else{
                        close = true;

                        int SelectedInt = Posicion.getSelectedItemPosition();
                        if (IMGPATH.equals(""))
                            IMGPATH="DEFAULT";
                        Jugador tmp = new Jugador(-1,IDEQUIPO,SelectedInt, nameJugador.getText().toString(),Integer.parseInt(numeroJugador.getText().toString()),IMGPATH);
                        MyDatabaseHandler db = new MyDatabaseHandler(v.getContext());
                        db.addJugador(tmp);

                        Jugadores = (db.getAllPlayers(IDEQUIPO));
                        db.close();
                        Adapter.notifyDataSetChanged();

                    }

                    if (close)
                        dismiss();

                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator;
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                        IMGPATH = (file.getAbsolutePath());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = Actividad.getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                IMGPATH = (picturePath);
            }

        }

    }
}
