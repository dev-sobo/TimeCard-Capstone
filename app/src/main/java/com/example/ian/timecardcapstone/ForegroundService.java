package com.example.ian.timecardcapstone;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;
import com.example.ian.timecardcapstone.provider.shift.ShiftContentValues;

import java.util.Locale;

import hirondelle.date4j.DateTime;

/** This is where clocking in and clocking out is handled.
 user clocks in, the system records the current time and date, inserts it into a row using the
 databasehandler class. it inserts:
 * Current day
 * Current Time in hh:mm format
 * Current Time in UNIX format
 * Current month
 * Current day of week
 * Current year
 * Hourly Pay
 into the row.
 Then, a row URI is returned. This URI is used to clock out the user, and inserts the following data into
 the same row:

 *  Upon clocking in, the UI should change to a view
 *  showing the time clocked in,
 *  how many hours worked in real time,
 *  as well as how much money grossed.
 *
 *  POSSIBLE: implement the logic to grab the RosterApps data to compare the current shift worked
 *  with what is scheduled on their RosterApps, and if it's a supervisor shift or not.
 *  Also could implement the logic that any time worked over the RosterApps-scheduled/reported
 *  shift is eligble for overtime,
 *  as well as reminding the user to be extended on their rosterapps.
 *
 */
public class ForegroundService extends Service {

    // TODO: Refactor this class in the same style as the IntentService.

    private static final String LOG_TAG = ForegroundService.class.getSimpleName();
    private static final int ONGOING_NOTIFICATION_ID = 2;
  //  private static DateTime CLOCKED_OUT_DATETIME;
    //private static boolean CLOCKED_BOOL;
    private Context mContext;
    private static final String ACTION_CLOCK_IN = "com.example.ian.timecardcapstone.action.CLOCKIN";
    private static final String ACTION_CLOCK_OUT = "com.example.ian.timecardcapstone.action.CLOCKOUT";
    private static final String EXTRA_CUR_CLOCKIN_DATETIME = "com.example.ian.timecardcapstone.extra.CLOCKINTIME";
    private static final String EXTRA_CLOCKED_BOOL_ID = "com.example.ian.timecardcapstone.extra.CLOCKED_IN_BOOL";
    private static final String EXTRA_CUR_CLOCKOUT_DATETIME = "com.example.ian.timecardcapstone.extra.CLOCKOUTTIME";

    public ForegroundService() {
    }

    public static void startClockIn(Context context, DateTime clockedInTime) {
        boolean clockInBool = false;
        Intent intent = new Intent(context, ForegroundService.class);
        intent.setAction(ACTION_CLOCK_IN);
        intent.putExtra(EXTRA_CUR_CLOCKIN_DATETIME, clockedInTime);
        intent.putExtra(EXTRA_CLOCKED_BOOL_ID, clockInBool);

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.clockin_sharedpref_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.clockin_bool_sharedprefkey),clockInBool);
        editor.commit();

        context.startService(intent);
    }

    public static void startClockOut(Context context, DateTime clockedOutTime) {
        boolean clockOutBool = true;
        Intent intent = new Intent(context, ForegroundService.class);
        intent.setAction(ACTION_CLOCK_OUT);
        intent.putExtra(EXTRA_CUR_CLOCKOUT_DATETIME, clockedOutTime);
        intent.putExtra(EXTRA_CLOCKED_BOOL_ID, clockOutBool);

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.clockin_sharedpref_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.clockin_bool_sharedprefkey), clockOutBool);
        editor.commit();

        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }

    /**
     *
     * @param intent The intent contains the dateTime at which the clocking in occured
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.clockin_sharedpref_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Uri clockInUri;
        boolean clockInBool;
        if (intent != null) {
            final String clockingIntentAction = intent.getAction();
            if (ACTION_CLOCK_IN.equals(clockingIntentAction)) {
                final DateTime currentClockInDateTime = (DateTime) intent.getSerializableExtra(EXTRA_CUR_CLOCKIN_DATETIME);
                clockInBool = intent.getBooleanExtra(EXTRA_CLOCKED_BOOL_ID, true);
                // Receives the URI from the clockIn operation, and saves it in the Shared Pref file for future use.
                String clockInString = clockIn(currentClockInDateTime,clockInBool).toString();
                editor.putString(getString(R.string.clockin_uri_sharedprefkey),
                        clockInString);
                editor.commit();
            } else if (ACTION_CLOCK_OUT.equals(clockingIntentAction)) {
                final DateTime currentClockOutDateTime = (DateTime) intent.getSerializableExtra(EXTRA_CUR_CLOCKOUT_DATETIME);
                clockInBool = intent.getBooleanExtra(EXTRA_CLOCKED_BOOL_ID, false);
                clockInUri = Uri.parse(sharedPreferences.getString(getString(R.string.clockin_uri_sharedprefkey), null));
                clockOut(clockInUri, currentClockOutDateTime, clockInBool);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    // TODO: Put all logic of accessing the database here.
    /**
     * Inserts the following information for clocking in:
     * Current day
     * Current Time in hh:mm format
     * Current Time in UNIX format
     * Current month
     * Current day of week
     * Current year
     * Hourly Pay
     *
     * This starts a foreground service and notification. When clocked in, the SharedPref clockedin boolean will be
     * made true.
     *
     * It will be ongoing until the user clocks out, which stops the foreground service.
     *
     *
     * @param clockInTime The time in which the user clocked in at
     * @return The URI at which the clocked in data was inserted in
     */
    public Uri clockIn(DateTime clockInTime,boolean userIsClockedIn) {
        SharedPreferences userSettingsPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Float hourlyPayFloat = 11.25f;
        try {
            hourlyPayFloat = Float.valueOf(userSettingsPreferences.getString(mContext.getResources().getString(R.string.hourlyPay), "11.25"));
        } catch (NumberFormatException exception) {
            Log.e(LOG_TAG, exception.getMessage());
            Toast.makeText(mContext, "Hourly pay is in invalid format", Toast.LENGTH_SHORT).show();
        }
        // TODO: RECONCILE THIS SHAREDPREF VARIABLE WITH THE MAIN2ACTIVITY PART

        if (!userIsClockedIn) {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string
                    .clockin_sharedpref_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(mContext.getString(R.string.clockin_bool_sharedprefkey), false);
            editor.commit();
            // Builds the ongoing notification to keep the service running in the foreground.
            String formattedClockIntime = clockInTime.format("MM-DD-YYYY hh:mm", Locale.getDefault());
            Intent clockedInIntent = new Intent(this, Main2Activity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, clockedInIntent, 0);
            Notification.Builder builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.perm_group_system_clock)
                    .setContentTitle("clocked in")
                    .setContentText(formattedClockIntime)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true);
            Notification onGoingNotif = builder.build();
            // TODO: Have this enabled based on the clocked in boolean
            this.startForeground(ONGOING_NOTIFICATION_ID, onGoingNotif);

            // Actually logs the current DateTime which the user clocks in at
            ShiftContentValues clockInValues = new ShiftContentValues();
            clockInValues.putDayOfMonth(clockInTime.getDay());
            clockInValues.putStartTimeHhmm(clockInTime.format("hh:mm"));
            clockInValues.putDayOfWeek(clockInTime.getWeekDay().toString());
            clockInValues.putMonthName(clockInTime.getMonth().toString());
            clockInValues.putYear(clockInTime.getYear());
            clockInValues.putHourlyPay(hourlyPayFloat);
            long unixTime = (System.currentTimeMillis() / 1000);
            clockInValues.putStartTimeUnix((int) unixTime);

            return mContext.getContentResolver().insert(ShiftColumns.CONTENT_URI, clockInValues.values());
        } else {
            throw new AssertionError();
        }
    }

    /**
     * inserts:
     * the ending System Unix time
     * Then, a cursor is obtained using the URI, which contains the start and end time, along with the hourly pay.
     * Number of hours worked is then figured out using the cursor mentioned above.
     * These number of hours worked are then inserted into the same row, along with the calculated gross pay.
     *
     * Stops the foreground service, and sets the SharedPref clockout variable as false.
     *
     * @param clockInUri    the URI that was originally used to clock in
     * @param clockOutTime  The time at which the user clocks out
     * @return number of columns that were updated.
     */
    public int clockOut(Uri clockInUri, DateTime clockOutTime, boolean clockedOutBool) {
        Log.d(LOG_TAG, "IN CLOCK OUT METHOD");
        SharedPreferences sharedPreferences = getSharedPreferences(this.getString(R.string.clockin_sharedpref_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.clockin_bool_sharedprefkey), clockedOutBool);
        editor.commit();

        ShiftContentValues clockOutValues = new ShiftContentValues();
        clockOutValues.putEndTimeHhmm(clockOutTime.format("hh:mm"));
        long unixTime = (System.currentTimeMillis() / 1000);
        clockOutValues.putEndTimeUnix((int) unixTime);

        Log.i(LOG_TAG, "CLOCKED IN CONTENT URI: " + clockInUri);
        mContext.getContentResolver().update(clockInUri, clockOutValues.values(), null, null);

        // TODO: REDO THIS CODE SO ITS CLEANER AND NOT A SLOPPY PIECE OF CRAP
        Cursor clockedOutCursor = mContext.getContentResolver().query(clockInUri,
                new String[]{ShiftColumns.START_TIME_UNIX, ShiftColumns.END_TIME_UNIX, ShiftColumns.HOURLY_PAY}, null, null, null);
        float[] hoursWorkedAndGrossPay = numOfHoursWorked(clockedOutCursor);
        /*Log.i(LOG_TAG, "NUMBER OF HOURS REPORTED WORKED FROM CALLED METHOD: " + hoursWorkedAndGrossPay[0] +
                "GROSS PAY IN ARRAY: " + hoursWorkedAndGrossPay[1] + " AND QURIED CURSOR: " +
                DatabaseUtils.dumpCursorToString(clockedOutCursor));*/

        clockOutValues.putNumHrsShift(hoursWorkedAndGrossPay[0]);
        clockOutValues.putGrossPay(hoursWorkedAndGrossPay[1]);


        this.stopForeground(true);

        return mContext.getContentResolver().update(clockInUri, clockOutValues.values(), null, null);
    }

    private float[] numOfHoursWorked(Cursor clockedOutCursor) {
        Log.i(LOG_TAG, "CURSOR BEING WORKED ON: " + DatabaseUtils.dumpCursorToString(clockedOutCursor));
        clockedOutCursor.moveToFirst();
        int clockedInUnixTime = clockedOutCursor.getInt(clockedOutCursor.getColumnIndexOrThrow(ShiftColumns.START_TIME_UNIX));
        int clockedOutUnixTime = clockedOutCursor.getInt(clockedOutCursor.getColumnIndexOrThrow(ShiftColumns.END_TIME_UNIX));
        float hourlyPay = clockedOutCursor.getFloat(clockedOutCursor.getColumnIndexOrThrow(ShiftColumns.HOURLY_PAY));

        float totalHoursWorked = ((clockedOutUnixTime - clockedInUnixTime) / 3600f);
        float calcGrossPay = (hourlyPay * totalHoursWorked);
        Log.i(LOG_TAG, "TOTAL HOURS WORKED: " + totalHoursWorked);


        return new float[]{totalHoursWorked, calcGrossPay};

    }
}
