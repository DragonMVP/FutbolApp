package edu.unitec.futbolapp;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by nivx1 on 09/06/2015.
 */
public class onClickHandlerJugador implements View.OnClickListener {
    private Jugador JUGADOR;
    private Activity Actividad;
    private GridView btnGrid;
    private ButtonGridView LISTA_ACCION;

    private static boolean PORTERO;

    public onClickHandlerJugador(Jugador JUGADOR, Activity actividad, GridView btnGrid, ButtonGridView LISTA_ACCION) {
        this.JUGADOR = JUGADOR;
        Actividad = actividad;
        this.btnGrid = btnGrid;
        this.LISTA_ACCION = LISTA_ACCION;
    }

    //Image?

    @Override
    public synchronized void onClick(View v) {
        if (JUGADOR.getPosicion().getDescripcionPosicion().equals("Portero") && !PORTERO){
            btnGrid.setAdapter(LISTA_ACCION);
            PORTERO = true;
        }else if (!JUGADOR.getPosicion().getDescripcionPosicion().equals("Portero") && PORTERO){
            btnGrid.setAdapter(LISTA_ACCION);
            PORTERO = false;
        }

        if (PartidoCanchaActivity.ACCION_PRINCIPAL == null){
            PartidoCanchaActivity.ACCION_PRINCIPAL = MyDatabaseHandler.DEFAULT_PASE_ACCION;
            if (PartidoCanchaActivity.TIPO_PASE == null){
                PartidoCanchaActivity.TIPO_PASE = MyDatabaseHandler.DEFAULT_TIPO_PASE;
            }
        }else{
        }
        if (PartidoCanchaActivity.ACCION_PRINCIPAL instanceof Pase){
            if (PartidoCanchaActivity.TIPO_PASE == null){
                PartidoCanchaActivity.TIPO_PASE = MyDatabaseHandler.DEFAULT_TIPO_PASE;
            }
            if (PartidoCanchaActivity.ESPERA_PASE == 0) {
                PartidoCanchaActivity.ENVIA_PASE = JUGADOR;
                PartidoCanchaActivity.ESPERA_PASE = 1;

                //Toast.makeText(v.getContext(), "PASE INICIADO", Toast.LENGTH_SHORT).show();
            } else {
                PartidoCanchaActivity.PARTIDO.PaseJugador((Pase) PartidoCanchaActivity.ACCION_PRINCIPAL,
                        PartidoCanchaActivity.TIPO_PASE, PartidoCanchaActivity.ENVIA_PASE, JUGADOR,
                        PartidoCanchaActivity.TIEMPO_TOTAL);
                PartidoCanchaActivity.ENVIA_PASE = null;
                PartidoCanchaActivity.ESPERA_PASE = 0;
                Toast.makeText(v.getContext(), "PASE RECIBIDO", Toast.LENGTH_SHORT).show();
            }
        }else if (PartidoCanchaActivity.ACCION_PRINCIPAL instanceof Falta){
            PartidoCanchaActivity.ENVIA_PASE = JUGADOR;
            PartidoCanchaActivity.ESPERA_PASE = 0;
            Actividad.registerForContextMenu(v);
            Actividad.openContextMenu(v);
           //Toast.makeText(v.getContext(),"FALTA COMETIDA",Toast.LENGTH_SHORT).show();
        }else{
            PartidoCanchaActivity.ENVIA_PASE = null;
            PartidoCanchaActivity.ESPERA_PASE = 0;
            PartidoCanchaActivity.PARTIDO.AccionJugador(PartidoCanchaActivity.ACCION_PRINCIPAL, JUGADOR, PartidoCanchaActivity.TIEMPO_TOTAL);
            Toast.makeText(v.getContext(),"ACCION CUALQUIERA",Toast.LENGTH_SHORT).show();
        }
        if (!(PartidoCanchaActivity.ACCION_PRINCIPAL instanceof Falta)) {
            if (PartidoCanchaActivity.ESPERA_PASE == 0)
                PartidoCanchaActivity.ACCION_PRINCIPAL = null;
        }
        if (PartidoCanchaActivity.ESPERA_PASE == 0)
            PartidoCanchaActivity.TIPO_PASE = null;
    }


}
