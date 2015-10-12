package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        ImageView imgV = (ImageView)view.findViewById(R.id.item_image);


        if (LISTA.get(position) instanceof  Jugador){
            Bitmap thumbnail;
            if (((Jugador)LISTA.get(position)).getPICTURE() == null){
                thumbnail = BitmapFactory.decodeResource(Actividad.getResources(), R.drawable.defaultuser);
            }else {
                thumbnail = BitmapFactory.decodeByteArray(((Jugador) LISTA.get(position)).getPICTURE(), 0, ((Jugador) LISTA.get(position)).getPICTURE().length);

            }
            imgV.setImageBitmap(thumbnail);

        }

        String[] txtList = LISTA.get(position).toString().split(",");
        TextView maintxt = (TextView)view.findViewById(R.id.maintxt);
        TextView optionaltxt = (TextView)view.findViewById(R.id.optionaltxt);
        optionaltxt.setText("");
        if (txtList.length == 1) {
            maintxt.setText(txtList[0]);
           // optionaltxt.setText(txtList[0]);
        }
        else{
            maintxt.setText(txtList[0]);
            optionaltxt.setText(txtList[1]);
        }

        return view;
    }

    public void setNewLista(List newL){
        this.LISTA = newL;
    }
}
