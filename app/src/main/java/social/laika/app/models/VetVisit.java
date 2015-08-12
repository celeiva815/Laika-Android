package social.laika.app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import social.laika.app.utils.DB;

/**
 * Created by Tito_Leiva on 27-07-15.
 */
@Table(name = VetVisit.TABLE_VET_VISITS)
public class VetVisit extends Model {

    public final static String TABLE_VET_VISITS = "vet_visits";
    public final static String TABLE_NAME = "vet_visit";

    public final static String COLUMN_VET_VISIT_ID = "vet_visit_id";
    public final static String COLUMN_USER_ID = "user_id";
    public final static String COLUMN_DOG_ID = "dog_id";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_DETAIL = "detail";
    public final static String COLUMN_LARGE_URL = "large_url";
    public final static String COLUMN_SMALL_URL = "small_url";
    public final static String COLUMN_REASON = "reason";
    public final static String COLUMN_TREATMENT = "treatment";
    public final static String COLUMN_VET_DOCTOR = "vet_doctor";
    public final static String COLUMN_VET_NAME = "vet_name";

    @Column(name = COLUMN_VET_VISIT_ID)
    public int mVetVisitId;

    @Column(name = COLUMN_USER_ID)
    public int mUserId;

    @Column(name = COLUMN_DOG_ID)
    public int mDogId;

    @Column(name = COLUMN_DATE)
    public String mDate;

    @Column(name = COLUMN_DETAIL)
    public String mDetail;

    @Column(name = COLUMN_LARGE_URL)
    public String mLargeUrl;

    @Column(name = COLUMN_SMALL_URL)
    public String mSmallUrl;

    @Column(name = COLUMN_REASON)
    public String mReason;

    @Column(name = COLUMN_TREATMENT)
    public String mTreatment;

    @Column(name = COLUMN_VET_DOCTOR)
    public String mVetDoctor;

    @Column(name = COLUMN_VET_NAME)
    public String mVetName;


    public VetVisit() { }

    public VetVisit(int mUserId, int mDogId, String mDate, String mDetail,
                    String mLargeUrl, String mSmallUrl, String mReason, String mTreatment,
                    String mVetDoctor, String mVetName) {

        this.mUserId = mUserId;
        this.mDogId = mDogId;
        this.mDate = mDate;
        this.mDetail = mDetail;
        this.mLargeUrl = mLargeUrl;
        this.mSmallUrl = mSmallUrl;
        this.mReason = mReason;
        this.mTreatment = mTreatment;
        this.mVetDoctor = mVetDoctor;
        this.mVetName = mVetName;
    }

    public VetVisit(JSONObject jsonObject) {

        this.mVetVisitId = jsonObject.optInt(COLUMN_VET_VISIT_ID);
        this.mUserId = jsonObject.optInt(COLUMN_USER_ID);
        this.mDogId = jsonObject.optInt(COLUMN_DOG_ID);
        this.mDate = jsonObject.optString(COLUMN_DATE);
        this.mDetail = jsonObject.optString(COLUMN_DETAIL);
        this.mLargeUrl = jsonObject.optString(COLUMN_LARGE_URL);
        this.mSmallUrl = jsonObject.optString(COLUMN_SMALL_URL);
        this.mReason = jsonObject.optString(COLUMN_REASON);
        this.mTreatment = jsonObject.optString(COLUMN_TREATMENT);
        this.mVetDoctor = jsonObject.optString(COLUMN_VET_DOCTOR);
        this.mVetName = jsonObject.optString(COLUMN_VET_NAME);
    }

    public void update(VetVisit vetVisit) {

        this.mVetVisitId = vetVisit.mVetVisitId;
        this.mUserId = vetVisit.mUserId;
        this.mDogId = vetVisit.mDogId;
        this.mDate = vetVisit.mDate;
        this.mDetail = vetVisit.mDetail;
        this.mLargeUrl = vetVisit.mLargeUrl;
        this.mSmallUrl = vetVisit.mSmallUrl;
        this.mReason = vetVisit.mReason;
        this.mTreatment = vetVisit.mTreatment;
        this.mVetDoctor = vetVisit.mVetDoctor;
        this.mVetName = vetVisit.mVetName;

        this.save();

    }

    public JSONObject getJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(COLUMN_USER_ID, mUserId);
            jsonObject.put(COLUMN_DOG_ID, mDogId);
            jsonObject.put(COLUMN_DATE, mDate);
            jsonObject.put(COLUMN_DETAIL, mDetail);
            jsonObject.put(COLUMN_REASON, mReason);
            jsonObject.put(COLUMN_TREATMENT, mTreatment);
            jsonObject.put(COLUMN_VET_DOCTOR, mVetDoctor);
            jsonObject.put(COLUMN_VET_NAME, mVetName);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return jsonObject;
    }

    
    //DATABASE

    public static void saveVetVisits(JSONObject jsonObject) {

        if (jsonObject.has(TABLE_VET_VISITS)) {
            try {
                JSONArray jsonVetVisits = jsonObject.getJSONArray(TABLE_VET_VISITS);

                for (int i = 0; i < jsonVetVisits.length(); i++) {
                    saveVetVisit(jsonVetVisits.getJSONObject(i));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static VetVisit saveVetVisit(JSONObject jsonVetVisit) {

        VetVisit vetVisit = new VetVisit(jsonVetVisit);
        return createOrUpdate(vetVisit);

    }

    public static VetVisit createOrUpdate(VetVisit vetVisit) {

        if (!isSaved(vetVisit)) {
            vetVisit.save();
            return vetVisit;

        } else {
            VetVisit oldVetVisit = getSingleVetVisit(vetVisit.mVetVisitId);
            oldVetVisit.update(vetVisit);
            return oldVetVisit;
        }
    }

    public static boolean isSaved(VetVisit vetVisit) {

        String condition = COLUMN_VET_VISIT_ID + DB.EQUALS + vetVisit.mVetVisitId;
        return new Select().from(VetVisit.class).where(condition).exists();

    }

    public static VetVisit getSingleVetVisit(int vetVisitId) {

        String condition = COLUMN_VET_VISIT_ID + DB.EQUALS + vetVisitId;
        return new Select().from(VetVisit.class).where(condition).executeSingle();

    }

    public static List<VetVisit> getVetVisits(int dogId) {

        String condition = COLUMN_DOG_ID + DB.EQUALS + dogId;
        return new Select().from(VetVisit.class).where(condition).execute();

    }


    public static void deleteAll() {

        new Delete().from(VetVisit.class).execute();
    }
}

