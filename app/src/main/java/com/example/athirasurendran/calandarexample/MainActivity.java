package com.example.athirasurendran.calandarexample;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.parse.Parse;
import com.telerik.android.common.Function;
import com.telerik.android.common.Util;
import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.decorations.CellDecorator;
import com.telerik.widget.calendar.decorations.CircularCellDecorator;
import com.telerik.widget.calendar.decorations.SquareCellDecorator;
import com.telerik.widget.calendar.events.Event;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    RadCalendarView calendarve;
    Context context;
    String s;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        RadCalendarView calendarView = (RadCalendarView)findViewById(R.id.calendarView);
        calendarve=calendarView;
        calendarView.setSelectionMode(CalendarSelectionMode.Single);
        CellDecorator decorator = new CircularCellDecorator(calendarView);
        decorator.setColor(Color.parseColor("#ed742c"));
        decorator.setStrokeWidth(Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 2));
        decorator.setScale(.75f);
        decorator.setStroked(true);
        calendarView.setCellDecorator(decorator);

        final Calendar cal = Calendar.getInstance();

        calendarView.setDateToColor(new Function<Long, Integer>() {
            @Override
            public Integer apply(Long aLong) {
                cal.setTimeInMillis(aLong);
                if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    return Color.RED;
                }
                return null;
            }
        });

        calendarView.setOnSelectedDatesChangedListener(new RadCalendarView.
                OnSelectedDatesChangedListener() {
            @Override
            public void onSelectedDatesChanged(RadCalendarView.SelectionContext context) {
                Toast.makeText(getApplicationContext(),
                        String.format("%tF", context.newSelection().get(0)),
                        Toast.LENGTH_SHORT).show();
                s=String.format("%tF", context.newSelection().get(0));
                Intent i = new Intent(getApplicationContext(), event.class);
                i.putExtra("Date",s);
                startActivity(i);

            }
        });

        if(!isOnline()) {

        Log.d("Reading(For Markers): ", "Reading databse..(For Markers)");
        DatabaseHandler dba=new DatabaseHandler(getApplicationContext());
        List<local> contacts = dba.getAlllocals();
        List<Event> events = new ArrayList<Event>();


        for (local cn : contacts) {
            String log = "Date: " + cn.getName() + " ,Event: " + cn.getPhoneNumber();
            // Writing locals to log
            Log.d("Calender Log",log);
            String d=cn.getName();
            {
                int y=0,m=0, da=0;

                for(int k=0;k<10;k++)
                {
                    if (k < 4)
                        y = y*10 + Integer.parseInt(Character.toString(d.charAt(k)));
                    if (k > 4&&k<7)
                        m= m*10+Integer.parseInt(Character.toString(d.charAt(k)));
                    if (k > 7&&k<10)
                        da=da*10+Integer.parseInt(Character.toString(d.charAt(k)));

                }

                Calendar calendar = Calendar.getInstance();
                calendar.set(y,m-1,da);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                long eventStart = calendar.getTimeInMillis();

                calendar.add(Calendar.HOUR, 23);
                long eventEnd = calendar.getTimeInMillis();

                Event event = new Event("", eventStart, eventEnd);


                //    event.setEventColor(18);
                events.add(event);

            }


        }
        calendarView.getEventAdapter().setEvents(events);

            // get a Calendar object with current time
            Calendar cale = Calendar.getInstance();
            // add 5 minutes to the calendar object
            cale.add(Calendar.SECOND, 4);
            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            // In reality, you would want to have a static variable for the request code instead of 192837
            PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // Get the AlarmManager service

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, cale.getTimeInMillis(), sender);

            am.setRepeating(AlarmManager.RTC_WAKEUP, cale.getTimeInMillis(), 24*60*60*1000, sender);
        }

        else
        new read().execute();

    }


    class read extends AsyncTask<Void,Void,String>
    {        DatabaseHandler db = new DatabaseHandler(context);
        List<Event> events = new ArrayList<Event>();

        String e,d;
        private ProgressDialog pdia;


        @Override
        protected void onPreExecute()
        {
            if(isOnline()) {
                pdia = new ProgressDialog(context);
                pdia.setMessage("Syncing Data...");
                pdia.show();
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                sqLiteDatabase.delete("events", null, null);
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdia.dismiss();

            // get a Calendar object with current time
            Calendar cal = Calendar.getInstance();
            // add 5 minutes to the calendar object
            cal.add(Calendar.SECOND, 4);
            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            // In reality, you would want to have a static variable for the request code instead of 192837
            PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // Get the AlarmManager service

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
            am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24*60*60*1000, sender);

            if(isOnline()) {
            Log.d("Reading: ", "Reading databse..");
            List<local> contacts = db.getAlllocals();

            for (local cn : contacts) {
                String log = "Date: " + cn.getName() + " ,Event: " + cn.getPhoneNumber();
                // Writing locals to log
                Log.d("Calender Log", log);
                String d=cn.getName();
                {
                    int y=0,m=0, da=0;

                    for(int k=0;k<10;k++)
                    {
                        if (k < 4)
                            y = y*10 + Integer.parseInt(Character.toString(d.charAt(k)));
                        if (k > 4&&k<7)
                            m= m*10+Integer.parseInt(Character.toString(d.charAt(k)));
                        if (k > 7&&k<10)
                            da=da*10+Integer.parseInt(Character.toString(d.charAt(k)));

                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(y,m-1,da);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    long eventStart = calendar.getTimeInMillis();

                    calendar.add(Calendar.HOUR, 23);
                    long eventEnd = calendar.getTimeInMillis();

                    Event event = new Event("", eventStart, eventEnd);



                    //    event.setEventColor(18);
                    events.add(event);

                }


            }

            calendarve.getEventAdapter().setEvents(events);
        }}

        @Override
        protected String doInBackground(Void... params) {

            if(isOnline()) {
                localparse a = new localparse();
                try {
                    JSONArray j = a.getJSONfromURL("http://notification.host56.com/local.php");
                    for (int i = 0; i < j.length(); i++) {
                        e = j.getJSONObject(i).getString("event");
                        d = j.getJSONObject(i).getString("date");
                        Log.d("Insert: ", "Inserting .." + d + " " + e);
                        db.addlocal(new local(d, e));
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            return null;
        }
    }

    public boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent i=new Intent(getApplicationContext(),settings.class);
            startActivity(i);
            return true;
        }
        else if(id==R.id.action_search){
            Intent i=new Intent(getApplicationContext(),search.class);
            startActivity(i);
            return true;
        }
        else if(id==R.id.action_sync)
        {
            if(isOnline())
                new read().execute();
            else
                Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();

        }
        else if(id==R.id.action_monthlist)
        {
            Intent i=new Intent(getApplicationContext(),monthWiseList.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

