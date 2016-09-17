package com.example.ian.timecardcapstone;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Locale;

import hirondelle.date4j.DateTime;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ClockInOutService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_CLOCK_IN = "com.example.ian.timecardcapstone.action.FOO";
    private static final String ACTION_CLOCK_OUT = "com.example.ian.timecardcapstone.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_CUR_CLOCKIN_DATETIME = "com.example.ian.timecardcapstone.extra.PARAM1";
    private static final String EXTRA_CLOCKED_BOOL = "com.example.ian.timecardcapstone.extra.PARAM2";

    private static final String LOG_TAG = ClockInOutService.class.getSimpleName();
    private static final int ONGOING_NOTIFICATION_ID = 1;
    private static boolean CLOCKED_IN = false;
    private static DateTime CLOCKED_IN_DATETIME;
    private static DateTime CLOCKED_OUT_DATETIME;


    public ClockInOutService() {
        super("ClockInOutService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startClockIn(Context context, DateTime clockedInTime, boolean clockedInBool) {
        Intent intent = new Intent(context, ClockInOutService.class);
        intent.setAction(ACTION_CLOCK_IN);
        intent.putExtra(EXTRA_CUR_CLOCKIN_DATETIME, clockedInTime);
        intent.putExtra(EXTRA_CLOCKED_BOOL, clockedInBool);
        CLOCKED_IN = clockedInBool;
        CLOCKED_IN_DATETIME = clockedInTime;
        Log.w(LOG_TAG, "CLOCKED IN TIME: " + CLOCKED_IN_DATETIME);

        context.startService(intent);

    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startClockOut(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ClockInOutService.class);
        intent.setAction(ACTION_CLOCK_OUT);
        intent.putExtra(EXTRA_CUR_CLOCKIN_DATETIME, param1);
        intent.putExtra(EXTRA_CLOCKED_BOOL, param2);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_CLOCK_IN.equals(action)) {
                final DateTime currentClockInDateTime = (DateTime) intent.getSerializableExtra(EXTRA_CUR_CLOCKIN_DATETIME);
                assert currentClockInDateTime != null;
                final boolean CLOCKED_IN_BOOL = intent.getBooleanExtra(EXTRA_CLOCKED_BOOL, false);
                handleClockingIn(currentClockInDateTime, CLOCKED_IN_BOOL);


            } else if (ACTION_CLOCK_OUT.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_CUR_CLOCKIN_DATETIME);
                final String param2 = intent.getStringExtra(EXTRA_CLOCKED_BOOL);
                handleClockingOut(param1, param2);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, Main2Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 ,intent, 0);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.perm_group_system_clock)
                .setContentInfo("testing")
                .setContentText("foreground")
                .setContentIntent(pendingIntent).build();
        startForeground(4, notification);
    }

    /**
     *
     *
     * Handle clocking in, setting the service to the foreground
     * and create a persistent notification that shows user's clock in time, if possible update in realtime,
     * and provides a notification action for clocking out.
     *
     * POSSIBLE: show amount of gross money earned thus far in notification or main app UI.
     */
    private void handleClockingIn(DateTime clockedInDateTime, boolean clockedInBoolean) {
        Context context = this;
        Notification ongoingNotif;
        // TODO: Handle clocking in,

        Log.e(LOG_TAG, "in handle clocking in");
        //TODO: setting the service to the foreground and create a persistant notification
        String formattedClockInTime = clockedInDateTime.format("MM-DD-YYY hh:mm", Locale.getDefault());
        Intent clockedInIntent = new Intent(context, Main2Activity.class);
        PendingIntent pendingClockedIntent = PendingIntent.getActivity(context, 0,
                clockedInIntent, 0);
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.perm_group_system_clock)
                .setContentTitle("clocked in")
                .setContentText(formattedClockInTime)
                .setContentIntent(pendingClockedIntent);
        ongoingNotif = builder.build();
        this.startForeground(2, ongoingNotif);
        /*NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, ongoingNotif);*/

    }


    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleClockingOut(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
