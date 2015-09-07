package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nivx1 on 09/03/2015.
 */

public class PartidoCanchaActivity extends Activity {
    public static Accion ACCION_PRINCIPAL = null;
    public static TipoPase TIPO_PASE = null;
    private Esquema ESQUEMA;

    private List<Jugador> JUGADORES_CANCHA;
    //private List<Jugador> JUGADORES_BANCA;

    private List<Accion> LISTA_ACCION;
    private static Chronometer TIEMPO_TOTAL;
    private static Chronometer TIEMPO_PELOTA;

    public static int ESPERA_PASE =0;

    private final int LISTABOTONES[]={
        R.id.btnJugador1,
            R.id.btnJugador2,
            R.id.btnJugador3,
            R.id.btnJugador4,
            R.id.btnJugador5,
            R.id.btnJugador6,
            R.id.btnJugador7,
            R.id.btnJugador8,
            R.id.btnJugador9,
            R.id.btnJugador10
    };

    private ImageButton btnPortero;
    private ImageButton[] btnDefensas;
    private ImageButton[] btnMedios;
    private ImageButton[] btnOfensivos;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partido_activity);

       JUGADORES_CANCHA = (ArrayList<Jugador>)getIntent().getSerializableExtra("JUGADORES");
        ESQUEMA =(Esquema)getIntent().getSerializableExtra("ESQUEMA");

        btnDefensas = new ImageButton[ESQUEMA.getDefensas()];
        btnMedios = new ImageButton[ESQUEMA.getMedios()];
        btnOfensivos= new ImageButton[ESQUEMA.getDelanteros()];
        
        int Def=0,Med =0,Of = 0;


        for(Jugador tmp: JUGADORES_CANCHA){
            if (tmp.getPosicion().getDescripcionPosicion().equals("Defensa")) {
                btnOfensivos[Def] = (ImageButton) findViewById(LISTABOTONES[Def]);
                btnOfensivos[Def].setOnClickListener(new onClickHandlerJugador(tmp));
                Def++;
            }else  if (tmp.getPosicion().getDescripcionPosicion().equals("Medio")) {
                btnMedios[Med] = (ImageButton)findViewById(LISTABOTONES[ESQUEMA.getDefensas()+Med]);
                btnMedios[Med].setOnClickListener(new onClickHandlerJugador(tmp));
                Med++;
            } else  if (tmp.getPosicion().getDescripcionPosicion().equals("Delantero")) {
                btnOfensivos[Of] = (ImageButton)findViewById(LISTABOTONES[ESQUEMA.getDefensas()+ESQUEMA.getMedios()+Of]);
                btnOfensivos[Of].setOnClickListener(new onClickHandlerJugador(tmp));
                Of++;
            }
        }


        LISTA_ACCION = new ArrayList<>();
        MyDatabaseHandler db = new MyDatabaseHandler(this.getBaseContext());
        LISTA_ACCION = db.getDefaultAccions();
        db.close();

        RelativeLayout canchaLayout = (RelativeLayout)findViewById(R.id.layoutCancha);
        canchaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ESPERA_PASE != 0){
                    Toast.makeText(v.getContext(),"PASE FALLADO",Toast.LENGTH_SHORT).show();
                    ESPERA_PASE = 0;
                }
            }
        });

        GridView btnGrid = (GridView)findViewById(R.id.btnGrid);
        btnGrid.setAdapter(new ButtonGridView(LISTA_ACCION, this, getBaseContext()));

        TIEMPO_PELOTA = new Chronometer(new Date());
        TIEMPO_TOTAL = new Chronometer(new Date());
        TIEMPO_TOTAL.start();
        TIEMPO_PELOTA.start();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_menu_principal, menu);
        return true;
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


}

class TipoPase{
    int id;
    String name;

    public TipoPase(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}