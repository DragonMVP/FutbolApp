package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nivx1 on 09/02/2015.
 */
public class MyListViewAdapter extends BaseAdapter {
    private List LISTA;
    private Context Contexto;
    private Activity Actividad;

    public MyListViewAdapter(List LISTA, Context contexto, Activity actividad) {
        this.LISTA = LISTA;
        Contexto = contexto;
        Actividad = actividad;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView==null){
            view = new View(Contexto);
            LayoutInflater inflater=Actividad.getLayoutInflater();
            view=inflater.inflate(R.layout.mylistview, parent, false);
        }else{
            view = (View)convertView;
        }

        String[] txtList = LISTA.get(position).toString().split("|");
        TextView maintxt = (TextView)view.findViewById(R.id.maintxt);
        TextView optionaltxt = (TextView)view.findViewById(R.id.optionaltxt);
        optionaltxt.setText("");
        if (txtList.length == 1) {
            maintxt.setText(txtList[0]);
            optionaltxt.setText(txtList[0]);
        }
        else{
            maintxt.setText(txtList[0]);
            optionaltxt.setText(txtList[1]);
        }

        return view;
    }
}
