package com.example.ian.timecardcapstone.data;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    public final static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0";
    public final static String rosterappsLogin = "https://jetblue.rosterapps.com/Login.aspx?ReturnUrl=/";
    public final static String rosterAppsCalender = "https://jetblue.rosterapps.com/calendar.month.aspx";
    public final static String eventTarget = "__EVENTTARGET";
    public final static String eventArgument = "__EVENTARGUMENT";
    public final static String lastFocus = "__LASTFOCUS";
    public final static String viewState = "__VIEWSTATE";
    public final static String viewStateGenerator = "__VIEWSTATEGENERATOR";
    public final static String txtUsername = "txtUsername";
    public final static String txtPassword = "txtPassword";
    public final static String btnLogin = "btnLogin";
    public final static String LOG_TAG = MyIntentService.class.getSimpleName();

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_LOGIN_ROSTERAPPS = "com.example.ian.timecardcapstone.data.action.FOO";
    private static final String ACTION_BAZ = "com.example.ian.timecardcapstone.data.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_USERNAME = "com.example.ian.timecardcapstone.data.extra.PARAM1";
    private static final String EXTRA_PASSWORD = "com.example.ian.timecardcapstone.data.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionkLoginRosterapps(Context context, String username, String password) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_LOGIN_ROSTERAPPS);
        intent.putExtra(EXTRA_USERNAME, username);
        intent.putExtra(EXTRA_PASSWORD, password);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_USERNAME, param1);
        intent.putExtra(EXTRA_PASSWORD, param2);
        context.startService(intent);
    }
// TODO: Implement a user interface to allow the user to input their username and password, and that is passed into this service via an intent
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_LOGIN_ROSTERAPPS.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_USERNAME);
                final String param2 = intent.getStringExtra(EXTRA_PASSWORD);
                organizeShiftHTMLData(handleActionRosterappsLogin(param1, param2));
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_USERNAME);
                final String param2 = intent.getStringExtra(EXTRA_PASSWORD);
                handleActionBaz(param1, param2);
            }
        }
    }
// TODO: Get the HTML string from logging into Rosterapps. Organize the HTML data, sorting out all of the shift data to be organized week by week
    /**
     * Log into Rosterapps with the provided user name and password, and get back the HTML data from the calendar
     *
     *
     *
     *
     */
    private String handleActionRosterappsLogin(String username, String password) {
        try {
            Connection.Response loginResponse = Jsoup.connect(rosterappsLogin)
                    .userAgent(userAgent)
                    .data(eventTarget, "")
                    .data(eventArgument, "")
                    .data(lastFocus, "")
                    .data(viewState, "/wEPDwULLTE0NTU3NDM0MzlkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBRBjaGtQZXJzaXN0Q29va2llI+gcwdzIyCAjhj2bPon1rUHC+z8=")
                    .data(viewStateGenerator, "C2EE9ABB")
                    .data(txtUsername, username)
                    .data(txtPassword, password)
                    .data(btnLogin, "Login")
                    .method(Connection.Method.POST)
                    .execute();
            Map<String, String> loginCookie = loginResponse.cookies();
            System.out.print("COOKIES: " + loginCookie.toString());
            //  Log.i(LOG_TAG," LOGIN_RESPONSE: " + loginResponse.parse().html());
            Document calendarDocument = Jsoup.connect(rosterAppsCalender)
                    .userAgent(userAgent)
                    .cookies(loginCookie)
                    .execute().parse();
            System.out.print(calendarDocument.html());

            return calendarDocument.html();
        } catch (IOException exception) {
            Log.e(LOG_TAG, "EXCEPTION ERROR: " + exception);
        }
        // TODO: Handle action Foo
      return null;
    }

    private void organizeShiftHTMLData(String HTML) {


    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
