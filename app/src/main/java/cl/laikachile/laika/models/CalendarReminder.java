package cl.laikachile.laika.models;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

@Table(name = CalendarReminder.TABLE_NAME)
public class CalendarReminder extends Model {

    public static int ID = 100;

    public final static String TABLE_NAME = "calendar_reminder";
    public final static String COLUMN_CALENDAR_REMINDER = "calendar_reminder_id";
    public final static String COLUMN_TYPE = "type";
    public final static String COLUMN_CATEGORY = "category";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_DETAIL = "detail";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_OWNER_ID = "owner_id";
    public final static String COLUMN_DOG_ID = "dog_id";

    @Column(name = COLUMN_CALENDAR_REMINDER)
    public int mCalendarReminderId;

    @Column(name = COLUMN_TYPE)
    public int mType;

    @Column(name = COLUMN_CATEGORY)
    public int mCategory;

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

    public CalendarReminder(int mCalendarReminderId, int mType, int mCategory, String mTitle,
                            String mDetail, String mDate, String mTime, int mOwnerId, int mDogId) {

        this.mCalendarReminderId = mCalendarReminderId;
        this.mType = mType;
        this.mCategory = mCategory;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mOwnerId = mOwnerId;
        this.mDogId = mDogId;
    }

    public CalendarReminder() { }

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

    public History toHistory(Context context) {

        return new History(mCategory, mType, mTitle, mDetail, mDate, mTime);
    }

    public static List<CalendarReminder> getDogReminders(int dogId) {

        String condition = CalendarReminder.COLUMN_DOG_ID + DB._EQUALS_ + dogId;
        List<CalendarReminder> reminders = new Select().from(CalendarReminder.class).where(condition).execute();

        return reminders;
    }

}
