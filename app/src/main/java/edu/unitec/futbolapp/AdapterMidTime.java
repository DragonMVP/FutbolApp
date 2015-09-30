package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nivx1 on 09/15/2015.
 */
public class AdapterMidTime extends BaseAdapter {
    private List<Accion> ACCIONES;
    private Jugador JUGADOR;
    private PartidoMemoria PAR_MEM;

    private Context Contexto;
    private Activity Actividad;


    public AdapterMidTime(List<Accion> ACCIONES, Jugador JUGADOR, PartidoMemoria PAR_MEM, Context contexto, Activity actividad) {
        this.ACCIONES = ACCIONES;
        this.JUGADOR = JUGADOR;
        this.PAR_MEM = PAR_MEM;
        Contexto = contexto;
        Actividad = actividad;


    }



    @Override
    public int getCount() {
        return ACCIONES.size();
    }

    @Override
    public Object getItem(int position) {
        return ACCIONES.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView==null){
            view = new View(Contexto);
            LayoutInflater inflater=Actividad.getLayoutInflater();
            view=inflater.inflate(R.layout.layout_midtime, parent, false);
        }else{
            view = (View)convertView;
        }

        TextView DescAccion = (TextView)view.findViewById(R.id.txtViewDescAccion);
        DescAccion.setText(ACCIONES.get(position).getNombreAccion());
        TextView TimesAccion = (TextView)view.findViewById(R.id.txtViewTimesAccion);
        if (JUGADOR != null){
            if (ACCIONES.get(position) instanceof Pase){
                TimesAccion.setText(PAR_MEM.getCountPaseJugador(ACCIONES.get(position), JUGADOR) + "");
            }else if (ACCIONES.get(position) instanceof Falta){
                TimesAccion.setText(PAR_MEM.getCountFaltaJugador(ACCIONES.get(position), JUGADOR) + "");
            }else
                TimesAccion.setText(PAR_MEM.getCountAccionJugador(ACCIONES.get(position),JUGADOR) + "");
        }else {
            if (ACCIONES.get(position) instanceof Pase){
                TimesAccion.setText(PAR_MEM.getCountAllPaseJugador(ACCIONES.get(position)) + "");
            }else if (ACCIONES.get(position) instanceof Falta){
                TimesAccion.setText(PAR_MEM.getCountAllFaltaJugador(ACCIONES.get(position)) + "");
            }else
                TimesAccion.setText(PAR_MEM.getCountAllAccionJugador(ACCIONES.get(position)) + "");
        }



        return view;
    }
}
