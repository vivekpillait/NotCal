package com.example.athirasurendran.calandarexample;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by HP on 01-04-2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "Q76nbGbdvkCPdVcS04W5XgnNeECQM1OdmVmVKb2F", "bUfPsjsiLwSAuuJo55RZenbYejYZ2roMQodKCdv7");
        ParseInstallation.getCurrentInstallation().saveInBackground();


      //  mInstance = this;
    }
}
