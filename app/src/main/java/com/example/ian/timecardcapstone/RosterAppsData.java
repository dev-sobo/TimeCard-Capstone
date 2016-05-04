package com.example.ian.timecardcapstone;

/**
 * Created by ian on 5/4/2016.
 */
public class RosterAppsData {
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

    public RosterAppsData(String userLogin, String userPass){
        mUserLogin = userLogin;
        mUserPassword = userPass;
    }


}
