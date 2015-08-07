package com.example.athirasurendran.calandarexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;


public class monthWiseList extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHandler db = new DatabaseHandler(this);
    ArrayList<String> events= new ArrayList<>();
    String mon,yr;
    Spinner spinnerm,spinnery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_wise_list);

        spinnerm = (Spinner) findViewById(R.id.monthSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerm.setAdapter(adapter1);

        spinnerm.setOnItemSelectedListener(this);






       spinnery = (Spinner) findViewById(R.id.yearSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnery.setAdapter(adapter2);










    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_month_wise_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        mon =  spinnerm.getSelectedItem().toString();
        yr = spinnery.getSelectedItem().toString();

        yr = yr +"-"+mon;
        Log.d(yr,"yr");
        events = db.monthWiseList(yr);
        ArrayAdapter<String> month_wise_listAdapter;
        month_wise_listAdapter = new ArrayAdapter<String>(this,R.layout.monthwise,R.id.month,events);
        ListView listview=(ListView)findViewById(R.id.listView);
        listview.setAdapter(month_wise_listAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
