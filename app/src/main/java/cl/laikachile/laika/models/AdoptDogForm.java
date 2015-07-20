package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import cl.laikachile.laika.utils.DB;

@Table(name = AdoptDogForm.TABLE_ADOPT_DOG_FORM)
public class AdoptDogForm extends Model {

    public final static String TABLE_ADOPT_DOG_FORM = "adopt_dog_form";
    public final static String COLUMN_ADOPT_DOG_FORM_ID = "adopt_dog_form_id";
    public final static String COLUMN_OWNER_ID = "owner_id";
    public final static String COLUMN_LOCATION_ID = "location_id";
    public final static String COLUMN_HOME_TYPE = "home_type";
    public final static String COLUMN_FAMILY_COUNT = "family_count";
    public final static String COLUMN_HAS_PET = "has_pet";
    public final static String COLUMN_HAS_ELDERLY = "has_elderly";
    public final static String COLUMN_HAS_KIDS = "has_kids";
    public final static String COLUMN_DOG_GENDER = "dog_gender";
    public final static String COLUMN_DOG_SIZE = "dog_size";
    public final static String COLUMN_DOG_PERSONALITY = "dog_personality";
    public final static String COLUMN_FREE_TIME = "free_time";

    public final static String API_ADOPTION_FORM = "adoption_form";
    public final static String API_ID = "id";
    public final static String API_USER_ID = "user_id";

    @Column(name = COLUMN_ADOPT_DOG_FORM_ID)
    public int mAdoptDogFormId;

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_LOCATION_ID)
    public int mCityId;

    @Column(name = COLUMN_HOME_TYPE)
    public int mHomeType;

    @Column(name = COLUMN_FAMILY_COUNT)
    public int mFamilyCount;

    @Column(name = COLUMN_HAS_PET)
    public boolean mHasPet;

    @Column(name = COLUMN_HAS_ELDERLY)
    public boolean mHasElderly;

    @Column(name = COLUMN_HAS_KIDS)
    public boolean mHasKids;

    @Column(name = COLUMN_DOG_GENDER)
    public int mDogGender;

    @Column(name = COLUMN_DOG_SIZE)
    public int mDogSize;

    @Column(name = COLUMN_DOG_PERSONALITY)
    public int mDogPersonality;

    @Column(name = COLUMN_FREE_TIME)
    public int mFreeTime;

    public AdoptDogForm() { }

    public AdoptDogForm(int mOwnerId, int mCityId, int mHomeType,
                        int mFamilyCount, boolean mHasPet, boolean mHasElderly, boolean mHasKids,
                        int mDogGender, int mDogSize, int mDogPersonality, int mFreeTime) {
        this.mOwnerId = mOwnerId;
        this.mCityId = mCityId;
        this.mHomeType = mHomeType;
        this.mFamilyCount = mFamilyCount;
        this.mHasPet = mHasPet;
        this.mHasElderly = mHasElderly;
        this.mHasKids = mHasKids;
        this.mDogGender = mDogGender;
        this.mDogSize = mDogSize;
        this.mDogPersonality = mDogPersonality;
        this.mFreeTime = mFreeTime;
    }

    public AdoptDogForm(JSONObject jsonObject) {

        this.mAdoptDogFormId = jsonObject.optInt(API_ID);
        this.mOwnerId = jsonObject.optInt(API_USER_ID);
        this.mCityId = jsonObject.optInt(COLUMN_LOCATION_ID);
        this.mHomeType = jsonObject.optInt(COLUMN_HOME_TYPE);
        this.mFamilyCount = jsonObject.optInt(COLUMN_FAMILY_COUNT);
        this.mHasPet = jsonObject.optBoolean(COLUMN_HAS_PET);
        this.mHasElderly = jsonObject.optBoolean(COLUMN_HAS_ELDERLY);
        this.mHasKids = jsonObject.optBoolean(COLUMN_HAS_KIDS);
        this.mDogGender = jsonObject.optInt(COLUMN_DOG_GENDER);
        this.mDogSize = jsonObject.optInt(COLUMN_DOG_SIZE);
        this.mDogPersonality = jsonObject.optInt(COLUMN_DOG_PERSONALITY);
        this.mFreeTime = jsonObject.optInt(COLUMN_FREE_TIME);
    }

    public void update(AdoptDogForm dogForm, int adoptDogFormId) {

        this.mAdoptDogFormId = adoptDogFormId;
        update(dogForm);

    }

    public void update(AdoptDogForm dogForm) {

        this.mOwnerId = dogForm.mOwnerId;
        this.mCityId = dogForm.mCityId;
        this.mHomeType = dogForm.mHomeType;
        this.mFamilyCount = dogForm.mFamilyCount;
        this.mHasPet = dogForm.mHasPet;
        this.mHasElderly = dogForm.mHasElderly;
        this.mHasKids = dogForm.mHasKids;
        this.mDogGender = dogForm.mDogGender;
        this.mDogSize = dogForm.mDogSize;
        this.mDogPersonality = dogForm.mDogPersonality;
        this.mFreeTime = dogForm.mFreeTime;

        this.save();

    }

    public boolean hasId(){

        return mAdoptDogFormId > 0;
    }

    // Data Base

    public static AdoptDogForm saveAdoptForm(JSONObject jsonObject) {

        AdoptDogForm adoptDogForm = new AdoptDogForm(jsonObject);
        return createOrUpdate(adoptDogForm);

    }

    public static AdoptDogForm createOrUpdate(AdoptDogForm dogForm) {

        AdoptDogForm oldDogForm = getSingleDogForm(dogForm.mAdoptDogFormId);

        if (oldDogForm == null) {

            dogForm.save();
            return dogForm;

        } else {

            oldDogForm.update(dogForm);
            return oldDogForm;
        }
    }

    public static AdoptDogForm getSingleDogForm(int adoptDogFormId) {

        String condition = COLUMN_ADOPT_DOG_FORM_ID + DB.EQUALS + adoptDogFormId;
        return new Select().from(AdoptDogForm.class).where(condition).executeSingle();

    }

    public JSONObject getJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(COLUMN_LOCATION_ID, mCityId);
            jsonObject.put(COLUMN_HOME_TYPE, mHomeType);
            jsonObject.put(COLUMN_FAMILY_COUNT, mFamilyCount);
            jsonObject.put(COLUMN_HAS_PET, mHasPet);
            jsonObject.put(COLUMN_HAS_ELDERLY, mHasElderly);
            jsonObject.put(COLUMN_HAS_KIDS, mHasKids);
            jsonObject.put(COLUMN_DOG_GENDER, mDogGender);
            jsonObject.put(COLUMN_DOG_SIZE, mDogSize);
            jsonObject.put(COLUMN_DOG_PERSONALITY, mDogPersonality);
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
}