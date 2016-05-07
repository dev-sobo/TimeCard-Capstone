package com.example.ian.timecardcapstone;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;




public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0";
    private final String rosterappsLogin = "https://jetblue.rosterapps.com/Login.aspx?ReturnUrl=/";
    private final String rosterAppsCalender = "https://jetblue.rosterapps.com/calendar.month.aspx";
    private final String eventTarget = "__EVENTTARGET";
    private final String eventArgument = "__EVENTARGUMENT";
    private final String lastFocus = "__LASTFOCUS";
    private final String viewState = "__VIEWSTATE";
    private final String viewStateGenerator = "__VIEWSTATEGENERATOR";
    private final String txtUsername = "txtUsername";
    private final String txtPassword = "txtPassword";
    private final String btnLogin = "btnLogin";
    private final String LOG_TAG = RosterAppsData.class.getSimpleName();
    private String mUserLogin;
    private String mUserPassword;

    ContentResolver mContentResolver;
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    /**
     *
     *This method will handle connecting to RosterApps.
     *
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

    }
}
