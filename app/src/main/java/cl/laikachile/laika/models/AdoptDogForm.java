package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = AdoptDogForm.TABLE_ADOPT_DOG_FORM)
public class AdoptDogForm extends Model {

    public final static String TABLE_ADOPT_DOG_FORM = "adopt_dog_form";
    public final static String COLUMN_ADOPT_DOG_FORM_ID = "adopt_dog_form_id";
    public final static String COLUMN_OWNER_ID = "owner_id";
    public final static String COLUMN_CITY_ID = "location_id";
    public final static String COLUMN_PHONE = "phone";
    public final static String COLUMN_HOME_TYPE = "home_type";
    public final static String COLUMN_HAS_PET = "has_pet";
    public final static String COLUMN_HAS_ELDERLY = "has_elderly";
    public final static String COLUMN_HAS_KIDS = "has_kids";
    public final static String COLUMN_FREE_TIME = "free_time";

    public final static String API_ADOPTION_FORM = "adoption_form";
    public final static String API_USER_ID = "user_id";

    @Column(name = COLUMN_ADOPT_DOG_FORM_ID)
    public int mAdoptDogFormId;

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_CITY_ID)
    public int mCityId;

    @Column(name = COLUMN_PHONE)
    public String mPhone;

    @Column(name = COLUMN_HOME_TYPE)
    public int mHomeType;

    @Column(name = COLUMN_HAS_PET)
    public boolean mHasPet;

    @Column(name = COLUMN_HAS_ELDERLY)
    public boolean mHasElderly;

    @Column(name = COLUMN_HAS_KIDS)
    public boolean mHasKids;

    @Column(name = COLUMN_FREE_TIME)
    public int mFreeTime;

    public AdoptDogForm() {
    }

    private AdoptDogForm(int mOwnerId, int mCityId, String mPhone, int mHomeType, boolean mHasPet,
                         boolean mHasElderly, boolean mHasKids, int mFreeTime) {

        this.mOwnerId = mOwnerId;
        this.mPhone = mPhone;
        this.mCityId = mCityId;
        this.mHomeType = mHomeType;
        this.mHasPet = mHasPet;
        this.mHasElderly = mHasElderly;
        this.mHasKids = mHasKids;
        this.mFreeTime = mFreeTime;
    }

    private AdoptDogForm(JSONObject jsonObject) {

        this.mAdoptDogFormId = jsonObject.optInt(COLUMN_ADOPT_DOG_FORM_ID);
        this.mOwnerId = jsonObject.optInt(API_USER_ID);
        this.mHomeType = jsonObject.optInt(COLUMN_HOME_TYPE);
        this.mHasPet = jsonObject.optBoolean(COLUMN_HAS_PET);
        this.mHasElderly = jsonObject.optBoolean(COLUMN_HAS_ELDERLY);
        this.mHasKids = jsonObject.optBoolean(COLUMN_HAS_KIDS);
        this.mFreeTime = jsonObject.optInt(COLUMN_FREE_TIME);
    }

    public void update(AdoptDogForm dogForm, int adoptDogFormId) {

        this.mAdoptDogFormId = adoptDogFormId;
        update(dogForm);

    }

    public void update(AdoptDogForm dogForm) {

        this.mAdoptDogFormId = dogForm.mAdoptDogFormId;
        this.mOwnerId = dogForm.mOwnerId;
        this.mCityId = dogForm.mCityId;
        this.mHomeType = dogForm.mHomeType;
        this.mHasPet = dogForm.mHasPet;
        this.mHasElderly = dogForm.mHasElderly;
        this.mHasKids = dogForm.mHasKids;
        this.mFreeTime = dogForm.mFreeTime;

        this.save();

    }

    public JSONObject getJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(COLUMN_CITY_ID, mCityId);
            jsonObject.put(COLUMN_HOME_TYPE, mHomeType);
            jsonObject.put(COLUMN_HAS_PET, mHasPet);
            jsonObject.put(COLUMN_HAS_ELDERLY, mHasElderly);
            jsonObject.put(COLUMN_HAS_KIDS, mHasKids);
            jsonObject.put(COLUMN_FREE_TIME, mFreeTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonContainer = new JSONObject();

        try {
            jsonContainer.put(API_ADOPTION_FORM, jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonContainer;
    }

    public boolean hasId() {

        return mAdoptDogFormId > 0;
    }

    // Data Base

    public static AdoptDogForm newInstance(int ownerId, int cityId, String phone, int homeType, boolean hasPet,
                                           boolean hasElderly, boolean hasKids, int freeTime) {


        AdoptDogForm adoptDogForm = new AdoptDogForm(ownerId, cityId, phone, homeType, hasPet,
                hasElderly, hasKids, freeTime);

        return createOrUpdate(adoptDogForm);

    }

    public static AdoptDogForm saveAdoptForm(JSONObject jsonObject) {

        AdoptDogForm adoptDogForm = new AdoptDogForm(jsonObject);
        return createOrUpdate(adoptDogForm);

    }

    public static AdoptDogForm createOrUpdate(AdoptDogForm dogForm) {

        AdoptDogForm oldDogForm = getSingleDogForm();

        if (oldDogForm == null) {

            dogForm.save();
            return dogForm;

        } else {

            oldDogForm.update(dogForm);
            return oldDogForm;
        }
    }

    public static AdoptDogForm getSingleDogForm() {

        return new Select().from(AdoptDogForm.class).executeSingle();

    }

    public static boolean hasDogForm() {

        return new Select().from(AdoptDogForm.class).exists();

    }

    public static void deleteAll() {

        new Delete().from(AdoptDogForm.class).execute();
    }
}