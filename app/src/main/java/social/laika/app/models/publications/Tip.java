package social.laika.app.models.publications;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import social.laika.app.utils.DB;

@Table(name = Tip.TABLE_NAME)
public class Tip extends BasePublication {

    public static int ID = 1;

    public static final String TABLE_NAME = "tips";
    public static final String COLUMN_SPONSOR_ID = "sponsor_id";
    public static final String COLUMN_SPONSOR_NAME = "sponsor_name";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_TYPE = "type";

    public static final String API_TIP_ID = "tip_id";
    public final static String API_TIPS = "tips";
    public static final String API_URL_TIP = "url_tip";
    public final static String API_ID = "id";
    public final static String API_LAST_TIP_ID = "last_tip_id";
    public final static String API_LIMIT = "limit";

    @Column(name = COLUMN_SPONSOR_ID)
    public int mSponsorId;

    @Column(name = COLUMN_SPONSOR_NAME)
    public String mSponsorName;

    @Column(name = COLUMN_TITLE)
    public String mTitle;

    @Column(name = COLUMN_BODY)
    public String mBody;

    @Column(name = COLUMN_TYPE)
    public int mType;


    public Tip() {
    }

    public Tip(int mTipId, int mSponsorId, String mSponsorName, String mTitle, String mBody,
               String mUrlImage, String mUrlTip, int mType, boolean mIsPaid) {

        this.mServerId = mTipId;
        this.mSponsorId = mSponsorId;
        this.mSponsorName = mSponsorName;
        this.mTitle = mTitle;
        this.mBody = mBody;
        this.mUrlImage = mUrlImage;
        this.mUrl = mUrlTip;
        this.mType = mType;
        this.mIsPaid = mIsPaid;
    }

    public Tip(JSONObject jsonObject) {

        this.mServerId = jsonObject.optInt(API_ID);
        this.mSponsorId = jsonObject.optInt(COLUMN_SPONSOR_ID);
        this.mSponsorName = jsonObject.optString(COLUMN_SPONSOR_NAME);
        this.mTitle = jsonObject.optString(COLUMN_TITLE);
        this.mBody = jsonObject.optString(COLUMN_BODY);
        this.mUrlImage = jsonObject.optString(COLUMN_URL_IMAGE);
        this.mUrl = jsonObject.optString(API_URL_TIP);
        this.mType = jsonObject.optInt(COLUMN_TYPE);
        this.mIsPaid = jsonObject.optBoolean(COLUMN_IS_PAID);
        this.mIsFavorite = false;

    }


    public void update(Tip tip) {

        this.mServerId = tip.mServerId;
        this.mSponsorId = tip.mSponsorId;
        this.mSponsorName = tip.mSponsorName;
        this.mTitle = tip.mTitle;
        this.mBody = tip.mBody;
        this.mUrlImage = tip.mUrlImage;
        this.mUrl = tip.mUrl;
        this.mType = tip.mType;
        this.mIsPaid = tip.mIsPaid;
        this.mIsFavorite = tip.mIsFavorite;

        this.save();
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

    public static List<Tip> getTips() {

        String order = COLUMN_SERVER_ID + DB.DESC;
        return new Select().from(Tip.class).orderBy(order).execute();

    }

    public static Tip getSingleTip(Tip tip) {

        String condition = COLUMN_SERVER_ID + DB.EQUALS + tip.mServerId;

        return new Select().from(Tip.class).where(condition).executeSingle();

    }

    public static boolean isSaved(Tip tip) {

        String condition = COLUMN_SERVER_ID + DB.EQUALS + tip.mServerId;

        return new Select().from(Tip.class).where(condition).exists();

    }

    public static void deleteAll() {

    }
}
