package com.example.athirasurendran.calandarexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class settings extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Switch s = (Switch)findViewById(R.id.switch1);
        s.setChecked(true);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    Toast.makeText(getApplicationContext(),"Notifications On",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Notifications Off",Toast.LENGTH_SHORT).show();
            }
        });

    }
}

