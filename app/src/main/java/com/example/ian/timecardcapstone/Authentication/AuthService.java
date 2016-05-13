package com.example.ian.timecardcapstone.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthService extends Service {
    private RosterAppsAuth mRosterAppsAuth;

    public AuthService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRosterAppsAuth = new RosterAppsAuth(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mRosterAppsAuth.getIBinder();

    }
}
