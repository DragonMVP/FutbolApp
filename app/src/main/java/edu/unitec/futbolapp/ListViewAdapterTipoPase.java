package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

/**
 * Created by nivx1 on 09/14/2015.
 */
public class ListViewAdapterTipoPase extends BaseAdapter {
    List<TipoPase> TIPO_PASE;
    Context CONTEXTO;
    Activity ACTIVIDAD;

    public ListViewAdapterTipoPase(List<TipoPase> TIPO_PASE, Context CONTEXTO, Activity ACTIVIDAD) {
        this.TIPO_PASE = TIPO_PASE;
        this.CONTEXTO = CONTEXTO;
        this.ACTIVIDAD = ACTIVIDAD;
    }

    @Override
    public int getCount() {
        return TIPO_PASE.size();
    }

    @Override
    public Object getItem(int position) {
        return TIPO_PASE.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        SS.append(TIPO_PASE.get(position).getName());

        btnAccion.setText(SS.toString());
        btnAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PartidoCanchaActivity.TIPO_PASE = TIPO_PASE.get(position);
            }
        });


        return grid;
    }
}
