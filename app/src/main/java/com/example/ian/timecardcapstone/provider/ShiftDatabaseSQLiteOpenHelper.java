package com.example.ian.timecardcapstone.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.example.ian.timecardcapstone.BuildConfig;
import com.example.ian.timecardcapstone.provider.rosterappsdata.RosterappsdataColumns;
import com.example.ian.timecardcapstone.provider.shift.ShiftColumns;

public class ShiftDatabaseSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = ShiftDatabaseSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "shiftdatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static ShiftDatabaseSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final ShiftDatabaseSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_ROSTERAPPSDATA = "CREATE TABLE IF NOT EXISTS "
            + RosterappsdataColumns.TABLE_NAME + " ( "
            + RosterappsdataColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RosterappsdataColumns.ROSTERAPPS_DATA + " TEXT, "
            + RosterappsdataColumns.COLOR_OF_SHIFTS + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_SHIFT = "CREATE TABLE IF NOT EXISTS "
            + ShiftColumns.TABLE_NAME + " ( "
            + ShiftColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ShiftColumns.START_TIME_HHMM + " TEXT NOT NULL, "
            + ShiftColumns.END_TIME_HHMM + " TEXT, "
            + ShiftColumns.START_TIME_UNIX + " INTEGER NOT NULL, "
            + ShiftColumns.END_TIME_UNIX + " INTEGER, "
            + ShiftColumns.HOURLY_PAY + " REAL NOT NULL, "
            + ShiftColumns.DAY_OF_WEEK + " TEXT NOT NULL, "
            + ShiftColumns.DAY_OF_MONTH + " INTEGER NOT NULL, "
            + ShiftColumns.MONTH_NAME + " TEXT NOT NULL, "
            + ShiftColumns.YEAR + " INTEGER NOT NULL, "
            + ShiftColumns.NUM_HRS_SHIFT + " REAL, "
            + ShiftColumns.GROSS_PAY + " REAL "
            + " );";

    // @formatter:on

    public static ShiftDatabaseSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static ShiftDatabaseSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static ShiftDatabaseSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new ShiftDatabaseSQLiteOpenHelper(context);
    }

    private ShiftDatabaseSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new ShiftDatabaseSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static ShiftDatabaseSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new ShiftDatabaseSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private ShiftDatabaseSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new ShiftDatabaseSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_ROSTERAPPSDATA);
        db.execSQL(SQL_CREATE_TABLE_SHIFT);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
