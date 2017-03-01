package com.skyeng.skyengtest.core;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Calendar;


public class Server {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void registerUpdateFromServer(Context context) {
        Calendar cal = Calendar.getInstance();
        Intent activate = new Intent(context, UpdateReceiver.class);
        AlarmManager alarms;
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, activate, 0);
        alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        alarms.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);
    }
}
