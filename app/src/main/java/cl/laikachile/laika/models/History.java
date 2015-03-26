package cl.laikachile.laika.models;

import android.content.Context;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class History {

    public int mReminderId;
    public int mCategory;
    public int mType;
    public String mTitle;
    public String mDetail;
    public String mDate;
    public String mTime;

    public History(int mReminderId, int mCategory, int mType, String mTitle, String mDetail,
                   String mDate, String mTime) {

        this.mReminderId = mReminderId;
        this.mCategory = mCategory;
        this.mType = mType;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
        this.mTime = mTime;
    }

    public String getDateTime(Context context) {

        //TODO hacer los métodos que dan el tiempo de mejor forma

        return mDate + " a las " + mTime;
    }

}
