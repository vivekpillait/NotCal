package com.example.athirasurendran.calandarexample;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.athirasurendran.calandarexample.MainActivity;
import com.example.athirasurendran.calandarexample.R;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Athira Surendran on 3/1/2015.
 */
public class NotificationAlert extends Activity {
    private static final int NOTIFY_ME_ID = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new read().execute();
    }
    public class read extends AsyncTask<JSONArray,String,String>
    {
        String s;
        private ProgressDialog pdia;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(NotificationAlert.this);
            pdia.setMessage("Loading Data...");
            pdia.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdia.dismiss();
            if(s!=null)
            {
                /*
                // get a Calendar object with current time
                Calendar cal = Calendar.getInstance();
                // add 5 seconds to the calendar object
                cal.add(Calendar.SECOND,2);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                // In reality, you would want to have a static variable for the request code instead of 192837
                PendingIntent sender = PendingIntent.getBroadcast(NotificationAlert.this, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // Get the AlarmManager service
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
                */

                NotificationCompat.Builder m = new NotificationCompat.Builder(NotificationAlert.this).setSmallIcon(R.drawable.calendar_not).setContentTitle("Event").setContentText(s).setAutoCancel(true);
                Intent openHomePageActivity = new Intent(NotificationAlert.this,MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(NotificationAlert.this);
                stackBuilder.addNextIntent(openHomePageActivity);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                m.setContentIntent(resultPendingIntent);
                NotificationManager mManager = (NotificationManager) NotificationAlert.this.getSystemService(Context.NOTIFICATION_SERVICE);
                //mNotificationManager.notify(0, mBuilder.build());
                /*********** Create notification ***********/
                int mID = 123;
                mManager.notify(mID, m.build());
            }
        }

        @Override
        protected String doInBackground(JSONArray... params) {

            database a=new database();
            try {
                Calendar tom = Calendar.getInstance();
                tom.add(Calendar.DATE, 1);
                Date tomorrow;
                tomorrow=tom.getTime();
                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(tomorrow);
                JSONArray j = a.getJSONfromURL("http://notification.host56.com/connect.php",fDate);
                s=j.getJSONObject(0).getString("event");

                return s;
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }
    }
}

