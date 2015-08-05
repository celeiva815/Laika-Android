package social.laika.app.network.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.annotation.TargetApi;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import social.laika.app.models.AlarmReminder;
import social.laika.app.network.RequestManager;
import social.laika.app.network.requests.AlarmRemindersRequest;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

/**
 * Define a sync adapter for the app.
 * <p/>
 * <p>This class is instantiated in {@link SyncService}, which also binds SyncAdapter to the system.
 * SyncAdapter should only be initialized in SyncService, never anywhere else.
 * <p/>
 * <p>The system calls onPerformSync() via an RPC call through the IBinder object supplied by
 * SyncService.
 */
class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String TAG = "SyncAdapter";

    private static final String CODE = SyncUtils.CODE;
    private static final int CODE_GENERAL = SyncUtils.CODE_GENERAL;
    private static final int CODE_LOCATIONS = SyncUtils.CODE_LOCATIONS;
    private static final int CODE_BREEDS = SyncUtils.CODE_BREEDS;
    private static final int CODE_POSTULATIONS = SyncUtils.CODE_POSTULATIONS;
    private static final int CODE_MY_DOG = SyncUtils.CODE_MY_DOG;
    private static final int CODE_ALARM = SyncUtils.CODE_ALARM;
    private static final int CODE_ALARM_CREATE = SyncUtils.CODE_ALARM_CREATE;
    private static final int CODE_ALARM_READ = SyncUtils.CODE_ALARM_READ;
    private static final int CODE_ALARM_UPDATE = SyncUtils.CODE_ALARM_UPDATE;
    private static final int CODE_ALARM_DELETE = SyncUtils.CODE_ALARM_DELETE;
    private static final int CODE_CALENDAR = SyncUtils.CODE_CALENDAR;
    private static final int CODE_CALENDAR_CREATE = SyncUtils.CODE_CALENDAR_CREATE;
    private static final int CODE_CALENDAR_READ = SyncUtils.CODE_CALENDAR_READ;
    private static final int CODE_CALENDAR_UPDATE = SyncUtils.CODE_CALENDAR_UPDATE;
    private static final int CODE_CALENDAR_DELETE = SyncUtils.CODE_CALENDAR_DELETE;
    private static final int CODE_VET_VISIT = SyncUtils.CODE_VET_VISIT;
    private static final int CODE_VET_VISIT_CREATE = SyncUtils.CODE_VET_VISIT_CREATE;
    private static final int CODE_VET_VISIT_READ = SyncUtils.CODE_VET_VISIT_READ;
    private static final int CODE_VET_VISIT_UPDATE = SyncUtils.CODE_VET_VISIT_UPDATE;
    private static final int CODE_VET_VISIT_DELETE = SyncUtils.CODE_VET_VISIT_DELETE;
    private static final int CODE_OWNER = SyncUtils.CODE_OWNER;
    private static final int CODE_OWNER_CREATE = SyncUtils.CODE_OWNER_CREATE;
    private static final int CODE_OWNER_READ = SyncUtils.CODE_OWNER_READ;
    private static final int CODE_OWNER_UPDATE = SyncUtils.CODE_OWNER_UPDATE;
    private static final int CODE_OWNER_DELETE = SyncUtils.CODE_OWNER_DELETE;
    private static final int CODE_PHOTO = SyncUtils.CODE_PHOTO;
    private static final int CODE_PHOTO_CREATE = SyncUtils.CODE_PHOTO_CREATE;
    private static final int CODE_PHOTO_READ = SyncUtils.CODE_PHOTO_READ;
    private static final int CODE_PHOTO_UPDATE = SyncUtils.CODE_PHOTO_UPDATE;
    private static final int CODE_PHOTO_DELETE = SyncUtils.CODE_PHOTO_DELETE;


    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    /**
     * Called by the Android system in response to a request to run the sync adapter. The work
     * required to read data from the network, parse it, and store it in the content provider is
     * done here. Extending AbstractThreadedSyncAdapter ensures that all methods within SyncAdapter
     * run on a background thread. For this reason, blocking I/O and other long-running tasks can be
     * run <em>in situ</em>, and you don't have to set up a separate thread for them.
     * .
     * <p/>
     * <p>This is where we actually perform any work required to perform a sync.
     * {@link android.content.AbstractThreadedSyncAdapter} guarantees that this will be called on a non-UI thread,
     * so it is safe to peform blocking I/O here.
     * <p/>
     * <p>The syncResult argument allows you to pass information back to the method that triggered
     * the sync.
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {

        Context context = this.getContext();
        int code = extras.getInt(CODE);
        Log.i(TAG, "Beginning network synchronization | code:" + code);

        try {
                sync(context, code, extras);

            List<AlarmReminder> alarmReminders = AlarmReminder.getAllReminders();

            ActiveAndroid.beginTransaction();
            try {
                for (AlarmReminder alarmReminder : alarmReminders) {
                    alarmReminder.mNeedsSync = false;
                    alarmReminder.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }

        } catch (OperationCanceledException e) {
            Log.e(TAG, "The operation was canceled.");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "Problem with the IO");
            e.printStackTrace();
        } catch (AuthenticatorException e) {
            Log.e(TAG, "Retrieve cards api call interrupted.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e(TAG, "Retrieve cards api call interrupted.");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e(TAG, "Retrieve cards api call failed.");
            e.printStackTrace();
        } catch (TimeoutException e) {
            Log.e(TAG, "Retrieve cards api call timed out.");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, "Bad JSON request");
            e.printStackTrace();
        }


        String lastSync = Do.dateToString(PrefsManager.getLastSync(context), Do.DAY_FIRST);

        if (!Do.isNullOrEmpty(lastSync)) {


        }


        Log.i(TAG, "Network synchronization complete");
    }

    private void sync(Context context, int code, Bundle extras) throws InterruptedException,
            ExecutionException, TimeoutException, JSONException {

        switch (code) {

            case CODE_GENERAL:

                break;
            case CODE_LOCATIONS:

                break;
            case CODE_BREEDS:

                break;
            case CODE_POSTULATIONS:

                break;
            case CODE_MY_DOG:

                break;
            case CODE_ALARM:

                break;
            case CODE_ALARM_CREATE:

                break;
            case CODE_ALARM_READ:

                break;
            case CODE_ALARM_UPDATE:

                break;
            case CODE_ALARM_DELETE:

                AlarmRemindersRequest request = new AlarmRemindersRequest(context);
                request.deleteAlertReminder(context, extras);

                break;
            case CODE_CALENDAR:

                break;
            case CODE_CALENDAR_CREATE:

                break;
            case CODE_CALENDAR_READ:

                break;
            case CODE_CALENDAR_UPDATE:

                break;
            case CODE_CALENDAR_DELETE:

                break;
            case CODE_VET_VISIT:

                break;
            case CODE_VET_VISIT_CREATE:

                break;
            case CODE_VET_VISIT_READ:

                break;
            case CODE_VET_VISIT_UPDATE:

                break;
            case CODE_VET_VISIT_DELETE:

                break;
            case CODE_OWNER:

                break;
            case CODE_OWNER_CREATE:

                break;
            case CODE_OWNER_READ:

                break;
            case CODE_OWNER_UPDATE:

                break;
            case CODE_OWNER_DELETE:

                break;
            case CODE_PHOTO:

                break;
            case CODE_PHOTO_CREATE:

                break;
            case CODE_PHOTO_READ:

                break;
            case CODE_PHOTO_UPDATE:

                break;
            case CODE_PHOTO_DELETE:

                break;

        }


    }


    public void syncGeneral(Context context) {


    }

    public void syncMyDog(Context context) {


    }


    public void syncVetVisits(Context context) {


    }


}

