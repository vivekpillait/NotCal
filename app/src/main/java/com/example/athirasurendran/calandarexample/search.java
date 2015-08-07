package com.example.athirasurendran.calandarexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;


public class search extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final TextView view1 =(TextView)findViewById(R.id.textView2);
        final DatabaseHandler db = new DatabaseHandler(this);

        final Button button = (Button) findViewById(R.id.b1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                local l = new local();
                EditText medit=(EditText)findViewById(R.id.edit);
                l = db.searchEvents(medit.getText().toString());
                if(l==null)
                view1.setText("No Event Found");
                else
                view1.setText(l.date);

            }
        });

    }


}
