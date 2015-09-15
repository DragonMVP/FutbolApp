package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

/**
 * Created by nivx1 on 09/04/2015.
 */
public class ButtonGridView extends BaseAdapter {
    private List<Accion> LISTA;

    private Activity ACTIVIDAD;
    private Context CONTEXTO;

    public ButtonGridView(List<Accion> LISTA,Activity ACTIVIDAD, Context CONTEXTO) {
        this.LISTA = LISTA;
        this.ACTIVIDAD = ACTIVIDAD;
        this.CONTEXTO = CONTEXTO;
    }

    public List<Accion> getLISTA() {
        return LISTA;
    }

    public void setLISTA(List<Accion> LISTA) {
        this.LISTA = LISTA;
    }


    @Override
    public int getCount() {
        return LISTA.size();
    }

    @Override
    public Object getItem(int position) {
       return LISTA.get(position);
    }

    @Override
    public long getItemId(int position) {
       return  position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View grid;

        if(convertView==null){
            grid = new View(CONTEXTO);
            LayoutInflater inflater=ACTIVIDAD.getLayoutInflater();
            grid=inflater.inflate(R.layout.gridview_partidobutton, parent, false);
        }else{
            grid = (View)convertView;
        }

        Button btnAccion = (Button)grid.findViewById(R.id.btnAccion);
        StringBuilder SS = new StringBuilder();
        SS.setLength(12);
        SS.append(LISTA.get(position).getAbreviacionAccion());

        btnAccion.setText(SS.toString());
        btnAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PartidoCanchaActivity. ACCION_PRINCIPAL = LISTA.get(position);
            }
        });


        return grid;
    }
}
