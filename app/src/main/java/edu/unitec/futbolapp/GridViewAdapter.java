package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by nivx1 on 09/01/2015.
 */
public class GridViewAdapter extends BaseAdapter {
    final int idFotos[] = {
            R.drawable.partido,
            R.drawable.configuration,
            R.drawable.sync,
            R.drawable.logout

    };

    final String textButton[] ={
        "Partido",
        "Configuracion",
            "Sincronizar Datos",
            "Cerrar Sesion"
    };

    private Context Contexto;
    private Activity Actividad;
    public GridViewAdapter(Context context,Activity activity ) {
        Contexto = context;
        Actividad = activity;
    }

    @Override
    public int getCount() {
       return idFotos.length;
    }

    @Override
    public Object getItem(int position) {
        return idFotos[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;

        if(convertView==null){
            grid = new View(Contexto);
            LayoutInflater inflater=Actividad.getLayoutInflater();
            grid=inflater.inflate(R.layout.gridview_menuprincipal, parent, false);
        }else{
            grid = (View)convertView;
        }

        final ImageButton imgButton = (ImageButton) grid.findViewById(R.id.imgButton);
        imgButton.setImageResource(idFotos[position]);
        TextView txtDesc = (TextView)grid.findViewById(R.id.txtDesc);
        txtDesc.setText(textButton[position]);
        imgButton.setTag(Integer.valueOf(position));

        try {
            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(Integer.valueOf(imgButton.getTag().toString())){
                        case 0:
                            break;
                        case 1:
                            Intent intent= new Intent(Contexto, ConfiguracionActivity.class);
                            Contexto.startActivity(intent);
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return grid;
    }

}
