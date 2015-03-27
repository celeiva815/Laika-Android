package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import cl.laikachile.laika.utils.Do;

@Table(name = Event.TABLE_NAME)
public class Event extends Model {

	public final static String TABLE_NAME = "events";
	public final static String COLUMN_NAME = "name";
	public final static String COLUMN_SPONSOR_NAME = "sponsor_name";
    public final static String COLUMN_URL_IMAGE= "url_image";
    public final static String COLUMN_URL_EVENT= "url_event";
    public final static String COLUMN_LOCATION = "location";
	public final static String COLUMN_START_DATE = "start_date";
	public final static String COLUMN_FINISH_DATE = "finish_date";
    public final static String COLUMN_START_TIME = "start_time";
    public final static String COLUMN_FINISH_TIME = "finish_time";
	public final static String COLUMN_IS_ANNOUNCE = "is_announce";
	
	@Column(name = COLUMN_NAME)
	public String mName;
	
	@Column(name = COLUMN_SPONSOR_NAME)
    public String mSponsorName;

    @Column(name = COLUMN_URL_IMAGE)
 	public int mURLImage; //FIXME aqui tiene que haber un string

    @Column(name = COLUMN_URL_EVENT)
    public String mUrlEvent;

    @Column(name = COLUMN_LOCATION)
    public String mLocation;
	
	@Column(name = COLUMN_START_DATE)
	public String mStartDate;
	
	@Column(name = COLUMN_FINISH_DATE)
	public String mFinishDate;

    @Column(name = COLUMN_START_TIME)
    public String mStartTime;

    @Column(name = COLUMN_FINISH_TIME)
    public String mFinishTime;
	
	@Column(name = COLUMN_IS_ANNOUNCE)
	public boolean mIsAnnounce;

    public Event() { }

    public Event(String mName, String mSponsorName, int mURLImage, String mUrlEvent,
                 String mLocation, String mStartDate, String mFinishDate, String mStartTime,
                 String mFinishTime, boolean mIsAnnounce) {

        this.mName = mName;
        this.mSponsorName = mSponsorName;
        this.mURLImage = mURLImage;
        this.mUrlEvent = mUrlEvent;
        this.mLocation = mLocation;
        this.mStartDate = mStartDate;
        this.mFinishDate = mFinishDate;
        this.mStartTime = mStartTime;
        this.mFinishTime = mFinishTime;
        this.mIsAnnounce = mIsAnnounce;
    }

    public String getDate() {

        if (Do.isNullOrEmpty(mFinishDate)){
            return mStartDate;

        } else {
            return "del " + mStartDate + " al " + mFinishDate;
        }
    }

    public String getTime() {
        return mStartTime + " - " + mFinishTime;

    }

}
