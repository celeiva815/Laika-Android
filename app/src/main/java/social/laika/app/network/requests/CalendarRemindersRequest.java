package social.laika.app.network.requests;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import social.laika.app.fragments.AlarmReminderMyDogFragment;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.network.sync.SyncUtils;
import social.laika.app.responses.AlarmRemindersResponse;
import social.laika.app.responses.CreateAlarmReminderResponse;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 12-06-15.
 */
public class CalendarRemindersRequest extends BaseRequest {

    private static final int CODE_CALENDAR = SyncUtils.CODE_CALENDAR;
    private static final int CODE_CALENDAR_CREATE = SyncUtils.CODE_CALENDAR_CREATE;
    private static final int CODE_CALENDAR_READ = SyncUtils.CODE_CALENDAR_READ;
    private static final int CODE_CALENDAR_UPDATE = SyncUtils.CODE_CALENDAR_UPDATE;
    private static final int CODE_CALENDAR_DELETE = SyncUtils.CODE_CALENDAR_DELETE;

    private static final String TAG = CalendarRemindersRequest.class.getSimpleName();

    public Context mContext;
    public int mCode;
    public int mCalendarId;

    public CalendarRemindersRequest(Context context, int code, int calendarId) {
        super(context);

        mCode = code;
        mCalendarId = calendarId;
    }



    public void createCalendarReminders(AlarmReminder alarmReminder, int method,
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

    public void requestCalendarReminders(Dog dog, Context context) {

        Map<String,String> params = new HashMap<>();
        AlarmRemindersResponse response = new AlarmRemindersResponse(context, dog);

        params.put(Dog.COLUMN_DOG_ID, Integer.toString(dog.mDogId));

        Request request = RequestManager.getRequest(params,
                RequestManager.ADDRESS_ALERT_REMINDERS, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(request, TAG);


    }

    public void updateCalendarReminders(AlarmReminder alarmReminder, int method,
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

    public void deleteCalendarReminders() {

        JSONObject jsonParams = new JSONObject();
        SimpleResponse response = new SimpleResponse(this);

        Request createRequest = RequestManager.deleteRequest(jsonParams,
                RequestManager.ADDRESS_ALERT_REMINDERS, response, response,
                PrefsManager.getUserToken(mContext));

        VolleyManager.getInstance(mContext)
                .addToRequestQueue(createRequest, TAG);

    }


    @Override
    public void request() {

        switch (mCode) {

            case CODE_CALENDAR_DELETE:

                deleteCalendarReminders();

                break;
        }
    }

    @Override
    public void onSuccess() {

        switch (mCode) {

            case CODE_CALENDAR_DELETE:


                break;
        }

    }

    @Override
    public void onFailure() {

        switch (mCode) {

            case CODE_CALENDAR_DELETE:


                break;
        }

    }
}
