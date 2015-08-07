package social.laika.app.network.sync;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import com.activeandroid.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import social.laika.app.activities.MyDogsActivity;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.network.requests.AlarmRemindersRequest;
import social.laika.app.network.requests.SyncRequest;

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

    private static final String DATA = "data";
    private static final String CODE = SyncUtils.CODE;
    private static final String ID = MyDogsActivity.ID;
    private static final String DOG_ID = Dog.COLUMN_DOG_ID;
    private static final int CODE_GENERAL = SyncUtils.CODE_GENERAL;
    private static final int CODE_LOCATIONS = SyncUtils.CODE_LOCATIONS;
    private static final int CODE_BREEDS = SyncUtils.CODE_BREEDS;
    private static final int CODE_POSTULATIONS = SyncUtils.CODE_POSTULATIONS;
    private static final int CODE_MY_DOG = SyncUtils.CODE_MY_DOG;
    private static final int CODE_ALARM_REFRESH = SyncUtils.CODE_ALARM_REFRESH;
    private static final int CODE_ALARM_SYNC = SyncUtils.CODE_ALARM_SYNC;
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
     * required to refresh data from the network, parse it, and store it in the content provider is
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

        try {

            Context context = this.getContext();
            int code = 0;

            if (extras.containsKey(CODE)) {

                code = extras.getInt(CODE);

            } else if (extras.containsKey(DATA)){

                JSONObject jsonObject = new JSONObject(extras.getString(DATA));
                code = jsonObject.getInt(CODE);
                int dogId = jsonObject.optInt(DOG_ID);

                extras.putInt(CODE, code);
                extras.putInt(DOG_ID, dogId);

            }

            Log.i(TAG, "Beginning network synchronization | code:" + code);
            sync(context, code, extras);

        } catch (InterruptedException e) {
            Log.e(TAG, "Retrieve cards api call interrupted.");
            syncResult.stats.numIoExceptions++;
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e(TAG, "Retrieve cards api call failed.");
            syncResult.stats.numIoExceptions++;
            e.printStackTrace();
        } catch (TimeoutException e) {
            Log.e(TAG, "Retrieve cards api call timed out.");
            syncResult.stats.numIoExceptions++;
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, "Bad JSON request");
            syncResult.stats.numIoExceptions++;
            e.printStackTrace();
        } catch (NullPointerException e) {
            syncResult.stats.numIoExceptions++;
            Log.e(TAG, "FUUUUUUUCK! I missed something");
            e.printStackTrace();
        }


//        String lastSync = Do.dateToString(PrefsManager.getLastSync(context), Do.DAY_FIRST);
//
//        if (!Do.isNullOrEmpty(lastSync)) {
//
//
//        }


        Log.i(TAG, "Network synchronization complete");
    }

    private void sync(Context context, int code, Bundle extras) throws InterruptedException,
            ExecutionException, TimeoutException, JSONException {

        SyncRequest request;

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
            case CODE_ALARM_REFRESH:

                int dogId = extras.getInt(DOG_ID);
                request = new AlarmRemindersRequest(context, dogId);
                JSONObject jsonObject = request.refresh();
                AlarmReminder.saveReminders(jsonObject, context);

                break;

            case CODE_ALARM_SYNC:

                List<AlarmReminder> alarmReminders = AlarmReminder.getAllReminders();

                for (AlarmReminder alarmReminder : alarmReminders) {
                    request = new AlarmRemindersRequest(context, alarmReminder);
                    request.sync();
                }
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

    public void syncPostulation(Context context) {


    }


}

