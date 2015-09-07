package edu.unitec.futbolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nivx1 on 09/02/2015.
 */
public class ClubActivity extends Activity {
    List<Club> CLUBS;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_layout);
        MyDatabaseHandler db = new MyDatabaseHandler(getBaseContext());
        CLUBS = db.getAllClubs();
        db.close();

        lista = (ListView)findViewById(R.id.listClub);
        lista.setAdapter(new MyListViewAdapter(CLUBS,getBaseContext(),this));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), EquipoActivity.class);
                //intent.putExtra("CLUB", lista.getItemAtPosition(position).toString());

                int CLUBID = CLUBS.get(position).getIdClub();
                intent.putExtra("CLUB", CLUBID);
                startActivity(intent);
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
            addClubDialog addClub = new addClubDialog(CLUBS,(MyListViewAdapter)lista.getAdapter());
            addClub.show(getFragmentManager(),"addClubDialog");
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}
