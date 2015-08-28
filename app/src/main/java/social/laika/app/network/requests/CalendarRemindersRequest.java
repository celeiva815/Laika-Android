package social.laika.app.network.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import social.laika.app.models.CalendarReminder;
import social.laika.app.models.Dog;
import social.laika.app.network.RequestManager;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 12-06-15.
 */
public class CalendarRemindersRequest extends SyncRequest {


    private static final String TAG = CalendarRemindersRequest.class.getSimpleName();

    CalendarReminder mCalendarReminder;
    int mDogId;

    public CalendarRemindersRequest(Context context, CalendarReminder alarmReminder) {
        super(context);
        mCalendarReminder = alarmReminder;

    }

    public CalendarRemindersRequest(Context mContext, int mDogId) {
        super(mContext);
        this.mDogId = mDogId;
    }

    @Override
    public void sync() throws InterruptedException, ExecutionException, TimeoutException, JSONException {

        JSONObject jsonObject;

        switch (mCalendarReminder.mNeedsSync) {

            case Tag.FLAG_CREATED:

                Log.d(TAG, "FLAG_CREATED Uploading the new alarm to the server");

                jsonObject = create();
                mCalendarReminder.mCalendarReminderId = jsonObject.getInt(CalendarReminder.COLUMN_CALENDAR_REMINDER_ID);
                mCalendarReminder.refresh();
                mCalendarReminder.setAlarm(mContext);

                break;

            case Tag.FLAG_UPDATED:

                Log.d(TAG, "FLAG_UPDATED Uploading the updated alarm to the server");

                jsonObject = update();
                CalendarReminder.saveReminder(jsonObject, mContext);

                break;

            case Tag.FLAG_DELETED:

                Log.d(TAG, "FLAG_DELETED Deleting the alarm with the server");

                jsonObject = delete();

                if (jsonObject.getBoolean("success")) {

                    mCalendarReminder.delete();
                }

                break;
        }
    }

    @Override
    public JSONObject refresh() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        Map<String, String> params = new HashMap<>();
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDogId));

            String address = RequestManager.ADDRESS_CALENDAR_REMINDERS;
        String token = PrefsManager.getUserToken(mContext);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        LaikaRequest request = (LaikaRequest) RequestManager.getRequest(params, address, future, errorListener, token);

        request.setDeviceId(mContext);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);

    }

    @Override
    protected JSONObject create() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = mCalendarReminder.getJsonObject();

            String address = RequestManager.ADDRESS_CALENDAR_REMINDERS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        LaikaRequest request = (LaikaRequest) RequestManager.postRequest(jsonObject, address, future, errorListener, token);

        request.setDeviceId(mContext);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);
    }

    @Override
    protected JSONObject update() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = mCalendarReminder.getJsonObject();

            String address = RequestManager.ADDRESS_CALENDAR_REMINDERS + mCalendarReminder.mCalendarReminderId;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        LaikaRequest request = (LaikaRequest) RequestManager.putRequest(jsonObject, address, future, errorListener, token);

        request.setDeviceId(mContext);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(10, TimeUnit.SECONDS);
    }


    @Override
    protected JSONObject delete() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

            String address = RequestManager.ADDRESS_CALENDAR_REMINDERS + mCalendarReminder.mCalendarReminderId;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        LaikaRequest request = (LaikaRequest) RequestManager.deleteRequest(null, address, future, errorListener, token);

        request.setDeviceId(mContext);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(10, TimeUnit.SECONDS);

    }
}
