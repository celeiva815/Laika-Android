package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.utils.DB;

@Table(name = Tip.TABLE_NAME)
public class Tip extends Model {

    public static int ID = 1;

	public static final String TABLE_NAME = "tips";
    public static final String COLUMN_TIP_ID = "tip_id";
    public static final String COLUMN_SPONSOR_ID = "sponsor_id";
    public static final String COLUMN_SPONSOR_NAME = "sponsor_name";
    public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_BODY = "body";
    public static final String COLUMN_URL_IMAGE = "url_image";
    public static final String COLUMN_URL_TIP = "url_tip";
	public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_IS_PAID = "is_paid";

    public final static String API_TIPS = "tips";
    public final static String API_ID = "id";
    public final static String API_LAST_TIP_ID = "last_tip_id";
    public final static String API_LIMIT = "limit";

    @Column(name = COLUMN_TIP_ID)
    public int mTipId;

    @Column(name = COLUMN_SPONSOR_ID)
    public int mSponsorId;

    @Column(name = COLUMN_SPONSOR_NAME)
    public String mSponsorName;

    @Column(name = COLUMN_TITLE)
    public String mTitle;

    @Column(name = COLUMN_BODY)
    public String mBody;

    @Column(name = COLUMN_URL_IMAGE)
    public int mUrlImage; //FIXME

    @Column(name = COLUMN_URL_TIP)
    public String mUrlTip;

	@Column(name = COLUMN_TYPE)
	public int mType;

    @Column(name = COLUMN_IS_PAID)
    public boolean mIsPaid;


	public Tip() { }

    public Tip(int mTipId, int mSponsorId, String mSponsorName, String mTitle, String mBody,
               int mUrlImage, String mUrlTip, int mType, boolean mIsPaid) {

        this.mTipId = mTipId;
        this.mSponsorId = mSponsorId;
        this.mSponsorName = mSponsorName;
        this.mTitle = mTitle;
        this.mBody = mBody;
        this.mUrlImage = mUrlImage;
        this.mUrlTip = mUrlTip;
        this.mType = mType;
        this.mIsPaid = mIsPaid;
    }

    public Tip(JSONObject jsonObject) {

        try {

            this.mTipId = jsonObject.getInt(API_ID);
            this.mSponsorId = 0; //FIXME jsonObject.getInt(COLUMN_SPONSOR_ID); me llega un id null
            this.mSponsorName = ""; //FIXME jsonObject.getString(COLUMN_SPONSOR_NAME);
            this.mTitle = jsonObject.getString(COLUMN_TITLE);
            this.mBody = jsonObject.getString(COLUMN_BODY);
            this.mUrlImage = R.drawable.lk_news_picture_three; //FIXME jsonObject.getString(COLUMN_URL_IMAGE);
            this.mUrlTip = ""; //FIXME jsonObject.getString(COLUMN_URL_TIP);
            this.mType = 0; //FIXME jsonObject.getString(COLUMN_TYPE);
            this.mIsPaid = jsonObject.getBoolean(COLUMN_IS_PAID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void update(Tip tip) {

        this.mTipId = tip.mTipId;
        this.mSponsorId = tip.mSponsorId;
        this.mSponsorName = tip.mSponsorName;
        this.mTitle = tip.mTitle;
        this.mBody = tip.mBody;
        this.mUrlImage = tip.mUrlImage;
        this.mUrlTip = tip.mUrlTip;
        this.mType = tip.mType;
        this.mIsPaid = tip.mIsPaid;

        this.save();
    }

    public static List<Tip> getTips() {

        return new Select().from(Tip.class).execute();

    }

    public static void saveTips(JSONObject jsonObject) {

        try {

            JSONArray jsonTips = jsonObject.getJSONArray(API_TIPS);

            for (int i = 0; i < jsonTips.length(); i++) {

                JSONObject jsonTip = jsonTips.getJSONObject(i);
                Tip tip = new Tip(jsonTip);

                createOrUpdate(tip);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void createOrUpdate(Tip tip) {

        if (!Tip.isSaved(tip)) {
            tip.save();

        } else {
            Tip oldTip = getSingleTip(tip);
            oldTip.update(tip);

        }
    }

    public static Tip getSingleTip(Tip tip) {

        String condition = COLUMN_TIP_ID + DB.EQUALS + tip.mTipId;

        return new Select().from(Tip.class).where(condition).executeSingle();

    }

    public static boolean isSaved(Tip tip) {

        String condition = COLUMN_TIP_ID + DB.EQUALS + tip.mTipId;

        return new Select().from(Tip.class).where(condition).exists();

    }
    
}
