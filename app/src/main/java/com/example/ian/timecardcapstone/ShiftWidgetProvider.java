package com.example.ian.timecardcapstone;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;

import java.util.Locale;


public class ShiftWidgetProvider extends AppWidgetProvider {
    private final static String LOG_TAG = ShiftWidgetProvider.class.getSimpleName();
    public static String UPDATE_WIDGET = "updateWidget";
    public static String REMOTE_VIEWS_EXTRA = "remoteViewsExtra";
    public static String APP_WIDGET_IDS_EXTRA = "appWidgetIdsExtra";
    private Button refreshShift;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private String dayOfMonth;
    private String monthNameNumber;
    private String numOfHoursWorked;
    private String grossPay;
    private Cursor shift;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int length = appWidgetIds.length;


        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.shiftwidgetlayout);


            remoteViews = populateWidget(remoteViews, context);

            Intent intent = new Intent(context, ShiftWidgetProvider.class);
            intent.putExtra(REMOTE_VIEWS_EXTRA, remoteViews);
            intent.putExtra(APP_WIDGET_IDS_EXTRA, appWidgetIds);
            intent.setAction(UPDATE_WIDGET);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.refreshShiftButton, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
    }

    private RemoteViews populateWidget(RemoteViews remoteViews, Context context) {

        try {
            shift = context.getContentResolver().query(ShiftColumns.CONTENT_URI, new String[]{ShiftColumns.START_TIME_HHMM,
                            ShiftColumns.END_TIME_HHMM, ShiftColumns.DAY_OF_MONTH, ShiftColumns.DAY_OF_WEEK,
                            ShiftColumns.MONTH_NAME, ShiftColumns.NUM_HRS_SHIFT, ShiftColumns.GROSS_PAY}, null,
                    null, ShiftColumns._ID + " DESC");
            Log.e(LOG_TAG, "WIDGET CURSOR DUMP: " + DatabaseUtils.dumpCursorToString(shift));
        } catch (SQLException exception) {
            Log.e(LOG_TAG, "DATABASE ERROR: " + exception.getMessage());
        } catch (Exception error) {
            Log.e(LOG_TAG, "error: " + error.getMessage());
        }

        if (shift != null && shift.moveToFirst()) {
            shift.moveToFirst();
            Log.i(LOG_TAG, "boolean INSIDE IF: " + shift.moveToFirst());
            startTime = shift.getString(shift.getColumnIndex(ShiftColumns.START_TIME_HHMM));
            remoteViews.setTextViewText(R.id.startTime, "START TIME: " + startTime);

            endTime = shift.getString(shift.getColumnIndex(ShiftColumns.END_TIME_HHMM));
            remoteViews.setTextViewText(R.id.endTime, "END TIME: " + endTime);

            dayOfWeek = String.valueOf(shift.getInt(shift.getColumnIndex(ShiftColumns.DAY_OF_WEEK)));
            remoteViews.setTextViewText(R.id.dayOfWeek, "DAY OF WEEK: " + dayOfWeek);

            dayOfMonth = String.valueOf(shift.getInt(shift.getColumnIndex(ShiftColumns.DAY_OF_MONTH)));
            remoteViews.setTextViewText(R.id.dayOfMonth, "DATE OF MONTH: " + dayOfMonth);

            monthNameNumber = String.valueOf(shift.getInt(shift.getColumnIndex(ShiftColumns.MONTH_NAME)));
            remoteViews.setTextViewText(R.id.monthName, "MONTH NUMBER: " + monthNameNumber);

            numOfHoursWorked = String.format(Locale.getDefault(), "%.2f",
                    shift.getFloat(shift.getColumnIndex(ShiftColumns.NUM_HRS_SHIFT)));
            remoteViews.setTextViewText(R.id.numOfHoursWorkedInShift, "NUMBER OF HOURS WORKED: " + numOfHoursWorked);

            grossPay = String.format(Locale.getDefault(), "%.2f" +
                    "", shift.getFloat(shift.getColumnIndex(ShiftColumns.GROSS_PAY)));
            remoteViews.setTextViewText(R.id.grossPay, "$" + grossPay + " GROSS PAY");
        }
        return remoteViews;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews parceledViews;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] sentAppWidgetIds;

        if (intent.getAction().equals(UPDATE_WIDGET) && intent.hasExtra(REMOTE_VIEWS_EXTRA) && intent.hasExtra(APP_WIDGET_IDS_EXTRA)) {
            parceledViews = intent.getParcelableExtra(REMOTE_VIEWS_EXTRA);
            sentAppWidgetIds = intent.getIntArrayExtra(APP_WIDGET_IDS_EXTRA);
            parceledViews = populateWidget(parceledViews, context);
            appWidgetManager.updateAppWidget(sentAppWidgetIds, parceledViews);
        }
    }


}
