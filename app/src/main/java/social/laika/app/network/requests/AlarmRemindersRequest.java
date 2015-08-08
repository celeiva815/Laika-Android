package social.laika.app.network.requests;

import android.content.Context;

import com.android.volley.Request;
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

import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.network.sync.SyncUtils;
import social.laika.app.responses.AlarmRemindersResponse;
import social.laika.app.responses.RemindersResponse;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 12-06-15.
 */
public class AlarmRemindersRequest extends SyncRequest {

    private static final int CODE_ALARM = SyncUtils.CODE_ALARM_REFRESH;
    private static final int CODE_ALARM_CREATE = SyncUtils.CODE_ALARM_CREATE;
    private static final int CODE_ALARM_READ = SyncUtils.CODE_ALARM_READ;
    private static final int CODE_ALARM_UPDATE = SyncUtils.CODE_ALARM_SYNC;
    private static final int CODE_ALARM_DELETE = SyncUtils.CODE_ALARM_DELETE;
    private static final int CODE_CALENDAR = SyncUtils.CODE_CALENDAR;
    private static final int CODE_CALENDAR_CREATE = SyncUtils.CODE_CALENDAR_CREATE;
    private static final int CODE_CALENDAR_READ = SyncUtils.CODE_CALENDAR_READ;
    private static final int CODE_CALENDAR_UPDATE = SyncUtils.CODE_CALENDAR_UPDATE;
    private static final int CODE_CALENDAR_DELETE = SyncUtils.CODE_CALENDAR_DELETE;

    private static final String TAG = AlarmRemindersRequest.class.getSimpleName();

    AlarmReminder mAlarmReminder;
    int mDogId;

    public AlarmRemindersRequest(Context context, AlarmReminder alarmReminder) {
        super(context);
        mAlarmReminder = alarmReminder;

    }

    public AlarmRemindersRequest(Context mContext, int mDogId) {
        super(mContext);
        this.mDogId = mDogId;
    }

    @Override
    public void sync() throws InterruptedException, ExecutionException, TimeoutException, JSONException {

        JSONObject jsonObject = new JSONObject();

        switch (mAlarmReminder.mNeedsSync) {

            case Tag.FLAG_CREATED:

                jsonObject = create();
                mAlarmReminder.mAlarmReminderId = jsonObject.getInt(AlarmReminder.COLUMN_ALARM_REMINDER_ID);
                mAlarmReminder.save();

                break;

            case Tag.FLAG_UPDATED:

                jsonObject = update();
                AlarmReminder.saveReminder(jsonObject, mContext);

                break;

            case Tag.FLAG_DELETED:

                jsonObject = delete();

                if (jsonObject.getBoolean("success")) {

                    mAlarmReminder.delete();
                }

                break;
        }
    }

    @Override
    public JSONObject refresh() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        Map<String, String> params = new HashMap<>();
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDogId));

        String address = RequestManager.ADDRESS_ALERT_REMINDERS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.getRequest(params, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);

    }

    @Override
    protected JSONObject create() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = mAlarmReminder.getJsonObject();

        String address = RequestManager.ADDRESS_ALERT_REMINDERS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.postRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(10, TimeUnit.SECONDS);
    }

    @Override
    protected JSONObject update() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = mAlarmReminder.getJsonObject();

        String address = RequestManager.ADDRESS_ALERT_REMINDERS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.patchRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(10, TimeUnit.SECONDS);
    }


    @Override
    protected JSONObject delete() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(AlarmReminder.COLUMN_ALARM_REMINDER_ID, mAlarmReminder.mAlarmReminderId);

        String address = RequestManager.ADDRESS_ALERT_REMINDERS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.deleteRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(10, TimeUnit.SECONDS);

    }
}
