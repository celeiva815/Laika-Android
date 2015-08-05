package social.laika.app.network.requests;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import social.laika.app.fragments.AlarmReminderMyDogFragment;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.network.sync.SyncUtils;
import social.laika.app.responses.AlarmRemindersResponse;
import social.laika.app.responses.CalendarRemindersResponse;
import social.laika.app.responses.CreateAlarmReminderResponse;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 12-06-15.
 */
public class AlarmRemindersRequest extends BaseRequest {

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

    private static final String TAG = AlarmRemindersRequest.class.getSimpleName();

    public AlarmRemindersRequest(Context context) {
        super(context);

    }



    public void createAlarmReminders(AlarmReminder alarmReminder, int method,
                                     AlarmReminderMyDogFragment fragment) {

        Context context = fragment.getActivity().getApplicationContext();
        JSONObject jsonParams = alarmReminder.getJsonObject();
        CreateAlarmReminderResponse response = new CreateAlarmReminderResponse(context, this);

        Request createRequest = RequestManager.defaultRequest(method, jsonParams,
                RequestManager.ADDRESS_ALERT_REMINDERS, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(createRequest, TAG);

    }

    public void requestAlarmReminders(Dog dog, Context context) {

        Map<String,String> params = new HashMap<>();
        AlarmRemindersResponse response = new AlarmRemindersResponse(context, dog);

        params.put(Dog.COLUMN_DOG_ID, Integer.toString(dog.mDogId));

        Request request = RequestManager.getRequest(params,
                RequestManager.ADDRESS_ALERT_REMINDERS, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(request, TAG);


    }

    public void updateAlarmReminders(AlarmReminder alarmReminder, int method,
                                     AlarmReminderMyDogFragment fragment) {

        Context context = fragment.getActivity().getApplicationContext();
        JSONObject jsonParams = alarmReminder.getJsonObject();
        CreateAlarmReminderResponse response = new CreateAlarmReminderResponse(context, this);

        Request createRequest = RequestManager.defaultRequest(method, jsonParams,
                RequestManager.ADDRESS_ALERT_REMINDERS, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(createRequest, TAG);

    }

    public JSONObject deleteAlertReminder(Context context, Bundle data) throws JSONException,
            InterruptedException, ExecutionException, TimeoutException {

        int reminderId = data.getInt(AlarmReminder.COLUMN_ALARM_REMINDER_ID);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(AlarmReminder.COLUMN_ALARM_REMINDER_ID, reminderId);

        String address = RequestManager.ADDRESS_ALERT_REMINDERS;
        String token = PrefsManager.getUserToken(context);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse(this);
        Request request = RequestManager.deleteRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

        return future.get(30, TimeUnit.SECONDS);

    }

    @Override
    public void request() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}
