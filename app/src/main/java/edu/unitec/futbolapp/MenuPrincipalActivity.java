package edu.unitec.futbolapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

/**
 * Created by nivx1 on 09/01/2015.
 */
public class MenuPrincipalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        GridView maingridView = (GridView) findViewById(R.id.maingirdView);
        maingridView.setAdapter(new GridViewAdapter(getBaseContext(),this));
        maingridView.setNumColumns(2);
        maingridView.setColumnWidth(this.getResources().getDisplayMetrics().widthPixels/2);

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
