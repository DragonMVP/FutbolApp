package edu.unitec.futbolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by daemon on 09/15/2015.
 */
public class JugadorStatsActivity extends AppCompatActivity {

    ArrayList<JugadorPartido> jugadores;
    ListView lista;
    PartidoMemoria pm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);
        jugadores = (ArrayList<JugadorPartido>)getIntent().getSerializableExtra("JUGADORES");
        pm = (PartidoMemoria)getIntent().getSerializableExtra("PARTIDOMEMORIA");

        lista = (ListView)findViewById(R.id.listJugador);
        lista.setAdapter(new MyListViewAdapter(jugadores, getBaseContext(), this));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Jugador j = (Jugador)parent.getAdapter().getItem(position);

                JugadorStatsDialog jsd = new JugadorStatsDialog(j, pm);
                jsd.show(getFragmentManager(),"JugadorStatsDialog");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jugador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
