package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nivx1 on 09/05/2015.
 */
public class PartidoConfigActivity extends Activity {
    private Equipo SELECTED_TEAM;
    private Club SELECTED_CLUB;
    private Esquema SELECTED_ESQUEMA;
    private List<Jugador> INICIALES;

    Spinner spClubPartido;
    Spinner spEquipoPartido;
    Spinner spEsquema;
    Activity ME = this;
    ListView listJugadores;

    List<Equipo> tmpEquipo = new ArrayList<>();
    List<Jugador> tmpJugador= new ArrayList<>();
    MyDatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido_config);

        listJugadores = (ListView) findViewById(R.id.listJugadores_Partido);
        spEsquema=(Spinner)findViewById(R.id.spEsquema);
        spClubPartido = (Spinner)findViewById(R.id.spClub_Partido);
        spEquipoPartido = (Spinner)findViewById(R.id.spEquipo_Partido);

        //tmpEquipo.add(new Equipo(1,"Equipo Test",1));

/*
        for (int i=1;i<23;i++){
            PosicionCampo tmp;
            if (i <= 5)
                tmp = new PosicionCampo(i,"Delantero");
            else if (i<=10)
                tmp = new PosicionCampo(i,"Medio");
            else if  (i<=15)
                tmp = new PosicionCampo(i,"Defensa");
            else
                tmp = new PosicionCampo(i,"Portero");

            tmpJugador.add(new Jugador(i,1,tmp,"Jugador "+i,i));
        }
        */

        db = new MyDatabaseHandler(getBaseContext());


        ArrayAdapter<Esquema> EsquemaAdapter = new ArrayAdapter<Esquema>(this, android.R.layout.simple_spinner_item, db.getAllEsquema());
        EsquemaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEsquema.setAdapter(EsquemaAdapter);

        ArrayAdapter<Club> dataAdapter = new ArrayAdapter<Club>(this, android.R.layout.simple_spinner_item, db.getAllClubs());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClubPartido.setAdapter(dataAdapter);

        spClubPartido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //db.getAllEquipos(SelectedClub)
                SELECTED_CLUB = (Club) parent.getItemAtPosition(position);
                ArrayAdapter<Equipo> dataAdapter = new ArrayAdapter<Equipo>(ME, android.R.layout.simple_spinner_item, db.getAllEquipos(SELECTED_CLUB));
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spEquipoPartido.setAdapter(dataAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SELECTED_CLUB = null;
            }
        });

        spEquipoPartido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SELECTED_TEAM = (Equipo)parent.getItemAtPosition(position);
                if (SELECTED_ESQUEMA == null){
                    Toast.makeText(getBaseContext(),"Seleccione la Formacion", Toast.LENGTH_LONG).show();
                    SELECTED_TEAM = null;
                }else{
                    //db.getAllJugadores(SELECTED_TEAM);
                    ListViewAdapter_JugadoresPartidoConfig myAdapter = new ListViewAdapter_JugadoresPartidoConfig(db.getAllPlayers(SELECTED_TEAM.getIdEquipo()),getBaseContext(),ME,SELECTED_ESQUEMA);
                    listJugadores.setAdapter(myAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SELECTED_TEAM = null;
            }
        });

        spEsquema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SELECTED_ESQUEMA = (Esquema)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SELECTED_ESQUEMA = null;
            }
        });

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
            ListViewAdapter_JugadoresPartidoConfig l = (ListViewAdapter_JugadoresPartidoConfig)listJugadores.getAdapter();
            l.setEsquema(SELECTED_ESQUEMA);
            List<Jugador> INICIALES = l.getIniciales();
            System.out.println(SELECTED_ESQUEMA.toString());
            if (INICIALES == null){
                Toast.makeText(getBaseContext(),"Jugadores no cumplen con la formacion", Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(getBaseContext(),PartidoCanchaActivity.class);
                intent.putExtra("JUGADORES",(ArrayList)INICIALES);
                intent.putExtra("ESQUEMA",SELECTED_ESQUEMA);
                startActivity(intent);

            }
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
}
