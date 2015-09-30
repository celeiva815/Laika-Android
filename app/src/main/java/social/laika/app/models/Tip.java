package social.laika.app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import social.laika.app.utils.DB;

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
    public final static String COLUMN_URL_LOCAL = "url_local";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_IS_PAID = "is_paid";
    public static final String COLUMN_IS_FAVORITE = "is_favorite";

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
    public String mUrlImage;

    @Column(name = COLUMN_URL_TIP)
    public String mUrlTip;

    @Column(name = COLUMN_URL_LOCAL)
    public String mUriLocal;

    @Column(name = COLUMN_TYPE)
    public int mType;

    @Column(name = COLUMN_IS_PAID)
    public boolean mIsPaid;

    @Column(name = COLUMN_IS_FAVORITE)
    public boolean mIsFavorite;


    public Tip() {
    }

    public Tip(int mTipId, int mSponsorId, String mSponsorName, String mTitle, String mBody,
               String mUrlImage, String mUrlTip, int mType, boolean mIsPaid) {

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

        this.mTipId = jsonObject.optInt(API_ID);
        this.mSponsorId = jsonObject.optInt(COLUMN_SPONSOR_ID);
        this.mSponsorName = jsonObject.optString(COLUMN_SPONSOR_NAME);
        this.mTitle = jsonObject.optString(COLUMN_TITLE);
        this.mBody = jsonObject.optString(COLUMN_BODY);
        this.mUrlImage = jsonObject.optString(COLUMN_URL_IMAGE);
        this.mUrlTip = jsonObject.optString(COLUMN_URL_TIP);
        this.mType = jsonObject.optInt(COLUMN_TYPE);
        this.mIsPaid = jsonObject.optBoolean(COLUMN_IS_PAID);
        this.mIsFavorite = false;

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
        this.mIsFavorite = tip.mIsFavorite;

        this.save();
    }

    public void setIsFavorite(boolean isFavorite) {

        mIsFavorite = isFavorite;
        save();
    }

    public static List<Tip> getTips() {

        String order = COLUMN_TIP_ID + DB.DESC;
        return new Select().from(Tip.class).orderBy(order).execute();

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

    public static void deleteAll() {

    }

    public void setUriLocal(String uriLocal) {
        mUriLocal = uriLocal;
        save();
    }
}
