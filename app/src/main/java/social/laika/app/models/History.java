package social.laika.app.models;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Date;

import social.laika.app.interfaces.Alertable;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class History implements Comparable<History> {

    public long mReminderId;
    public int mCategory;
    public int mType;
    public String mTitle;
    public String mDetail;
    public String mDate;
    public String mTime;
    public Alertable mReminder;

    public History(long mReminderId, int mCategory, int mType, String mTitle, String mDetail,
                   String mDate, String mTime, Alertable mReminder) {

        this.mReminderId = mReminderId;
        this.mCategory = mCategory;
        this.mType = mType;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mReminder = mReminder;

    }

    public String getDateTime(Context context) {

        //TODO hacer los métodos que dan el tiempo de mejor forma

        return mDate + " a las " + mTime;
    }


    @Override
    public int compareTo(@NonNull History another) {
        return mReminder.createdAt().compareTo(another.mReminder.createdAt());
    }
}
