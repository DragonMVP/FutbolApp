package edu.unitec.futbolapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

/**
 * Created by carlo on 10/6/2015.
 */
public class signDialog extends Activity{
    List<Usuario> usuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDatabaseHandler db = new MyDatabaseHandler(getBaseContext());

        usuarios = db.getAllUsers();
        db.close();

    }
    public void SigninClick(View v){

    }
}
