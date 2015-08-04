package social.laika.app.network.requests;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import social.laika.app.fragments.AlarmReminderMyDogFragment;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.AlarmRemindersResponse;
import social.laika.app.responses.CalendarRemindersResponse;
import social.laika.app.responses.CreateAlarmReminderResponse;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 12-06-15.
 */
public class RemindersRequest implements Requestable {

    private static final String TAG = RemindersRequest.class.getSimpleName();

    public void createAlarmReminders(Dog dog, AlarmReminder alarmReminder, int method,
                                     Context context) {

        JSONObject jsonParams = alarmReminder.getJsonObject();
        CreateAlarmReminderResponse response = new CreateAlarmReminderResponse(context, dog, this);

        Request createRequest = RequestManager.defaultRequest(method, jsonParams,
                RequestManager.ADDRESS_ALERT_REMINDERS, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context).addToRequestQueue(createRequest, TAG);

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
