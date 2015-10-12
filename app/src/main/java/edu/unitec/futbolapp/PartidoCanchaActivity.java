package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by nivx1 on 09/03/2015.
 */

public class PartidoCanchaActivity extends Activity {
    public static  Accion ACCION_PRINCIPAL = null;
    public static TipoPase TIPO_PASE = null;
    public static PartidoMemoria PARTIDO = null;
    public static Jugador ENVIA_PASE = null;
    private Esquema ESQUEMA;
    private boolean midTime = false;

    private boolean menuFalta = false;

    private List<Jugador> JUGADORES_CANCHA;
    //private List<Jugador> JUGADORES_BANCA;

    private List<Accion> LISTA_ACCION;
    public static Chronometer TIEMPO_TOTAL;
    public static Chronometer TIEMPO_PELOTA;

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

    private TextView TIEMPOPARTIDO;
    private TextView TIEMPOBALON;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partido_activity);


        TIEMPOPARTIDO = (TextView) findViewById(R.id.txtTiempoPartido);
        TIEMPOBALON = (TextView) findViewById(R.id.txtTiempoBalon);
        LISTA_ACCION = new ArrayList<>();
        ListView TIPOP = (ListView)findViewById(R.id.btnList);

        MyDatabaseHandler db = new MyDatabaseHandler(this.getBaseContext());
        TIPOP.setAdapter(new ListViewAdapterTipoPase(db.getTipoPase(), getBaseContext(), this));
        LISTA_ACCION = db.getDefaultAccions();
        LISTA_ACCION.addAll(db.getUserAccions());
        List<Accion> LISTA_PORTERO = db.getDefaultAccionPortero();
        LISTA_PORTERO.addAll(db.getUserAccionsPortero());
        db.close();
        ButtonGridView ACCIONJUGADORES = new ButtonGridView(LISTA_ACCION, this, getBaseContext());
        ButtonGridView ACCIONPORTERO = new ButtonGridView(LISTA_PORTERO,this,getBaseContext());
        GridView btnGrid = (GridView)findViewById(R.id.btnGrid);
        btnGrid.setAdapter(ACCIONJUGADORES);

        JUGADORES_CANCHA = (ArrayList<Jugador>)getIntent().getSerializableExtra("JUGADORES");
        PARTIDO = new PartidoMemoria();
        ESQUEMA =(Esquema)getIntent().getSerializableExtra("ESQUEMA");
        PARTIDO.initJugadoresCancha(JUGADORES_CANCHA);

        btnDefensas = new ImageButton[ESQUEMA.getDefensas()];
        btnMedios = new ImageButton[ESQUEMA.getMedios()];
        btnOfensivos= new ImageButton[ESQUEMA.getDelanteros()];

        int Def=0,Med =0,Of = 0;


        for(Jugador tmp: JUGADORES_CANCHA){
            Bitmap thumbnail;
            if (tmp.getPICTURE() == null){
                thumbnail = BitmapFactory.decodeResource(getResources(), R.drawable.defaultuser);
            }else
                thumbnail = BitmapFactory.decodeByteArray(tmp.getPICTURE(), 0, tmp.getPICTURE().length);

            if (tmp.getPosicion().getDescripcionPosicion().equals("Defensa")) {
                btnDefensas[Def] = (ImageButton) findViewById(LISTABOTONES[Def]);
                btnDefensas[Def].setImageBitmap(thumbnail);
                btnDefensas[Def].setOnClickListener(new onClickHandlerJugador(tmp,this,btnGrid,ACCIONJUGADORES));
                Def++;
            }else  if (tmp.getPosicion().getDescripcionPosicion().equals("Medio")) {
                btnMedios[Med] = (ImageButton)findViewById(LISTABOTONES[ESQUEMA.getDefensas()+Med]);
                btnMedios[Med].setImageBitmap(thumbnail);
                btnMedios[Med].setOnClickListener(new onClickHandlerJugador(tmp,this,btnGrid,ACCIONJUGADORES));
                Med++;
            } else  if (tmp.getPosicion().getDescripcionPosicion().equals("Delantero")) {
                btnOfensivos[Of] = (ImageButton)findViewById(LISTABOTONES[ESQUEMA.getDefensas()+ESQUEMA.getMedios()+Of]);
                btnOfensivos[Of].setImageBitmap(thumbnail);
                btnOfensivos[Of].setOnClickListener(new onClickHandlerJugador(tmp,this,btnGrid,ACCIONJUGADORES));
                Of++;
            }else if (tmp.getPosicion().getDescripcionPosicion().equals("Portero")){
                btnPortero = (ImageButton)findViewById(R.id.btnPortero);
                btnPortero.setImageBitmap(thumbnail);
                btnPortero.setOnClickListener(new onClickHandlerJugador(tmp,this,btnGrid,ACCIONPORTERO));
            }
        }




        RelativeLayout canchaLayout = (RelativeLayout)findViewById(R.id.layoutCancha);
        canchaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public synchronized void onClick(View v) {
                if (ESPERA_PASE != 0) {
                    Toast.makeText(v.getContext(), "PASE FALLADO", Toast.LENGTH_SHORT).show();
                    PARTIDO.PaseJugador((Pase) ACCION_PRINCIPAL, TIPO_PASE, ENVIA_PASE, null, TIEMPO_TOTAL);
                    ENVIA_PASE = null;
                    ESPERA_PASE = 0;
                }
            }
        });



        TIEMPO_PELOTA = new Chronometer(new Date(),TIEMPOBALON,this);

        TIEMPO_TOTAL = new Chronometer(new Date(),TIEMPOPARTIDO,this);
        TIEMPO_TOTAL.start();
        TIEMPO_PELOTA.start();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_partido, menu);
        menu.findItem(R.id.resume_time).setVisible(false);
        //((MenuItem)findViewById(R.id.resume_time)).setVisible(false);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.pause_time).setVisible(!midTime);
        menu.findItem(R.id.resume_time).setVisible(midTime);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.pause_time) {
            TIEMPO_PELOTA.setPause(true);
            midTime = true;
            //MenuItem resume = (MenuItem) findViewById(R.id.resume_time);
            //resume.setVisible(true);
        }else if (id == R.id.resume_time){
            TIEMPO_PELOTA.setPause(false);
            midTime = false;
            //MenuItem resume = (MenuItem) findViewById(R.id.pause_time);
            //resume.setVisible(true);
        }else if (id == R.id.midtime){
            TIEMPO_PELOTA.setPause(true);
            TIEMPO_TOTAL.setPause(true);
            Intent intent = new Intent(getBaseContext(),MedioTiempoActivity.class);
            intent.putExtra("PARTIDOMEMORIA",PARTIDO);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public synchronized void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        //Context menu


            if (!menuFalta) {
                menu.setHeaderTitle("Falta");
                menu.add(Menu.NONE, 1, Menu.NONE, "Cometida");
                menu.add(Menu.NONE, 2, Menu.NONE, "Recibida");
            }else{
                menuFalta = true;
                menu.setHeaderTitle("Tarjeta");
                menu.add(Menu.NONE, 3, Menu.NONE, "Amarilla");
                menu.add(Menu.NONE,4,Menu.NONE,"Roja");
                menu.add(Menu.NONE,5,Menu.NONE,"Ninguna");
            }
        }

    @Override
    public synchronized boolean onContextItemSelected(final MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId())
        {
            case 1:
            {
                //PREGUNTAR TARJETA
                //PARTIDO.FaltaJugador((Falta)ACCION_PRINCIPAL,ENVIA_PASE,1,1,TIEMPO_TOTAL);
                menuFalta = true;
                this.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        openContextMenu(item.getActionView());
                    }
                });
            }
            break;
            case 2:
            {
                PARTIDO.FaltaJugador((Falta)ACCION_PRINCIPAL,ENVIA_PASE,0,-1,TIEMPO_TOTAL);
            }
            break;
            case 3:{
                PARTIDO.FaltaJugador((Falta)ACCION_PRINCIPAL,ENVIA_PASE,1,1,TIEMPO_TOTAL);
            }break;
            case 4:{
                PARTIDO.FaltaJugador((Falta)ACCION_PRINCIPAL,ENVIA_PASE,1,2,TIEMPO_TOTAL);
            }break;
            case 5:{PARTIDO.FaltaJugador((Falta)ACCION_PRINCIPAL,ENVIA_PASE,1,0,TIEMPO_TOTAL);}break;
        }
        return super.onContextItemSelected(item);

    }

}

class TipoPase implements Serializable{
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



