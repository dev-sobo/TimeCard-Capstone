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

public class ForegroundService extends Service {

    // TODO: Refactor this class in the same style as the IntentService.

    private static final String LOG_TAG = ForegroundService.class.getSimpleName();
    private static final int ONGOING_NOTIFICATION_ID = 2;
  //  private static DateTime CLOCKED_OUT_DATETIME;
    private static boolean CLOCKED_BOOL;
    private Context mContext;
    private static final String ACTION_CLOCK_IN = "com.example.ian.timecardcapstone.action.CLOCKIN";
    private static final String ACTION_CLOCK_OUT = "com.example.ian.timecardcapstone.action.CLOCKOUT";
    private static final String EXTRA_CUR_CLOCKIN_DATETIME = "com.example.ian.timecardcapstone.extra.CLOCKINTIME";
    private static final String EXTRA_CLOCKED_BOOL_ID = "com.example.ian.timecardcapstone.extra.CLOCKED_IN_BOOL";
    private static final String EXTRA_CUR_CLOCKOUT_DATETIME = "com.example.ian.timecardcapstone.extra.CLOCKOUTTIME";

    public ForegroundService() {
    }

    public static void startClockIn(Context context, DateTime clockedInTime, boolean clockInBool) {
        Intent intent = new Intent(context, ForegroundService.class);
        intent.setAction(ACTION_CLOCK_IN);
        intent.putExtra(EXTRA_CUR_CLOCKIN_DATETIME, clockedInTime);
        intent.putExtra(EXTRA_CLOCKED_BOOL_ID, clockInBool);
        CLOCKED_BOOL = clockInBool;
        context.startService(intent);
    }

    public static void startClockOut(Context context, DateTime clockedOutTime, boolean clockOutBool) {
        Intent intent = new Intent(context, ForegroundService.class);
        intent.setAction(ACTION_CLOCK_OUT);
        intent.putExtra(EXTRA_CUR_CLOCKOUT_DATETIME, clockedOutTime);
        intent.putExtra(EXTRA_CLOCKED_BOOL_ID, clockOutBool);
        CLOCKED_BOOL = clockOutBool;
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
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.clockin_uri), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Uri clockInUri;
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_CLOCK_IN.equals(action)) {
                final DateTime currentClockInDateTime = (DateTime) intent.getSerializableExtra(EXTRA_CUR_CLOCKIN_DATETIME);
                //final boolean clockedInBool = intent.getBooleanExtra(EXTRA_CLOCKED_BOOL_ID, false);
                String clockInString = clockIn(currentClockInDateTime).toString();
                editor.putString(getString(R.string.clockin_uri_sharedprefkey),
                        clockInString);
                editor.commit();
            } else if (ACTION_CLOCK_OUT.equals(action)) {
                final DateTime currentClockOutDateTime = (DateTime) intent.getSerializableExtra(EXTRA_CUR_CLOCKOUT_DATETIME);
                clockInUri = Uri.parse(sharedPreferences.getString(getString(R.string.clockin_uri_sharedprefkey), null));
                clockOut(clockInUri, currentClockOutDateTime);
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
     * @param clockInTime The time in which the user clocked in at
     * @return The URI at which the clocked in data was inserted in
     */
    public Uri clockIn(DateTime clockInTime) {
        if (CLOCKED_BOOL) {
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
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            Float hourlyPayFloat = 11.25f;

            try {
                hourlyPayFloat = Float.valueOf(sharedPreferences.getString(mContext.getResources().getString(R.string.hourlyPay), "11.25"));
            } catch (NumberFormatException exception) {
                Log.e(LOG_TAG, exception.getMessage());
                Toast.makeText(mContext, "Hourly pay is in invalid format", Toast.LENGTH_SHORT).show();
            }

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
            return null;
        }
    }

    /**
     * inserts:
     * the ending System Unix time
     * Then, a cursor is obtained using the URI, which contains the start and end time, along with the hourly pay.
     * Number of hours worked is then figured out using the cursor mentioned above.
     * These number of hours worked are then inserted into the same row, along with the calculated gross pay.
     *
     * @param clockInUri    the URI that was originally used to clock in
     * @param clockOutTime  The time at which the user clocks out
     * @return number of columns that were updated.
     */
    public int clockOut(Uri clockInUri, DateTime clockOutTime) {
        ShiftContentValues clockOutValues = new ShiftContentValues();

        clockOutValues.putEndTimeHhmm(clockOutTime.format("hh:mm"));
        long unixTime = (System.currentTimeMillis() / 1000);
        clockOutValues.putEndTimeUnix((int) unixTime);
        // TODO: calculate number of hours worked and gross pay based on the hourly pay

        Log.i(LOG_TAG, "CLOCKED IN CONTENT URI: " + clockInUri);
        mContext.getContentResolver().update(clockInUri, clockOutValues.values(), null, null);

        // TODO: REDO THIS CODE SO ITS CLEANER AND NOT A SLOPPY PIECE OF CRAP
        Cursor clockedOutCursor = mContext.getContentResolver().query(clockInUri,
                new String[]{ShiftColumns.START_TIME_UNIX, ShiftColumns.END_TIME_UNIX, ShiftColumns.HOURLY_PAY}, null, null, null);
        float[] hoursWorkedAndGrossPay = numOfHoursWorked(clockedOutCursor);
        Log.i(LOG_TAG, "NUMBER OF HOURS REPORTED WORKED FROM CALLED METHOD: " + hoursWorkedAndGrossPay[0] +
                "GROSS PAY IN ARRAY: " + hoursWorkedAndGrossPay[1] + " AND QURIED CURSOR: " +
                DatabaseUtils.dumpCursorToString(clockedOutCursor));
        ShiftContentValues secondClockOutValues = new ShiftContentValues();
        secondClockOutValues.putNumHrsShift(hoursWorkedAndGrossPay[0]);
        secondClockOutValues.putGrossPay(hoursWorkedAndGrossPay[1]);
        // TODO: have this react to the shared preferences boolean
        this.stopForeground(true);

        return mContext.getContentResolver().update(clockInUri, secondClockOutValues.values(), null, null);
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
