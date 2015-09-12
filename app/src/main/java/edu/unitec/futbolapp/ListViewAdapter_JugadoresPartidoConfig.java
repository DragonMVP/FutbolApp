package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
public class ListViewAdapter_JugadoresPartidoConfig extends ArrayAdapter<Jugador> {
    private List<Jugador> LISTA;
    private Context Contexto;
    private Activity Actividad;
    private Esquema SELECTED_ESQUEMA;

    private  List<Jugador> INICIALES;

    public ListViewAdapter_JugadoresPartidoConfig(List<Jugador> LISTA, Context contexto, Activity actividad,Esquema ESQUEMA) {
        super(contexto, R.layout.listview_partidoconfig_players, LISTA);
        this.LISTA = LISTA;
        Contexto = contexto;
        Actividad = actividad;
        SELECTED_ESQUEMA=ESQUEMA;

        INICIALES =  new ArrayList();
    }

    static class ViewHolder {//daemon: quiero comer galletas
        protected CheckBox checkbox;
        protected TextView name;
        protected TextView posicion;
    }

    @Override
    public int getCount() {
      return LISTA.size();
    }

    @Override
    public Jugador getItem(int position) {
       return  (Jugador)LISTA.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder vh = null;
        if(convertView==null){
            //view = new View(Contexto);
            LayoutInflater inflater=Actividad.getLayoutInflater();
            convertView=inflater.inflate(R.layout.listview_partidoconfig_players, parent, false);
          /*  vh = new ViewHolder();
            vh.checkbox = (CheckBox)convertView.findViewById(R.id.cbEntra);
            vh.name = (TextView)convertView.findViewById(R.id.lblNombreJugador);
            vh.posicion = (TextView)convertView.findViewById(R.id.lblPosicion);
            */
        }else{
            //convertView = (View)convertView;
            vh = (ViewHolder)convertView.getTag();
        }

        TextView lblName = (TextView)convertView.findViewById(R.id.lblNombreJugador);
        TextView lblPosicion = (TextView) convertView.findViewById(R.id.lblPosicion);
        CheckBox cbEntra = (CheckBox) convertView.findViewById(R.id.cbEntra);

        lblName.setText(((Jugador) LISTA.get(position)).getNombreJugador());
        lblPosicion.setText(((Jugador) LISTA.get(position)).getPosicion().getDescripcionPosicion());
        cbEntra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox CB = (CheckBox) v.findViewById(R.id.cbEntra);

                if (CB.isChecked()) {
                    INICIALES.add((Jugador) LISTA.get(position));
                    LISTA.get(position).setSelected(true);
                } else {
                    LISTA.get(position).setSelected(false);
                    if (INICIALES.contains((Jugador) LISTA.get(position))) {
                        INICIALES.remove((Jugador) LISTA.get(position));
                    }
                }
            }
        });
        cbEntra.setChecked(LISTA.get(position).isSelected());
        //convertView.setTag(vh);

        //convertView.setTag(R.id.cbEntra, vh.checkbox);

        //convertView.setTag(R.id.lblNombreJugador, vh.name);

        //convertView.setTag(R.id.lblPosicion, vh.posicion);

        //

        //vh.checkbox.setTag(position); // This line is important

        //vh.name.setText(LISTA.get(position).getNombreJugador());

        //vh.posicion.setText(LISTA.get(position).getPosicion().getDescripcionPosicion());

        //vh.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

        //@Override

        //public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        //int getPosition = (Integer) buttonView.getTag();

        //LISTA.get(position).setSelected(buttonView.isChecked());

        //

        //if (isChecked) {

        //INICIALES.add((Jugador) LISTA.get(position));

        ////System.out.println("-----------------"+LISTA.get(position).getNombreJugador());

        //} else {

        //if (INICIALES.contains((Jugador) LISTA.get(position))) {

        //INICIALES.remove((Jugador) LISTA.get(position));

        ////System.out.println("++++++++++++++++++++" + LISTA.get(position).getNombreJugador());

        //}

        //}

        //}

        //});

        //vh.checkbox.setChecked(LISTA.get(position).isSelected());

        //

        return convertView;
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
