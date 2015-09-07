package edu.unitec.futbolapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by daemonaeon on 09/05/2015.
 */
public class JugadorActivity extends Activity {

    List<Jugador> Jugadores;
    ListView lista;
    int IDEQUIPO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);
        MyDatabaseHandler db = new MyDatabaseHandler(getBaseContext());
        IDEQUIPO = getIntent().getIntExtra("EQUIPO", 0);
        Jugadores = db.getAllPlayers(IDEQUIPO);
        db.close();

        lista = (ListView)findViewById(R.id.listJugador);
        lista.setAdapter(new MyListViewAdapter(Jugadores, getBaseContext(), this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.club_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.addClub){
            addJugadorDialog addJugador = new addJugadorDialog(Jugadores,(MyListViewAdapter)lista.getAdapter(),IDEQUIPO,this);
            addJugador.show(getFragmentManager(),"addJugadorDialog");
            return true;
        }else {
            System.out.println("-----------------------------------------"+id);
            return super.onOptionsItemSelected(item);
        }
    }
}
