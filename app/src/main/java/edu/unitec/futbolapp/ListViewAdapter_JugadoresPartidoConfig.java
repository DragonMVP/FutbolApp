package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nivx1 on 09/02/2015.
 */
public class ListViewAdapter_JugadoresPartidoConfig extends BaseAdapter {
    private List LISTA;
    private Context Contexto;
    private Activity Actividad;
    private Esquema SELECTED_ESQUEMA;

    private  List<Jugador> INICIALES;

    public ListViewAdapter_JugadoresPartidoConfig(List LISTA, Context contexto, Activity actividad,Esquema ESQUEMA) {
        this.LISTA = LISTA;
        Contexto = contexto;
        Actividad = actividad;
        SELECTED_ESQUEMA=ESQUEMA;

        INICIALES =  new ArrayList();
    }



    @Override
    public int getCount() {
      return LISTA.size();
    }

    @Override
    public Object getItem(int position) {
       return  LISTA.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView==null){
            view = new View(Contexto);
            LayoutInflater inflater=Actividad.getLayoutInflater();
            view=inflater.inflate(R.layout.listview_partidoconfig_players, parent, false);
        }else{
            view = (View)convertView;
        }

        TextView lblName = (TextView)view.findViewById(R.id.lblNombreJugador);
        TextView lblPosicion = (TextView)view.findViewById(R.id.lblPosicion);
        CheckBox cbEntra = (CheckBox)view.findViewById(R.id.cbEntra);

        lblName.setText(((Jugador)LISTA.get(position)).getNombreJugador());
        lblPosicion.setText(((Jugador)LISTA.get(position)).getPosicion().getDescripcionPosicion());

        cbEntra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    INICIALES.add((Jugador) LISTA.get(position));
                } else {
                    if (INICIALES.contains((Jugador) LISTA.get(position)))
                        INICIALES.remove((Jugador) LISTA.get(position));
                }
            }
        });

        return view;
    }

    public List<Jugador> getIniciales(){
        int tmpOfensivo = 0;
        int tmpDefensivo = 0;
        int tmpMedio = 0;
        int tmpPorteria = 0;
        for (Jugador tmp: INICIALES){
            if (tmp.getPosicion().getDescripcionPosicion().equals("Delantero"))
                tmpOfensivo++;
            else  if (tmp.getPosicion().getDescripcionPosicion().equals("Medio"))
                tmpMedio++;
            else if (tmp.getPosicion().getDescripcionPosicion().equals("Defensa"))
                tmpDefensivo++;
            else if (tmp.getPosicion().getDescripcionPosicion().equals("Portero"))
                tmpPorteria++;
        }
        if (SELECTED_ESQUEMA.getDelanteros() != tmpOfensivo)
            return null;
        if (SELECTED_ESQUEMA.getDefensas() != tmpDefensivo)
            return null;
        if (SELECTED_ESQUEMA.getMedios() != tmpMedio)
            return null;
        if (tmpPorteria != 1)
            return null;
        if ((tmpDefensivo+tmpMedio+tmpOfensivo+tmpPorteria) !=11)
            return null;


        return INICIALES;
    }
}
