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
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.utils.AlarmReceiver;
import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.DateFormatter;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

@Table(name = AlarmReminder.TABLE_NAME)
public class AlarmReminder extends Model {

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

    public AlarmReminder(int mAlarmReminderId, int mType, int mCategory, String mTitle,
                         String mDetail, int mStatus, boolean mHasMonday, boolean mHasTuesday,
                         boolean mHasWednesday, boolean mHasThursday, boolean mHasFriday,
                         boolean mHasSaturday, boolean mHasSunday, String mTime, int mOwnerId,
                         int mDogId) {

        this.mAlarmReminderId = mAlarmReminderId;
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

    public AlarmReminder() { }
	
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

        if(mHasMonday) { countDays++; }

        if(mHasTuesday) { countDays++; }

        if(mHasWednesday){ countDays++; }

        if(mHasThursday) { countDays++; }

        if(mHasFriday) { countDays++; }

        if(mHasSaturday) { countDays++; }

        if(mHasSunday) { countDays++; }

        int addedDays = 0;
        String date = "";

        if(mHasMonday) {

            if (countDays == 1) {

                date += Do.getRString(context, R.string.all_the_date) + " ";
                date += Do.getRString(context, R.string.monday_date);

                return date;

            } else {

                date += Do.getRString(context, R.string.monday_date) + ", ";
                addedDays++;
            }
        }

        if(mHasTuesday) {

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

        if(mHasWednesday) {

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

        if(mHasThursday) {

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

        if(mHasFriday) {

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

        if(mHasSaturday) {

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
            setIntentAlarm(context, Calendar.MONDAY, hour, minutes);
        }

        if (mHasTuesday) {
            setIntentAlarm(context, Calendar.TUESDAY, hour, minutes);
        }

        if (mHasWednesday) {
            setIntentAlarm(context, Calendar.WEDNESDAY, hour, minutes);
        }

        if (mHasThursday) {
            setIntentAlarm(context, Calendar.THURSDAY, hour, minutes);
        }

        if (mHasFriday) {
            setIntentAlarm(context, Calendar.FRIDAY, hour, minutes);
        }

        if (mHasSaturday) {
            setIntentAlarm(context, Calendar.SATURDAY, hour, minutes);
        }

        if (mHasSunday) {
            setIntentAlarm(context, Calendar.SUNDAY, hour, minutes);
        }
    }

    public void setAlarm(Context context, int i)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 5 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 , pi);
    }


    public void setIntentAlarm(Context context, int week, int hour, int minutes) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, week);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra(LOCAL_ID, this.getId());
        intent.putExtra(COLUMN_TIME, this.mTime);
        intent.putExtra(WEEKDAY, week);
        intent.putExtra(USER_ID, PrefsManager.getUserId(context));

        long id = getId();
        int requestCode = (int) id * 10 + week;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                Do.HOURS * Do.MINUTES * Do.SECONDS * Do.MILLIS, pendingIntent);

    }

    // DATABASE METHODS

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
