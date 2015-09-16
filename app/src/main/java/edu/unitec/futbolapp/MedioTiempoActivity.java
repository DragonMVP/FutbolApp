package edu.unitec.futbolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by daemon on 09/15/2015.
 */
public class MedioTiempoActivity extends AppCompatActivity {

    private TextView GOLES;
    private TextView PASESEXITOSOS;
    private TextView FALTAS;
    private TextView PASESFALLIDOS;
    private TextView TARJETASAMARILLAS;
    private TextView TARJETASROJAS;
    PartidoMemoria pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medio_tiempo);
        GOLES = (TextView)findViewById(R.id.textViewGoles);
        PASESEXITOSOS = (TextView)findViewById(R.id.textViewPasesExitosos);
        PASESFALLIDOS = (TextView)findViewById(R.id.textViewPasesFallidos);
        FALTAS = (TextView)findViewById(R.id.textViewFaltas);
        TARJETASAMARILLAS = (TextView)findViewById(R.id.textViewTarjetasA);
        TARJETASROJAS = (TextView)findViewById(R.id.textViewTarjetasR);

        pm = (PartidoMemoria)getIntent().getSerializableExtra("PARTIDOMEMORIA");
        fillStats(pm);

        try {
            Button btnInd = (Button) findViewById(R.id.butInd);
            btnInd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),JugadorStatsActivity.class);
                    intent.putExtra("JUGADORES", (ArrayList<JugadorPartido>) pm.getJugadores());
                    intent.putExtra("PARTIDOMEMORIA", pm);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medio_tiempo, menu);
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

    private void fillStats(PartidoMemoria pm){
        GOLES.setText(pm.getCountAllGoles()+"");
        FALTAS.setText(pm.getCountAllFaltas()+"");
        PASESEXITOSOS.setText(pm.getCountAllPasesExitosos()+"");
        PASESFALLIDOS.setText(pm.getCountAllPasesFallidos()+"");
        TARJETASAMARILLAS.setText(pm.getCountAllTarjetasAmarillas() + "");
        TARJETASROJAS.setText(pm.getCountAllTarjetasRojas()+"");
    }
}
