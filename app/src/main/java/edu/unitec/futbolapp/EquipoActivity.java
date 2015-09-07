package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by daemonaeon on 09/05/2015.
 */
public class EquipoActivity extends Activity {

    List<Equipo> Equipos;
    ListView lista;
    int IDCLUB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);
        MyDatabaseHandler db = new MyDatabaseHandler(getBaseContext());
        IDCLUB = getIntent().getIntExtra("CLUB", 0);
        Equipos = db.getAllEquipos(IDCLUB);
        db.close();

        lista = (ListView)findViewById(R.id.listEquipo);
        lista.setAdapter(new MyListViewAdapter(Equipos, getBaseContext(), this));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(lista.getItemAtPosition(position).toString());
                Intent intent = new Intent(view.getContext(), JugadorActivity.class);
                //intent.putExtra("CLUB", lista.getItemAtPosition(position).toString());
                MyDatabaseHandler db = new MyDatabaseHandler(view.getContext());
                int EQUIPOID = db.getEquipoID(lista.getItemAtPosition(position).toString());
                db.close();
                intent.putExtra("EQUIPO", EQUIPOID);
                System.out.println("--------------------------"+id);
                startActivity(intent);
                Toast.makeText(view.getContext(), "TEST", Toast.LENGTH_SHORT);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.club_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.addClub){
            addEquipoDialog addEquipo = new addEquipoDialog(Equipos,(MyListViewAdapter)lista.getAdapter(),IDCLUB);
            addEquipo.show(getFragmentManager(),"addEquipoDialog");
            return true;
        }else {
            System.out.println("-----------------------------------------"+id);
            return super.onOptionsItemSelected(item);
        }
    }
}
