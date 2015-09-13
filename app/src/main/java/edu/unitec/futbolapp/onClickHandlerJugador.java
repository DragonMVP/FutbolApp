package edu.unitec.futbolapp;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Toast;

/**
 * Created by nivx1 on 09/06/2015.
 */
public class onClickHandlerJugador implements View.OnClickListener {
    private Jugador JUGADOR;
    private Activity Actividad;
    public onClickHandlerJugador(Jugador JUGADOR,Activity A) {
        this.JUGADOR = JUGADOR;
        Actividad = A;
    }

    //Image?

    @Override
    public void onClick(View v) {
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
                Toast.makeText(v.getContext(), "PASE INICIADO", Toast.LENGTH_SHORT).show();
                PartidoCanchaActivity.ENVIA_PASE = JUGADOR;
                PartidoCanchaActivity.ESPERA_PASE = 1;
            } else {
                Toast.makeText(v.getContext(), "PASE RECIBIDO", Toast.LENGTH_SHORT).show();
                PartidoCanchaActivity.PARTIDO.PaseJugador((Pase) PartidoCanchaActivity.ACCION_PRINCIPAL,
                        PartidoCanchaActivity.TIPO_PASE, PartidoCanchaActivity.ENVIA_PASE, JUGADOR,
                        PartidoCanchaActivity.TIEMPO_TOTAL);
                PartidoCanchaActivity.ENVIA_PASE = null;
                PartidoCanchaActivity.ESPERA_PASE = 0;
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
            PartidoCanchaActivity.PARTIDO.AccionJugador(PartidoCanchaActivity.ACCION_PRINCIPAL,JUGADOR,PartidoCanchaActivity.TIEMPO_TOTAL);
            Toast.makeText(v.getContext(),"ACCION CUALQUIERA",Toast.LENGTH_SHORT).show();
        }
        PartidoCanchaActivity.ACCION_PRINCIPAL = null;
        PartidoCanchaActivity.TIPO_PASE = null;
    }


}
