package social.laika.app.network.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import social.laika.app.R;
import social.laika.app.activities.MainActivity;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.CalendarReminder;
import social.laika.app.models.Dog;
import social.laika.app.models.Photo;
import social.laika.app.models.UserAdoptDog;
import social.laika.app.models.VetVisit;
import social.laika.app.network.requests.PostulationRequest;
import social.laika.app.network.sync.SyncUtils;
import social.laika.app.utils.Do;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 30-07-15.
 */
public class LaikaGcmListenerService extends GcmListenerService {

    public static final String TAG = LaikaGcmListenerService.class.getSimpleName();

    /* GCM Codes */
    public static final int GCM_POSTULATED_DOGS = 200;              /* When a postulation is updated */
    public static final int GCM_ALERT_REMINDER = 310;               /* ? */
    public static final int GCM_ALERT_REMINDER_UPDATE = 313;        /* When a alert reminder is updated */
    public static final int GCM_ALERT_REMINDER_DELETE = 314;        /* When a alert reminder is deleted */
    public static final int GCM_CALENDAR_REMINDER_UPDATE = 323;     /* When a calendar reminder is updated */
    public static final int GCM_CALENDAR_REMINDER_DELETE = 324;     /* When a calender reminder is deleted */
    public static final int GCM_VET_VISIT_UPDATE = 333;             /* When a vet visit is updated */
    public static final int GCM_VET_VISIT_DELETE = 334;             /* When a vet visit is deleted */
    public static final int GCM_ADDED_AS_OWNER = 340;               /* When a users add you as a dog owner */
    public static final int GCM_PHOTO_UPDATE = 353;                 /* When a photo is either created or updated */
    public static final int GCM_PHOTO_DELETE = 354;                 /* When a photo is deleted */


    @Override
    public void onMessageReceived(String from, Bundle data) {

        String title = data.getString("title");
        String message = data.getString("message");

        if (!Do.isNullOrEmpty(message)) {

            title = !Do.isNullOrEmpty(title)? title : "Laika";
            sendNotification(message, title);
        }

        try {
            synchronize(data);
        } catch (JSONException e) {
            Log.e(TAG, "JSOException while parsing the data.");
        } catch (NullPointerException e) {
            Log.e(TAG, "NullPointerException while parsing the data.");
        }
    }

    public static void sendNotification(String message, Class activityClass, Bundle data,
                                        Context context) {

        Log.d(TAG, "Displaying GCM message: " + message);

        Intent intent = new Intent(context, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" +
                R.raw.ladrido_simple);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.camera_icon_camera)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotification(String message, String title) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ladrido_simple);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_laika)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private Uri getWofUri() {

        return Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ladrido);

    }

    public void synchronize(Bundle data) throws JSONException, NullPointerException {
        /* First, we need to obtain the data from the message */
        JSONObject jsonData = new JSONObject(data.getString("data"));

        int code = jsonData.getInt("code");
        Log.d(TAG, "Beginning GCM synchronization with code: " + code);
        switch (code) {
            case GCM_POSTULATED_DOGS:
                int userAdoptDogID = jsonData.getInt(UserAdoptDog.COLUMN_USER_ADOPT_DOG_ID);
                int status = jsonData.getInt(UserAdoptDog.COLUMN_STATUS);
                /* Soft UserAdoptDog Synchronization */
                syncUserAdoptDogSoft(userAdoptDogID, status);
                break;
            case GCM_ALERT_REMINDER:
                Log.e(TAG, "GCM_ALERT_REMINDER not implemented");
                break;
            case GCM_ALERT_REMINDER_UPDATE:
                JSONObject reminder = new JSONObject(jsonData.getString(AlarmReminder.TABLE_NAME));
                /* Update Operation */
                AlarmReminder.saveReminder(reminder, getApplicationContext());
                break;
            case GCM_ALERT_REMINDER_DELETE:
                int reminderId = jsonData.getInt(AlarmReminder.COLUMN_ALARM_REMINDER_ID);
                /* Deletion Operation */
                deleteAlarmReminder(reminderId);
                break;
            case GCM_CALENDAR_REMINDER_UPDATE:
                Log.e(TAG, "GCM_CALENDAR_REMINDER_UPDATE not implemented");
                /* TODO implement GCM_CALENDAR_REMINDER_UPDATE
                reminder = jsonData.getJSONObject(CalendarReminder.TABLE_NAME);
                /* Update Operation */
                /* CalendarReminder.saveReminder(reminder, getApplicationContext()); */
                break;
            case GCM_CALENDAR_REMINDER_DELETE:
                reminderId = jsonData.getInt(CalendarReminder.COLUMN_CALENDAR_REMINDER_ID);
                /* Deletion Operation */
                deleteCalendarReminder(reminderId);
                break;
            case GCM_VET_VISIT_UPDATE:
                JSONObject vetVisit = new JSONObject(jsonData.getString(VetVisit.TABLE_NAME));
                /* Update Operation */
                VetVisit.saveVetVisit(vetVisit);
                break;
            case GCM_VET_VISIT_DELETE:
                int vetVisitID = jsonData.getInt(VetVisit.COLUMN_VET_VISIT_ID);
                /* Deletion Operation */
                deleteVetVisit(vetVisitID);
                break;
            case GCM_ADDED_AS_OWNER:
                int dog_id = jsonData.getInt(Dog.COLUMN_DOG_ID);
                /* Hard Dog Synchronization */
                syncDogHard(dog_id);
                break;
            case GCM_PHOTO_UPDATE:
                JSONObject photo = new JSONObject(jsonData.getString(Photo.TABLE_PHOTOS));
                /* Hard Photo Synchronization */
                Photo.saveDogPhoto(photo, getApplicationContext());
                break;
            case GCM_PHOTO_DELETE:
                int photoID = jsonData.getInt(Photo.COLUMN_PHOTO_ID);
                /* Deletion Operation */
                deletePhoto(photoID);
                break;
        }
        Log.d(TAG, "GCM synchronization completed");
    }

    /** Performs a delete operation to a AlarmReminder instance.
     * @param reminderId The alarm_reminder id to be deleted.
     */
    private static void deleteAlarmReminder(int reminderId) {
        /* We get the single instance */
        AlarmReminder alarmReminder = AlarmReminder.getSingleReminder(reminderId);
        if (alarmReminder != null) {
            Log.d(TAG, "Deleting alarm " + alarmReminder.mAlarmReminderId + "[" + alarmReminder.getId() + "]");
            /* Deleting the alarm */
            alarmReminder.delete();
        }  else {
            /* Login the error */
            Log.e(TAG, "Attempting to delete alarm reminder @" + reminderId + ", but no alarm was found.");
        }
    }

    /** Performs a delete operation to a CalendarReminder instance.
     * @param reminderId The alarm_reminder id to be deleted.
     */
    private static void deleteCalendarReminder(int reminderId) {
        /* We get the single instance */
        CalendarReminder calendarReminder = CalendarReminder.getSingleReminder(reminderId);
        if (calendarReminder != null) {
            /* Deleting the alarm */
            calendarReminder.delete();
        }  else {
            /* Login the error */
            Log.e(TAG, "Attempting to delete calendar reminder @" + reminderId + ", but no reminder was found.");
        }
    }

    /** Performs a delete operation to a VetVisit instance.
     * @param vetVisitID The vet_visit id to be deleted.
     */
    private void deleteVetVisit(int vetVisitID) {
        /* We get the single instance */
        VetVisit vetVisit = VetVisit.getSingleVetVisit(vetVisitID);
        if (vetVisit != null) {
            /* Deleting the alarm */
            vetVisit.delete();
        }  else {
            /* Login the error */
            Log.e(TAG, "Attempting to delete vet_visit @" + vetVisitID + ", but no vet_visit was found.");
        }
    }

    /** Performs an operation to an UserAdoptDog instance, updating the status.
     * @param userAdoptDogID The user_adopt_dog id to be sync.
     * @param status The status to be updated.
     */
    private static void syncUserAdoptDogSoft(int userAdoptDogID, int status) {
        /* First we search the postulation and then we update the data */
        UserAdoptDog postulation = UserAdoptDog.getSingleUserAdoptDog(userAdoptDogID);
        postulation.mStatus = status;
        postulation.save();
    }

    /** Requests a hard synchronization for a certain dog.
     * @param dogID The dog's id to be sync.
     */
    private static void syncDogHard(int dogID) {
        /* First, we prepare the bundle data */
        Bundle syncInfo = SyncUtils.getFrequentData(SyncUtils.CODE_MY_DOG);
        syncInfo.putInt(Dog.COLUMN_DOG_ID, dogID);
        /* Then we request the synchronization through sync adapter. */
        SyncUtils.triggerRefresh(syncInfo);
    }

    /** Performs a delete operation to a Photo instance.
     * @param photoID The photo's id to be deleted.
     */
    private void deletePhoto(int photoID) {
        /* We get the single instance */
        Photo photo = Photo.getPhoto(photoID);
        if (photo != null) {
            /* Deleting the alarm */
            photo.delete();
        }  else {
            /* Login the error */
            Log.e(TAG, "Attempting to delete photo @" + photoID + ", but no photo was found.");
        }
    }


}
