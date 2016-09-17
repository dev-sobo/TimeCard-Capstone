package com.example.ian.timecardcapstone;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

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
        // TODO: Handle clocking in,


        //TODO: setting the service to the foreground and create a persistant notification
        NotificationCompat foregroundNotification;

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
