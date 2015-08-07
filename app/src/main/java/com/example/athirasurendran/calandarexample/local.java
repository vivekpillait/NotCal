package com.example.athirasurendran.calandarexample;

/**
 * Created by HP on 09-03-2015.
 */

public class local {

      //private variables
        int _id;
    String date;
    String events;

            // Empty constructor
            public local(){
        }
    // constructor
           public local(int id, String name, String _phone_number){
        this._id = id;
        this.date = date;
        this.events = events;
        }

   // constructor
            public local(String date, String events){
        this.date = date;
        this.events = events;
        }
    // getting ID
            public int getID(){
        return this._id;
        }

   // setting id
            public void setID(int id){
        this._id = id;
        }

            // getting date
            public String getName(){
        return this.date;
        }

            // setting date
            public void setName(String name){
        this.date = name;
        }

       // getting events
            public String getPhoneNumber(){
        return this.events;
        }

    // setting events
            public void setPhoneNumber(String phone_number){
        this.events = phone_number;
        }
}