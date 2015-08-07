package com.example.athirasurendran.calandarexample;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "eventsList";

    // locals table name
    private static final String TABLE_EVENTS = "events";

    // locals Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_EVENTS = "events";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_EVENTS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //monthWiseEvents

    public ArrayList<String> monthWiseList(String month) {
        ArrayList<String> eventList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE "+ KEY_DATE +" LIKE '"+ month + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding events to list
                eventList.add(cursor.getString(1)+"          "+cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // return contact list
        return eventList;
    }

    // Adding new events
    void addlocal(local contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, contact.getName()); // local Name
        values.put(KEY_EVENTS, contact.getPhoneNumber()); // local Phone

        // Inserting Row
        db.insert(TABLE_EVENTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single local
    local getlocal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_ID,
                        KEY_DATE, KEY_EVENTS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        local contact = new local(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // Getting All locals
    public List<local> getAlllocals() {
        List<local> contactList = new ArrayList<local>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                local contact = new local();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updatelocal(local contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, contact.getName());
        values.put(KEY_EVENTS, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_EVENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deletelocal(local contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getlocalsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Searching db

    public local searchEvents(String event)
    {
        String searchQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE EVENTS = '" + event +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);
        local d = new local();
        cursor.moveToFirst();
        if(cursor.getCount()!=0) {
            d.setID(Integer.parseInt(cursor.getString(0)));
            d.setName(cursor.getString(1));
            d.setPhoneNumber(cursor.getString(2));
            return d;
        }
        return  null;
    }
    public local searchDate(String date)
    {
        String searchQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE DATE = '" + date +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);
        local d = new local();
        cursor.moveToFirst();
        if(cursor.getCount()!=0) {
            d.setID(Integer.parseInt(cursor.getString(0)));
            d.setName(cursor.getString(1));
            d.setPhoneNumber(cursor.getString(2));
            return  d;
        }

        return  null;
    }



}
