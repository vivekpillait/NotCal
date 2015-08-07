package com.example.athirasurendran.calandarexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class event extends ActionBarActivity {
    TextView date,events,v;
    String str;
    final DatabaseHandler db = new DatabaseHandler(this);
    local l = new local();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        date = (TextView)findViewById(R.id.date);
        events = (TextView)findViewById(R.id.events);
        date.setText(getIntent().getStringExtra("Date"));
        str=getIntent().getStringExtra("Date");
        v=(TextView)findViewById(R.id.events1);
        l = db.searchDate(str);
        if(l==null)
            v.setText("<No Events>");
        else
            v.setText(l.events);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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


}
