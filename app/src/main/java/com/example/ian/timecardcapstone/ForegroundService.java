package com.example.ian.timecardcapstone;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Locale;

import hirondelle.date4j.DateTime;

public class ForegroundService extends Service {
    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DateTime dateTime = (DateTime) intent.getSerializableExtra("current_datetime");
        boolean clockedInBool = intent.getBooleanExtra("clocked_bool", false);

        String formattedClockIntime = dateTime.format("MM-DD-YYYY hh:mm", Locale.getDefault());
        Intent clockedInIntent = new Intent(this, Main2Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, clockedInIntent, 0);

        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.perm_group_system_clock)
                .setContentTitle("clocked in")
                .setContentText(formattedClockIntime)
                .setContentIntent(pendingIntent);
        Notification onGoingNotif = builder.build();
        this.startForeground(2, onGoingNotif);


        return super.onStartCommand(intent, flags, startId);
    }
}
