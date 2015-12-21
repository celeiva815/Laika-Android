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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import social.laika.app.activities.MyDogsActivity;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.CalendarReminder;
import social.laika.app.models.Dog;
import social.laika.app.models.Photo;
import social.laika.app.models.VetVisit;
import social.laika.app.network.requests.AlarmRemindersRequest;
import social.laika.app.network.requests.CalendarRemindersRequest;
import social.laika.app.network.requests.DogRequest;
import social.laika.app.network.requests.PhotosRequest;
import social.laika.app.network.requests.SyncRequest;
import social.laika.app.network.requests.VetVisitsRequest;

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
    public static final String TAG = "Laika Sync Service";

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
    private static final int CODE_CALENDAR_REFRESH = SyncUtils.CODE_CALENDAR_REFRESH;
    private static final int CODE_CALENDAR_SYNC = SyncUtils.CODE_CALENDAR_SYNC;
    private static final int CODE_VET_VISIT_REFRESH = SyncUtils.CODE_VET_VISIT_REFRESH;
    private static final int CODE_VET_VISIT_SYNC = SyncUtils.CODE_VET_VISIT_SYNC;
    private static final int CODE_PHOTO_REFRESH = SyncUtils.CODE_PHOTO_REFRESH;
    private static final int CODE_PHOTO_SYNC = SyncUtils.CODE_PHOTO_SYNC;
    private static final int CODE_OWNER = SyncUtils.CODE_OWNER;
    private static final int CODE_OWNER_CREATE = SyncUtils.CODE_OWNER_CREATE;
    private static final int CODE_OWNER_READ = SyncUtils.CODE_OWNER_READ;
    private static final int CODE_OWNER_UPDATE = SyncUtils.CODE_OWNER_UPDATE;
    private static final int CODE_OWNER_DELETE = SyncUtils.CODE_OWNER_DELETE;


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
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        // TODO implement: code zero synchronization
        try {

            Context context = this.getContext();
            int code = extras.getInt(CODE);

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

        Log.i(TAG, "Network synchronization complete");
    }

    private void sync(Context context, int code, Bundle extras) throws InterruptedException,
            ExecutionException, TimeoutException, JSONException {

        SyncRequest request;
        JSONObject jsonObject;
        int dogId = extras.getInt(DOG_ID);

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

                request = new DogRequest(context, dogId);
                ((DogRequest) request).download();

                break;

            case CODE_ALARM_REFRESH:

                request = new AlarmRemindersRequest(context, dogId);
                jsonObject = request.refresh();
                AlarmReminder.saveReminders(jsonObject, context);

                break;

            case CODE_ALARM_SYNC:

                List<AlarmReminder> alarmReminders = AlarmReminder.getNeedSync();

                for (AlarmReminder alarmReminder : alarmReminders) {
                    request = new AlarmRemindersRequest(context, alarmReminder);
                    request.sync();
                }
                break;

            case CODE_CALENDAR_REFRESH:

                request = new CalendarRemindersRequest(context, dogId);
                jsonObject = request.refresh();
                CalendarReminder.saveReminders(jsonObject, context);

                break;
            case CODE_CALENDAR_SYNC:

                List<CalendarReminder> calendarReminders = CalendarReminder.getNeedSync();

                for (CalendarReminder calendarReminder : calendarReminders) {
                    request = new CalendarRemindersRequest(context, calendarReminder);
                    request.sync();
                }

                break;
            case CODE_VET_VISIT_REFRESH:

                request = new VetVisitsRequest(context, dogId);
                jsonObject = request.refresh();
                VetVisit.saveVetVisits(jsonObject);

                break;
            case CODE_VET_VISIT_SYNC:

                List<VetVisit> vetVisits = VetVisit.getNeedSync();

                for (VetVisit vetVisit : vetVisits) {
                    request = new VetVisitsRequest(context, vetVisit);
                    request.sync();
                }

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
            case CODE_PHOTO_REFRESH:

                request = new PhotosRequest(context, dogId);
                jsonObject = request.refresh();
                Photo.saveDogPhotos(jsonObject, context);

                break;
            case CODE_PHOTO_SYNC:

                List<Photo> photos = Photo.getNeedSync();

                for (Photo photo : photos) {
                    request = new PhotosRequest(context, photo);
                    request.sync();
                }

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

