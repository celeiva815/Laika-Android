package cl.laikachile.laika.models;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.utils.AlarmReceiver;
import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.DateFormatter;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

@Table(name = AlarmReminder.TABLE_NAME)
public class AlarmReminder extends Model {

    public final static int ID_NOT_SET = 0;

    public final static String TABLE_NAME = "alarm_reminder";
    public final static String COLUMN_ALARM_REMINDER_ID = "alarm_reminder_id";
    public final static String COLUMN_TYPE = "type";
    public final static String COLUMN_CATEGORY = "category";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_DETAIL = "detail";
    public final static String COLUMN_STATUS = "status";
    public final static String COLUMN_HAS_MONDAY = "has_monday";
    public final static String COLUMN_HAS_TUESDAY = "has_tuesday";
    public final static String COLUMN_HAS_WEDNESDAY = "has_wednesday";
    public final static String COLUMN_HAS_THURSDAY = "has_thursday";
    public final static String COLUMN_HAS_FRIDAY = "has_friday";
    public final static String COLUMN_HAS_SATURDAY = "has_saturday";
    public final static String COLUMN_HAS_SUNDAY = "has_sunday";
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_OWNER_ID = "owner_id";
    public final static String COLUMN_DOG_ID = "dog_id";

    public final static String LOCAL_ID = "local_id";
    public final static String WEEKDAY = "weekday";
    public final static String USER_ID = "user";

    public static int ID = 1;
    public final static String API_ALERT_REMINDERS = "alert_reminders";
    public final static String API_USER_ID = "user_id";

    @Column(name = COLUMN_ALARM_REMINDER_ID)
    public int mAlarmReminderId;

    @Column(name = COLUMN_TYPE)
    public int mType;

    @Column(name = COLUMN_CATEGORY)
    public int mCategory;

    @Column(name = COLUMN_TITLE)
    public String mTitle;

    @Column(name = COLUMN_DETAIL)
    public String mDetail;

    @Column(name = COLUMN_STATUS)
    public int mStatus;

    @Column(name = COLUMN_HAS_MONDAY)
    public boolean mHasMonday;

    @Column(name = COLUMN_HAS_TUESDAY)
    public boolean mHasTuesday;

    @Column(name = COLUMN_HAS_WEDNESDAY)
    public boolean mHasWednesday;

    @Column(name = COLUMN_HAS_THURSDAY)
    public boolean mHasThursday;

    @Column(name = COLUMN_HAS_FRIDAY)
    public boolean mHasFriday;

    @Column(name = COLUMN_HAS_SATURDAY)
    public boolean mHasSaturday;

    @Column(name = COLUMN_HAS_SUNDAY)
    public boolean mHasSunday;

    @Column(name = COLUMN_TIME)
    public String mTime;

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_DOG_ID)
    public int mDogId;
    
    public static AlarmManager mAlarmManager;

    public AlarmReminder(int mType, int mCategory, String mTitle,
                         String mDetail, int mStatus, boolean mHasMonday, boolean mHasTuesday,
                         boolean mHasWednesday, boolean mHasThursday, boolean mHasFriday,
                         boolean mHasSaturday, boolean mHasSunday, String mTime, int mOwnerId,
                         int mDogId) {

        this.mType = mType;
        this.mCategory = mCategory;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mStatus = mStatus;
        this.mHasMonday = mHasMonday;
        this.mHasTuesday = mHasTuesday;
        this.mHasWednesday = mHasWednesday;
        this.mHasThursday = mHasThursday;
        this.mHasFriday = mHasFriday;
        this.mHasSaturday = mHasSaturday;
        this.mHasSunday = mHasSunday;
        this.mTime = mTime;
        this.mOwnerId = mOwnerId;
        this.mDogId = mDogId;
    }

    public AlarmReminder(JSONObject jsonObject, int mDogId, Context context) {

        this.mType = jsonObject.optInt(COLUMN_TYPE, Tag.TYPE_ALARM);
        this.mCategory = jsonObject.optInt(COLUMN_CATEGORY, Tag.CATEGORY_FOOD);
        this.mTitle = jsonObject.optString(COLUMN_TITLE);
        this.mDetail = jsonObject.optString(COLUMN_DETAIL);
        this.mStatus = jsonObject.optInt(COLUMN_STATUS, Tag.STATUS_IN_PROGRESS);
        this.mHasMonday = jsonObject.optBoolean(COLUMN_HAS_MONDAY);
        this.mHasTuesday = jsonObject.optBoolean(COLUMN_HAS_TUESDAY);
        this.mHasWednesday = jsonObject.optBoolean(COLUMN_HAS_WEDNESDAY);
        this.mHasThursday = jsonObject.optBoolean(COLUMN_HAS_THURSDAY);
        this.mHasFriday = jsonObject.optBoolean(COLUMN_HAS_FRIDAY);
        this.mHasSaturday = jsonObject.optBoolean(COLUMN_HAS_SATURDAY);
        this.mHasSunday = jsonObject.optBoolean(COLUMN_HAS_SUNDAY);
        this.mTime = jsonObject.optString(COLUMN_TIME);
        this.mOwnerId = jsonObject.optInt(API_USER_ID, PrefsManager.getUserId(context));
        this.mDogId = jsonObject.optInt(COLUMN_DOG_ID, mDogId);
    }

    public AlarmReminder() {
    }

    public JSONObject getJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(COLUMN_DOG_ID, this.mDogId);
            jsonObject.put(TABLE_NAME, getAlarmJsonObject());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    public JSONObject getAlarmJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            if (mAlarmReminderId > ID_NOT_SET) {
                jsonObject.put(COLUMN_ALARM_REMINDER_ID, this.mAlarmReminderId);
            }

            jsonObject.put(COLUMN_TYPE, mType);
            jsonObject.put(COLUMN_CATEGORY, mCategory);
            jsonObject.put(COLUMN_TITLE, mTitle);
            jsonObject.put(COLUMN_DETAIL, mDetail);
            jsonObject.put(COLUMN_STATUS, mStatus);
            jsonObject.put(COLUMN_HAS_MONDAY, mHasMonday);
            jsonObject.put(COLUMN_HAS_TUESDAY, mHasTuesday);
            jsonObject.put(COLUMN_HAS_WEDNESDAY, mHasWednesday);
            jsonObject.put(COLUMN_HAS_THURSDAY, mHasThursday);
            jsonObject.put(COLUMN_HAS_FRIDAY, mHasFriday);
            jsonObject.put(COLUMN_HAS_SATURDAY, mHasSaturday);
            jsonObject.put(COLUMN_HAS_SUNDAY, mHasSunday);
            jsonObject.put(COLUMN_TIME, mTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    public void update(AlarmReminder alarmReminder) {

        this.mType = alarmReminder.mType;
        this.mCategory = alarmReminder.mCategory;
        this.mTitle = alarmReminder.mTitle;
        this.mDetail = alarmReminder.mDetail;
        this.mStatus = alarmReminder.mStatus;
        this.mHasMonday = alarmReminder.mHasMonday;
        this.mHasTuesday = alarmReminder.mHasTuesday;
        this.mHasWednesday = alarmReminder.mHasWednesday;
        this.mHasThursday = alarmReminder.mHasThursday;
        this.mHasFriday = alarmReminder.mHasFriday;
        this.mHasSaturday = alarmReminder.mHasSaturday;
        this.mHasSunday = alarmReminder.mHasSunday;
        this.mTime = alarmReminder.mTime;
        this.mOwnerId = alarmReminder.mOwnerId;
        this.mDogId = alarmReminder.mDogId;

        this.save();

    }

    public int getImageResource() {

        switch (this.mCategory) {

            case Tag.CATEGORY_FOOD:

                return R.drawable.lk_food_tips;

            case Tag.CATEGORY_MEDICINE:

                return R.drawable.lk_health_tips;

            case Tag.CATEGORY_POO:

                return R.drawable.lk_hygiene_tips;

            case Tag.CATEGORY_WALK:

                return R.drawable.lk_walk_tips;

        }

        return R.drawable.lk_food_tips;
    }


    public String getCategoryName(Context context) {

        switch (this.mCategory) {

            case Tag.CATEGORY_FOOD:

                return Do.getRString(context, R.string.food_my_dog);

            case Tag.CATEGORY_MEDICINE:

                return Do.getRString(context, R.string.medicine_my_dog);

            case Tag.CATEGORY_POO:

                return Do.getRString(context, R.string.poo_my_dog);

            case Tag.CATEGORY_WALK:

                return Do.getRString(context, R.string.walk_my_dog);

        }

        return Do.getRString(context, R.string.food_my_dog);
    }

    public String toDate(Context context) {

        if (mHasMonday && mHasTuesday && mHasWednesday && mHasThursday && mHasFriday && mHasSaturday
                && mHasSunday) {

            String every = Do.getRString(context, R.string.all_the_date);
            String days = Do.getRString(context, R.string.days_date);

            return every + " " + days;
        }

        if (mHasMonday && mHasTuesday && mHasWednesday && mHasThursday && mHasFriday && !mHasSaturday
                && !mHasSunday) {

            String every = Do.getRString(context, R.string.all_the_date);
            String days = Do.getRString(context, R.string.weekdays_date);

            return every + " " + days;
        }

        if (!mHasMonday && !mHasTuesday && !mHasWednesday && !mHasThursday && !mHasFriday && mHasSaturday
                && mHasSunday) {

            String every = Do.getRString(context, R.string.all_the_date);
            String days = Do.getRString(context, R.string.weekend_date);

            return every + " " + days;
        }

        int countDays = 0;

        if (mHasMonday) {
            countDays++;
        }

        if (mHasTuesday) {
            countDays++;
        }

        if (mHasWednesday) {
            countDays++;
        }

        if (mHasThursday) {
            countDays++;
        }

        if (mHasFriday) {
            countDays++;
        }

        if (mHasSaturday) {
            countDays++;
        }

        if (mHasSunday) {
            countDays++;
        }

        int addedDays = 0;
        String date = "";

        if (mHasMonday) {

            if (countDays == 1) {

                date += Do.getRString(context, R.string.all_the_date) + " ";
                date += Do.getRString(context, R.string.monday_date);

                return date;

            } else {

                date += Do.getRString(context, R.string.monday_date) + ", ";
                addedDays++;
            }
        }

        if (mHasTuesday) {

            if (countDays == 1) {

                date += Do.getRString(context, R.string.all_the_date) + " ";
                date += Do.getRString(context, R.string.tuesday_date);

                return date;

            } else {

                if (addedDays == countDays) {

                    date += Do.getRString(context, R.string.and_date) + " ";
                    date += Do.getRString(context, R.string.tuesday_date);

                    return date;

                } else {

                    date += Do.getRString(context, R.string.tuesday_date) + ", ";
                    addedDays++;
                }

            }
        }

        if (mHasWednesday) {

            if (countDays == 1) {

                date += Do.getRString(context, R.string.all_the_date) + " ";
                date += Do.getRString(context, R.string.wednesday_date);

                return date;

            } else {

                if (addedDays == countDays) {

                    date += Do.getRString(context, R.string.and_date) + " ";
                    date += Do.getRString(context, R.string.wednesday_date);

                    return date;

                } else {

                    date += Do.getRString(context, R.string.wednesday_date) + ", ";
                    addedDays++;
                }

            }
        }

        if (mHasThursday) {

            if (countDays == 1) {

                date += Do.getRString(context, R.string.all_the_date) + " ";
                date += Do.getRString(context, R.string.thursday_date);

                return date;

            } else {

                if (addedDays == countDays) {

                    date += Do.getRString(context, R.string.and_date) + " ";
                    date += Do.getRString(context, R.string.thursday_date);

                    return date;

                } else {

                    date += Do.getRString(context, R.string.thursday_date) + ", ";
                    addedDays++;
                }

            }
        }

        if (mHasFriday) {

            if (countDays == 1) {

                date += Do.getRString(context, R.string.all_the_date) + " ";
                date += Do.getRString(context, R.string.friday_date);

                return date;

            } else {

                if (addedDays == countDays) {

                    date += Do.getRString(context, R.string.and_date) + " ";
                    date += Do.getRString(context, R.string.friday_date);

                    return date;

                } else {

                    date += Do.getRString(context, R.string.friday_date) + ", ";
                    addedDays++;
                }

            }
        }

        if (mHasSaturday) {

            if (countDays == 1) {

                date += Do.getRString(context, R.string.all_the_date) + " ";
                date += Do.getRString(context, R.string.saturday_date);

                return date;

            } else {

                if (addedDays == countDays) {

                    date += Do.getRString(context, R.string.and_date) + " ";
                    date += Do.getRString(context, R.string.saturday_date);

                    return date;

                } else {

                    date += Do.getRString(context, R.string.saturday_date) + ", ";
                    addedDays++;
                }

            }
        }

        if (mHasSunday) {

            if (countDays == 1) {

                date += Do.getRString(context, R.string.all_the_date) + " ";
                date += Do.getRString(context, R.string.friday_date);

                return date;

            } else {

                date += Do.getRString(context, R.string.and_date) + " ";
                date += Do.getRString(context, R.string.friday_date);

                return date;
            }
        }

        return date;
    }

    public History toHistory(Context context) {

        return new History(mAlarmReminderId, mCategory, mType, mTitle, mDetail, toDate(context),
                mTime);
    }

    public void setAlarm(Context context) {

        int[] time = DateFormatter.parseTimeFromString(mTime);
        int hour = time[0];
        int minutes = time[1];

        if (mHasMonday) {
            setAlarm(context, Calendar.MONDAY, hour, minutes);
        }

        if (mHasTuesday) {
            setAlarm(context, Calendar.TUESDAY, hour, minutes);
        }

        if (mHasWednesday) {
            setAlarm(context, Calendar.WEDNESDAY, hour, minutes);
        }

        if (mHasThursday) {
            setAlarm(context, Calendar.THURSDAY, hour, minutes);
        }

        if (mHasFriday) {
            setAlarm(context, Calendar.FRIDAY, hour, minutes);
        }

        if (mHasSaturday) {
            setAlarm(context, Calendar.SATURDAY, hour, minutes);
        }

        if (mHasSunday) {
            setAlarm(context, Calendar.SUNDAY, hour, minutes);
        }
    }

    public void setAlarm(Context context, int i) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 5 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60, pi);
    }


    public void setAlarm(Context context, int weekday, int hour, int minutes) {

        Intent intent = getAlarmIntent(context, weekday);
        int requestCode = getAlarmRequestCode(weekday);
        Calendar calendar = getAlarmCalendar(weekday,hour,minutes);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, 0);
        
        if (mAlarmManager == null) {
            mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                Do.WEEK * Do.HOURS * Do.MINUTES * Do.SECONDS * Do.MILLIS, pendingIntent);

    }

    public void cancelAlarm(Context context, int weekday, int hour, int minutes) {

        Intent intent = getAlarmIntent(context, weekday);
        int requestCode = getAlarmRequestCode(weekday);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (mAlarmManager == null) {
            mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        mAlarmManager.cancel(pendingIntent);
    }

    public void updateAlarm(Context context, int oldWeekday, int oldHour, int oldMinutes,
                            int newWeekday, int newHour, int newMinutes) {

        cancelAlarm(context, oldWeekday, oldHour, oldMinutes);
        setAlarm(context, newWeekday, newHour, newMinutes);

    }

    public Intent getAlarmIntent(Context context, int weekday) {

        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra(LOCAL_ID, this.getId());
        intent.putExtra(COLUMN_TIME, this.mTime);
        intent.putExtra(WEEKDAY, weekday);
        intent.putExtra(USER_ID, PrefsManager.getUserId(context));

        return intent;
    }

    public int getAlarmRequestCode(int weekday) {

        return (int) (getId() * 10 + weekday);

    }

    public Calendar getAlarmCalendar(int weekday, int hour, int minutes) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, weekday);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    // DATABASE METHODS

    public static void saveReminders(JSONObject jsonObject, int dogId, Context context) {

        try {
            JSONArray jsonReminders = jsonObject.getJSONArray(API_ALERT_REMINDERS);

            for (int i = 0; i < jsonReminders.length(); i++) {
                saveReminder(jsonReminders.getJSONObject(i), dogId, context);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static AlarmReminder saveReminder(JSONObject jsonReminder, int dogId, Context context) {

        AlarmReminder reminder = new AlarmReminder(jsonReminder, dogId, context);
        createOrUpdate(reminder);

        return reminder;

    }

    public static void createOrUpdate(AlarmReminder reminder) {

        if (!AlarmReminder.isSaved(reminder)) {
            reminder.save();

        } else {
            AlarmReminder oldReminder = getSingleReminder(reminder.mAlarmReminderId);
            oldReminder.update(reminder);

        }
    }

    public static boolean isSaved(AlarmReminder reminder) {

        String condition = AlarmReminder.COLUMN_ALARM_REMINDER_ID + DB.EQUALS + reminder.mAlarmReminderId;
        return new Select().from(AlarmReminder.class).where(condition).exists();
    }

    public static List<AlarmReminder> getDogReminders(int dogId) {

        String condition = AlarmReminder.COLUMN_DOG_ID + DB.EQUALS + dogId;
        return new Select().from(AlarmReminder.class).where(condition).execute();
    }

    public static List<AlarmReminder> getAllReminders() {

        return new Select().from(AlarmReminder.class).execute();
    }

    public static AlarmReminder getSingleReminder(int reminderId) {

        String condition = AlarmReminder.COLUMN_ALARM_REMINDER_ID + DB.EQUALS + reminderId;
        return new Select().from(AlarmReminder.class).where(condition).executeSingle();
    }

    public static void deleteAll() {

        new Delete().from(AlarmReminder.class).execute();

    }
}
