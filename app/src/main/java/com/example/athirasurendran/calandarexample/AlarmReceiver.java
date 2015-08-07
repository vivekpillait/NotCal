package com.example.athirasurendran.calandarexample;

/**
 * Created by HP on 09-03-2015.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;
import android.os.Bundle;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    static Context context;

    local l = new local();
    @Override
    public void onReceive(Context context, Intent intent) {
        final DatabaseHandler db = new DatabaseHandler(context);
        AlarmReceiver.context=context;
        Calendar tom = Calendar.getInstance();
        tom.add(Calendar.DATE, 1);
        Date tomorrow;
        tomorrow=tom.getTime();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(tomorrow);
        l = db.searchDate(fDate);
        if(l!=null)
        {
            NotificationCompat.Builder m = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.calendar_not).setContentTitle("Event").setContentText(l.events).setAutoCancel(true);
            Intent openHomePageActivity = new Intent(context,MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(openHomePageActivity);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            m.setContentIntent(resultPendingIntent);
            NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //mNotificationManager.notify(0, mBuilder.build());
            /*********** Create notification ***********/
            int mID = 123;
            mManager.notify(mID, m.build());
        }

    }




}