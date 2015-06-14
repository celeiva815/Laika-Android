package cl.laikachile.laika.network.requests;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cl.laikachile.laika.fragments.AlarmReminderMyDogFragment;
import cl.laikachile.laika.models.AlarmReminder;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.AlarmRemindersResponse;
import cl.laikachile.laika.responses.CalendarRemindersResponse;
import cl.laikachile.laika.responses.CreateAlarmReminderResponse;
import cl.laikachile.laika.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 12-06-15.
 */
public class RemindersRequest {

    private static final String TAG = RemindersRequest.class.getSimpleName();

    public void createAlarmReminders(AlarmReminder alarmReminder, int method, Context context) {

        JSONObject jsonParams = alarmReminder.getJsonObject();
        CreateAlarmReminderResponse response = new CreateAlarmReminderResponse(context);

        Request createRequest = RequestManager.defaultRequest(method, jsonParams,
                RequestManager.ADDRESS_ALERT_REMINDERS, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(createRequest, TAG);

    }

    public void createAlarmReminders(AlarmReminder alarmReminder, int method,
                                     AlarmReminderMyDogFragment fragment) {

        Context context = fragment.getActivity().getApplicationContext();
        JSONObject jsonParams = alarmReminder.getJsonObject();
        CreateAlarmReminderResponse response = new CreateAlarmReminderResponse(fragment);

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

    public void requestCalendarReminders(Dog dog, Context context) {

        Map<String,String> params = new HashMap<>();
        CalendarRemindersResponse response = new CalendarRemindersResponse(context);

        params.put(Dog.COLUMN_DOG_ID, Integer.toString(dog.mDogId));

        Request request = RequestManager.getRequest(params,
                RequestManager.ADDRESS_CALENDAR_REMINDERS, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(request, TAG);


    }



}
