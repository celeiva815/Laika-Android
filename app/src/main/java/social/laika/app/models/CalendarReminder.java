package social.laika.app.models;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import social.laika.app.R;
import social.laika.app.interfaces.Alertable;
import social.laika.app.utils.AlarmReceiver;
import social.laika.app.utils.DB;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

@Table(name = CalendarReminder.TABLE_CALENDAR_REMINDERS)
public class CalendarReminder extends ModelSync implements Alertable {

    public final static int ID_NOT_SET = 0;

    public final static String TABLE_CALENDAR_REMINDERS = "calendar_reminders";
    public final static String COLUMN_CALENDAR_REMINDER_ID = "calendar_reminder_id";
    public final static String COLUMN_TYPE = "type";
    public final static String COLUMN_CATEGORY = "category";
    public final static String COLUMN_STATUS = "status";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_DETAIL = "detail";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_OWNER_ID = "owner_id";
    public final static String COLUMN_DOG_ID = "dog_id";

    public final static String LOCAL_ID = "local_id";
    public final static String USER_ID = "user";

    public final static String API_USER_ID = "user_id";

    @Column(name = COLUMN_CALENDAR_REMINDER_ID, unique = true)
    public int mCalendarReminderId;

    @Column(name = COLUMN_TYPE)
    public int mType;

    @Column(name = COLUMN_CATEGORY)
    public int mCategory;

    @Column(name = COLUMN_STATUS)
    public int mStatus;

    @Column(name = COLUMN_TITLE)
    public String mTitle;

    @Column(name = COLUMN_DETAIL)
    public String mDetail;

    @Column(name = COLUMN_DATE)
    public String mDate;

    @Column(name = COLUMN_TIME)
    public String mTime;

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_DOG_ID)
    public int mDogId;

    public static AlarmManager mAlarmManager;

    public CalendarReminder(int mType, int mCategory, String mTitle, String mDetail, String mDate,
                            String mTime, int mOwnerId, int mDogId) {

        this.mType = mType;
        this.mCategory = mCategory;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mOwnerId = mOwnerId;
        this.mDogId = mDogId;
        this.mStatus = Tag.STATUS_NOT_ACTIVATED;
    }

    public CalendarReminder(JSONObject jsonObject, Context context) {

        this.mCalendarReminderId = jsonObject.optInt(COLUMN_CALENDAR_REMINDER_ID);
        this.mType = jsonObject.optInt(COLUMN_TYPE, Tag.TYPE_CALENDAR);
        this.mCategory = jsonObject.optInt(COLUMN_CATEGORY);
        this.mTitle = jsonObject.optString(COLUMN_TITLE);
        this.mDetail = jsonObject.optString(COLUMN_DETAIL);
        this.mDate = jsonObject.optString(COLUMN_DATE);
        this.mTime = jsonObject.optString(COLUMN_TIME);
        this.mOwnerId = jsonObject.optInt(API_USER_ID, PrefsManager.getUserId(context));
        this.mDogId = jsonObject.optInt(COLUMN_DOG_ID);
        this.mNeedsSync = Tag.FLAG_READED;
        this.mStatus = Tag.STATUS_NOT_ACTIVATED;
    }

    public CalendarReminder() { }

    public JSONObject getJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(COLUMN_DOG_ID, this.mDogId);
            jsonObject.put(COLUMN_CALENDAR_REMINDER_ID, getCalendarJsonObject());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    public JSONObject getCalendarJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            if (mCalendarReminderId > ID_NOT_SET) {
                jsonObject.put(COLUMN_CALENDAR_REMINDER_ID, this.mCalendarReminderId);
            }

            jsonObject.put(COLUMN_CATEGORY, mCategory);
            jsonObject.put(COLUMN_TITLE, mTitle);
            jsonObject.put(COLUMN_DETAIL, mDetail);
            jsonObject.put(COLUMN_DATE, mDate);
            jsonObject.put(COLUMN_TIME, mTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    private void update(CalendarReminder calendarReminder ) {

        this.mCalendarReminderId = calendarReminder.mCalendarReminderId;
        this.mType = calendarReminder.mType;
        this.mCategory = calendarReminder.mCategory;
        this.mTitle = calendarReminder.mTitle;
        this.mDetail = calendarReminder.mDetail;
        this.mDate = calendarReminder.mDate;
        this.mTime = calendarReminder.mTime;
        this.mOwnerId = calendarReminder.mOwnerId;
        this.mDogId = calendarReminder.mDogId;
        this.mNeedsSync = calendarReminder.mNeedsSync;
        this.mStatus = calendarReminder.mStatus;

        this.save();

    }

    @Override
    public int getServerId() { return mCalendarReminderId; }

    public int getImageResource() {

        switch (this.mCategory) {

            case Tag.CATEGORY_VACCINE:

                return R.drawable.lk_food_tips;

            case Tag.CATEGORY_HYGIENE:

                return R.drawable.lk_hygiene_tips;

            case Tag.CATEGORY_VET:

                return R.drawable.lk_hygiene_tips;

        }

        return R.drawable.lk_food_tips;
    }

    public String getCategoryName(Context context) {

        switch (this.mCategory) {

            case Tag.CATEGORY_VACCINE:

                return Do.getRString(context, R.string.medicine_my_dog);

            case Tag.CATEGORY_HYGIENE:

                return Do.getRString(context, R.string.hygiene_my_dog);

            case Tag.CATEGORY_VET:

                return Do.getRString(context, R.string.vet_my_dog);
        }

        return Do.getRString(context, R.string.food_my_dog);
    }

    public String getDate(Context context) {

        //TODO implementar este método de mejor manera

        return mDate + " " + mTime;

    }

    @Override
    public History toHistory(Context context) {

        return new History(mCalendarReminderId, mCategory, mType, mTitle, mDetail, mDate, mTime, this);
    }

    @Override
    public void setAlarm(Context context) {

        mStatus = Tag.STATUS_ACTIVATED;
        Intent intent = getAlarmIntent(context);
        int requestCode = getAlarmRequestCode();
        Calendar calendar = getAlarmCalendar();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, 0);

        if (mAlarmManager == null) {
            mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);

    }

    @Override
    public void cancelAlarm(Context context) {

        mStatus = Tag.STATUS_NOT_ACTIVATED;
        Intent intent = getAlarmIntent(context);
        int requestCode = getAlarmRequestCode();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (mAlarmManager == null) {
            mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        mAlarmManager.cancel(pendingIntent);


    }

    @Override
    public int checkStatusAlarm(Context context) {

        Intent intent = getAlarmIntent(context);
        boolean alarmUp = (PendingIntent.getBroadcast(context, getAlarmRequestCode(),
                intent, PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp) {
            Log.i("ID:" + mCalendarReminderId + " Date:" + getDate(context), "Cool!! Alarm is already active");

        } else {

            Log.wtf("ID:" + mCalendarReminderId + " Date:" + getDate(context), "WTF!! Alarm is not active");
        }

        mStatus = alarmUp ? Tag.STATUS_ACTIVATED : Tag.STATUS_NOT_ACTIVATED;

        return mStatus;

    }

    private Intent getAlarmIntent(Context context) {

        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra(LOCAL_ID, this.getId());
        intent.putExtra(COLUMN_DATE, this.mDate);
        intent.putExtra(COLUMN_TIME, this.mTime);
        intent.putExtra(COLUMN_TYPE, Tag.TYPE_CALENDAR);
        intent.putExtra(USER_ID, PrefsManager.getUserId(context));

        return intent;
    }

    private int getAlarmRequestCode() {

        return (int) ((getId() * 100) + (Tag.TYPE_CALENDAR * 10));

    }

    private Calendar getAlarmCalendar() {

        Calendar calendar = Calendar.getInstance();

        String dateTime = mDate + " " + mTime;
        Date date = Do.stringToDate(dateTime, Do.DAY_FIRST + " " + Do.HOUR_MINUTE);

        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    // DATABASE METHODS

    public static void saveReminders(JSONObject jsonObject, Context context) {

        if (jsonObject.has(TABLE_CALENDAR_REMINDERS)) {

            try {
                JSONArray jsonReminders = jsonObject.getJSONArray(TABLE_CALENDAR_REMINDERS);

                for (int i = 0; i < jsonReminders.length(); i++) {
                    saveReminder(jsonReminders.getJSONObject(i), context);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static CalendarReminder saveReminder(JSONObject jsonReminder, Context context) {

        CalendarReminder reminder = new CalendarReminder(jsonReminder, context);
        createOrUpdate(reminder, context);

        return reminder;
    }

    public static void createOrUpdate(CalendarReminder reminder, Context context) {

        reminder.mNeedsSync = Tag.FLAG_READED;

        if (!CalendarReminder.isSaved(reminder)) {

            reminder.save();
            reminder.setAlarm(context);

        } else {
            CalendarReminder oldReminder = getSingleReminder(reminder.mCalendarReminderId);

            oldReminder.cancelAlarm(context);
            oldReminder.update(reminder);
            oldReminder.setAlarm(context);
        }
    }

    public static boolean isSaved(CalendarReminder reminder) {

        String condition = CalendarReminder.COLUMN_CALENDAR_REMINDER_ID + DB.EQUALS + reminder.mCalendarReminderId;
        return new Select().from(CalendarReminder.class).where(condition).exists();
    }


    public static List<CalendarReminder> getDogReminders(int dogId) {

        String condition = CalendarReminder.COLUMN_DOG_ID + DB.EQUALS + dogId;
        condition += DB.AND + AlarmReminder.COLUMN_NEEDS_SYNC + DB.NOT_EQUALS + Tag.FLAG_DELETED;
        return new Select().from(CalendarReminder.class).where(condition).execute();
    }

    public static List<CalendarReminder> getNeedSync() {

        String condition = CalendarReminder.COLUMN_NEEDS_SYNC + DB.GREATER_THAN + Tag.FLAG_READED;
        return new Select().from(CalendarReminder.class).where(condition).execute();
    }

    public static CalendarReminder getSingleReminder(int reminderId) {

        String condition = CalendarReminder.COLUMN_CALENDAR_REMINDER_ID + DB.EQUALS + reminderId;
        return new Select().from(CalendarReminder.class).where(condition).executeSingle();
    }

    public static void deleteAll() {

        new Delete().from(CalendarReminder.class).execute();

    }
}
