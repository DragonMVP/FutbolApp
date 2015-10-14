package edu.unitec.futbolapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import java.util.Iterator;
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
    private String indicador = "";

    private boolean menuFalta = false;

    private List<Jugador> JUGADORES_CANCHA;
    private List<Jugador> JUGADORES_BANCA;

    private ArrayList<Jugador> defensas;
    private ArrayList<Jugador> medios;
    private ArrayList<Jugador> ataque;

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

    private ButtonGridView ACCIONPORTERO;
    private GridView btnGrid;
    private ButtonGridView ACCIONJUGADORES;

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
        ACCIONJUGADORES = new ButtonGridView(LISTA_ACCION, this, getBaseContext());
        ACCIONPORTERO = new ButtonGridView(LISTA_PORTERO,this,getBaseContext());
        btnGrid = (GridView)findViewById(R.id.btnGrid);
        btnGrid.setAdapter(ACCIONJUGADORES);

        JUGADORES_CANCHA = (ArrayList<Jugador>)getIntent().getSerializableExtra("JUGADORES");
        JUGADORES_BANCA = (ArrayList<Jugador>)getIntent().getSerializableExtra("BANCA");
        PARTIDO = new PartidoMemoria();
        ESQUEMA =(Esquema)getIntent().getSerializableExtra("ESQUEMA");
        PARTIDO.initJugadoresCancha(JUGADORES_CANCHA);

        btnDefensas = new ImageButton[ESQUEMA.getDefensas()];
        btnMedios = new ImageButton[ESQUEMA.getMedios()];
        btnOfensivos= new ImageButton[ESQUEMA.getDelanteros()];

        defensas = new ArrayList<Jugador>();
        medios = new ArrayList<Jugador>();
        ataque = new ArrayList<Jugador>();

        int Def=0,Med =0,Of = 0;


        for(Jugador tmp: JUGADORES_CANCHA){
            tmp.setAyyposicion(tmp.getPosicion().getDescripcionPosicion());
            Bitmap thumbnail;
            if (tmp.getPICTURE() == null){
                thumbnail = BitmapFactory.decodeResource(getResources(), R.drawable.defaultuser);
            }else
                thumbnail = BitmapFactory.decodeByteArray(tmp.getPICTURE(), 0, tmp.getPICTURE().length);

            if (tmp.getPosicion().getDescripcionPosicion().equals("Defensa")) {
                btnDefensas[Def] = (ImageButton) findViewById(LISTABOTONES[Def]);
                btnDefensas[Def].setImageBitmap(thumbnail);
                btnDefensas[Def].setOnClickListener(new onClickHandlerJugador(tmp, this, btnGrid, ACCIONJUGADORES));
                //btnDefensas[Def].setOnCreateContextMenuListener(this);
                registerForContextMenu(btnDefensas[Def]);
                defensas.add(tmp);
                Def++;
            }else  if (tmp.getPosicion().getDescripcionPosicion().equals("Medio")) {
                btnMedios[Med] = (ImageButton)findViewById(LISTABOTONES[ESQUEMA.getDefensas()+Med]);
                btnMedios[Med].setImageBitmap(thumbnail);
                btnMedios[Med].setOnClickListener(new onClickHandlerJugador(tmp, this, btnGrid, ACCIONJUGADORES));
                registerForContextMenu(btnMedios[Med]);
                medios.add(tmp);
                //btnMedios[Med].setOnCreateContextMenuListener(this);
                Med++;
            } else  if (tmp.getPosicion().getDescripcionPosicion().equals("Delantero")) {
                btnOfensivos[Of] = (ImageButton)findViewById(LISTABOTONES[ESQUEMA.getDefensas()+ESQUEMA.getMedios()+Of]);
                btnOfensivos[Of].setImageBitmap(thumbnail);
                btnOfensivos[Of].setOnClickListener(new onClickHandlerJugador(tmp, this, btnGrid, ACCIONJUGADORES));
                registerForContextMenu(btnOfensivos[Of]);
                ataque.add(tmp);
                //btnOfensivos[Of].setOnCreateContextMenuListener(this);
                Of++;
            }else if (tmp.getPosicion().getDescripcionPosicion().equals("Portero")){
                btnPortero = (ImageButton)findViewById(R.id.btnPortero);
                btnPortero.setImageBitmap(thumbnail);
                btnPortero.setOnClickListener(new onClickHandlerJugador(tmp, this, btnGrid, ACCIONPORTERO));
                registerForContextMenu(btnPortero);
                //btnPortero.setOnCreateContextMenuListener(this);
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
        int k = 0;
        if(v == btnPortero){
            System.out.println("lululululu");
            populateMenu(menu);
            indicador = "portero0";
            return;
        }else{
            k = btnDefensas.length-1;
            for(int i = 0; i<k;i++){
                if(v == btnDefensas[i]){
                    System.out.println("lelelelele");
                    populateMenu(menu);
                    indicador = "defensa"+i;
                    return;
                }
            }
            k = btnMedios.length-1;
            for(int i = 0; i<k;i++){
                    if(v == btnMedios[i]){
                    System.out.println("lelelelela");
                        populateMenu(menu);
                        indicador = "medio"+i;
                    return;
                }
            }
            k = btnOfensivos.length-1;
            for(int i = 0; i<k;i++){
                if(v == btnOfensivos[i]){
                    System.out.println("lelelelelo");
                    populateMenu(menu);
                    indicador = "ofensivo"+i;
                    return;
                }
            }



            if (!menuFalta) {
                menu.setHeaderTitle("Falta");
                menu.add(Menu.NONE, 666, Menu.NONE, "Cometida");
                menu.add(Menu.NONE, 667, Menu.NONE, "Recibida");
            }else{
                menuFalta = true;
                menu.setHeaderTitle("Tarjeta");
                menu.add(Menu.NONE, 668, Menu.NONE, "Amarilla");
                menu.add(Menu.NONE,669,Menu.NONE,"Roja");
                menu.add(Menu.NONE,660,Menu.NONE,"Ninguna");
            }
        }

    }

    public void populateMenu(ContextMenu menu){
        menu.setHeaderTitle("Sustitución");
        for(int i = 0;i < JUGADORES_BANCA.size();i++){
            menu.add(Menu.NONE,i,Menu.NONE,JUGADORES_BANCA.get(i).getNombreJugador());
        }
    }

    @Override
    public synchronized boolean onContextItemSelected(final MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId())
        {
            case 666:
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
            case 667:
            {
                PARTIDO.FaltaJugador((Falta)ACCION_PRINCIPAL,ENVIA_PASE,0,-1,TIEMPO_TOTAL);
            }
            break;
            case 668:{
                PARTIDO.FaltaJugador((Falta)ACCION_PRINCIPAL,ENVIA_PASE,1,1,TIEMPO_TOTAL);
            }break;
            case 669:{
                PARTIDO.FaltaJugador((Falta)ACCION_PRINCIPAL,ENVIA_PASE,1,2,TIEMPO_TOTAL);
            }break;
            case 660:{PARTIDO.FaltaJugador((Falta)ACCION_PRINCIPAL,ENVIA_PASE,1,0,TIEMPO_TOTAL);}break;
            default: {
                String ind = indicador.substring(0, indicador.length() - 1);
                int indi = Integer.parseInt(indicador.substring(indicador.length() - 1));
                Jugador j = JUGADORES_BANCA.get(item.getItemId());
                Jugador tmp;
                Bitmap thumbnail;


                if (ind.equals("portero")) {
                    for(int i = 0; i<JUGADORES_CANCHA.size();i++){
                        tmp = JUGADORES_CANCHA.get(i);
                        if (tmp.getAyyPosicion().equals("Portero")) {
                            PARTIDO.CambioJugador(tmp, j, TIEMPO_TOTAL);

                            if (j.getPICTURE() == null) {
                                btnPortero.setBackgroundResource(R.drawable.defaultuser);
                            } else {
                                BitmapFactory.Options bfOptions = new BitmapFactory.Options();
                                bfOptions.inDither = false;
                                bfOptions.inPreferredConfig = Bitmap.Config.ALPHA_8;
                                bfOptions.inSampleSize = 8;
                                thumbnail = BitmapFactory.decodeByteArray(j.getPICTURE(), 0, j.getPICTURE().length, bfOptions);
                                btnPortero.setImageBitmap(thumbnail);
                            }

                            btnPortero = (ImageButton) findViewById(R.id.btnPortero);
                            //btnPortero.setImageBitmap(thumbnail);
                            btnPortero.setOnClickListener(new onClickHandlerJugador(j, this, btnGrid, ACCIONPORTERO));
                            JUGADORES_CANCHA.remove(tmp);
                            j.setAyyposicion("Portero");
                            JUGADORES_CANCHA.add(j);
                            System.out.println(tmp.getNombreJugador()+".:.:.::::::::::" + j.getNombreJugador());
                            break;
                        }
                    }
                } else if (ind.equals("defensa")) {
                    for(int i = 0; i<JUGADORES_CANCHA.size();i++){
                        tmp = JUGADORES_CANCHA.get(i);
                        if (tmp.getAyyPosicion().equals("Defensa")) {
                            if (tmp == defensas.get(indi)) {

                                if (j.getPICTURE() == null) {
                                    btnDefensas[indi].setBackgroundResource(R.drawable.defaultuser);
                                } else {
                                    BitmapFactory.Options bfOptions = new BitmapFactory.Options();
                                    bfOptions.inDither = false;
                                    bfOptions.inPreferredConfig = Bitmap.Config.ALPHA_8;
                                    bfOptions.inSampleSize = 8;
                                    thumbnail = BitmapFactory.decodeByteArray(j.getPICTURE(), 0, j.getPICTURE().length, bfOptions);
                                    btnDefensas[indi].setImageBitmap(thumbnail);
                                }

                                PARTIDO.CambioJugador(tmp, j, TIEMPO_TOTAL);
                                btnDefensas[indi] = (ImageButton) findViewById(LISTABOTONES[indi]);
                                //btnDefensas[indi].setImageBitmap(thumbnail);
                                btnDefensas[indi].setOnClickListener(new onClickHandlerJugador(j, this, btnGrid, ACCIONJUGADORES));
                                JUGADORES_CANCHA.remove(tmp);
                                defensas.get(indi).setAyyposicion("Defensa");
                                JUGADORES_CANCHA.add(defensas.get(indi));
                                System.out.println(".:.:.::::::::::" + j.getNombreJugador());
                                break;
                            }
                        }
                    }
                } else if (ind.equals("medio")) {
                    for(int i = 0; i<JUGADORES_CANCHA.size();i++){
                        tmp = JUGADORES_CANCHA.get(i);
                        if (tmp.getAyyPosicion().equals("Medio")) {
                            if (tmp == medios.get(indi)) {

                                if (j.getPICTURE() == null) {
                                    btnMedios[indi].setBackgroundResource(R.drawable.defaultuser);
                                } else {
                                    BitmapFactory.Options bfOptions = new BitmapFactory.Options();
                                    bfOptions.inDither = false;
                                    bfOptions.inPreferredConfig = Bitmap.Config.ALPHA_8;
                                    bfOptions.inSampleSize = 8;
                                    thumbnail = BitmapFactory.decodeByteArray(j.getPICTURE(), 0, j.getPICTURE().length, bfOptions);
                                    btnMedios[indi].setImageBitmap(thumbnail);
                                }

                                PARTIDO.CambioJugador(tmp, j, TIEMPO_TOTAL);
                                btnMedios[indi] = (ImageButton) findViewById(LISTABOTONES[ESQUEMA.getDefensas() + indi]);

                                btnMedios[indi].setOnClickListener(new onClickHandlerJugador(j, this, btnGrid, ACCIONJUGADORES));
                                JUGADORES_CANCHA.remove(tmp);
                                medios.get(indi).setAyyposicion("Medio");
                                JUGADORES_CANCHA.add(medios.get(indi));
                                System.out.println("+++++++++++++++" + j.getNombreJugador());
                                break;
                            }
                        }
                    }
                } else {
                    for(int i = 0; i<JUGADORES_CANCHA.size();i++){
                        tmp = JUGADORES_CANCHA.get(i);
                        if (tmp.getAyyPosicion().equals("Delantero")) {
                            if (tmp == ataque.get(indi)) {

                                if (j.getPICTURE() == null) {
                                    btnOfensivos[indi].setBackgroundResource(R.drawable.defaultuser);
                                } else {
                                    BitmapFactory.Options bfOptions = new BitmapFactory.Options();
                                    bfOptions.inDither = false;
                                    bfOptions.inPreferredConfig = Bitmap.Config.ALPHA_8;
                                    bfOptions.inSampleSize = 8;
                                    thumbnail = BitmapFactory.decodeByteArray(j.getPICTURE(), 0, j.getPICTURE().length, bfOptions);
                                    btnOfensivos[indi].setImageBitmap(thumbnail);
                                }

                                PARTIDO.CambioJugador(tmp, j, TIEMPO_TOTAL);
                                btnOfensivos[indi] = (ImageButton) findViewById(LISTABOTONES[ESQUEMA.getDefensas() + ESQUEMA.getMedios() + indi]);
                                //btnOfensivos[indi].setImageBitmap(thumbnail);
                                btnOfensivos[indi].setOnClickListener(new onClickHandlerJugador(j, this, btnGrid, ACCIONJUGADORES));
                                JUGADORES_CANCHA.remove(tmp);
                                ataque.get(indi).setAyyposicion("Delantero");
                                JUGADORES_CANCHA.add(ataque.get(indi));
                                System.out.println("---------------"+j.getNombreJugador());
                                break;
                            }
                        }
                    }
                }
            }
            break;

        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        final CharSequence[] items = {"Si", "No"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Desea terminar el partido?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if(items[item].equals("Si")){
                    finish();
                }else{
                    dialog.cancel();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
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



