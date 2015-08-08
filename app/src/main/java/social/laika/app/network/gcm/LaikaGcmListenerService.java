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
import social.laika.app.models.Dog;
import social.laika.app.models.UserAdoptDog;
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
    public static final int GCM_ALERT_REMINDER_UPDATE = 313;        /* When a reminder is updated */
    public static final int GCM_ALERT_REMINDER_DELETE = 314;        /* When a reminder is deleted */
    public static final int GCM_ADDED_AS_OWNER = 340;               /* When a users add you as a dog owner */


    @Override
    public void onMessageReceived(String from, Bundle data) {

        String message = data.getString("title");

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (!Do.isNullOrEmpty(message)) {
            sendNotification(message);
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

    private void sendNotification(String message) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_laika)
                .setContentTitle("Laika")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
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
                JSONObject alarmReminder = jsonData.getJSONObject(AlarmReminder.TABLE_NAME);
                /* Update Operation */
                AlarmReminder.saveReminder(alarmReminder, getApplicationContext());
                break;
            case GCM_ALERT_REMINDER_DELETE:
                int reminderId = jsonData.getInt(AlarmReminder.COLUMN_ALARM_REMINDER_ID);
                /* Deletion Operation */
                deleteAlarmReminder(reminderId);
                break;
            case GCM_ADDED_AS_OWNER:
                int dog_id = jsonData.getInt(Dog.COLUMN_DOG_ID);
                /* Hard Dog Synchronization */
                syncDogHard(dog_id);
                break;
        }
    }

    /** Performs a delete operation to a AlarmReminder instance.
     * @param reminderId The alarm reminder id to be deleted.
     */
    private static void deleteAlarmReminder(int reminderId) {
        /* We get the single instance */
        AlarmReminder alarmReminder = AlarmReminder.getSingleReminder(reminderId);
        if (alarmReminder != null) {
            /* Deleting the alarm */
            alarmReminder.delete();
        }  else {
            /* Login the error */
            Log.e(TAG, "Attempting to delete alarm reminder @" + reminderId + ", but no alarm was found.");
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


}
