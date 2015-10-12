package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<Usuario> usuarios;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDatabaseHandler db = new MyDatabaseHandler(getBaseContext());
        final EditText usertext=(EditText) findViewById(R.id.txtboxUsername);
        final EditText paswordtext=(EditText) findViewById(R.id.txtboxPassword);

        usuarios = db.getAllUsers();
        db.close();

        try {
            Button btnLogin = (Button) findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i=0;i<usuarios.size();i++) {
                        if(usuarios.get(i).getUser().equalsIgnoreCase(usertext.getText().toString())&&usuarios.get(i).getPassword().equalsIgnoreCase(paswordtext.getText().toString())) {
                            Intent menuPrincipal = new Intent(v.getContext(), MenuPrincipalActivity.class);
                            startActivity(menuPrincipal);

                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
