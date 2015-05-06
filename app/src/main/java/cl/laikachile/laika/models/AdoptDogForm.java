package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

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

    public final static String API_ADOPTION_FORM = "adoption_form";

    @Column(name = COLUMN_ADOPT_DOG_FORM_ID)
    public int mAdoptDogFormId;

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_LOCATION_ID)
    public int mLocationId;

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

    public AdoptDogForm() { }

    public AdoptDogForm(int mOwnerId, int mLocationId, int mHomeType, int mFamilyCount,
                        boolean mHasPet, boolean mHasElderly, boolean mHasKids, int mDogGender,
                        int mDogSize, int mDogPersonality) {

        this.mOwnerId = mOwnerId;
        this.mLocationId = mLocationId;
        this.mHomeType = mHomeType;
        this.mFamilyCount = mFamilyCount;
        this.mHasPet = mHasPet;
        this.mHasElderly = mHasElderly;
        this.mHasKids = mHasKids;
        this.mDogGender = mDogGender;
        this.mDogSize = mDogSize;
        this.mDogPersonality = mDogPersonality;
    }

    public void update(AdoptDogForm dogForm, int adoptDogFormId) {

        this.mAdoptDogFormId = adoptDogFormId;
        update(dogForm);

    }

    public void update(AdoptDogForm dogForm) {

        this.mOwnerId = dogForm.mOwnerId;
        this.mLocationId = dogForm.mLocationId;
        this.mHomeType = dogForm.mHomeType;
        this.mFamilyCount = dogForm.mFamilyCount;
        this.mHasPet = dogForm.mHasPet;
        this.mHasElderly = dogForm.mHasElderly;
        this.mHasKids = dogForm.mHasKids;
        this.mDogGender = dogForm.mDogGender;
        this.mDogSize = dogForm.mDogSize;
        this.mDogPersonality = dogForm.mDogPersonality;

        this.save();

    }

    public static void createOrUpdate(AdoptDogForm dogForm) {

        AdoptDogForm oldDogForm = getSingleDogForm();

        if (oldDogForm == null) {
            dogForm.save();

        } else {
            oldDogForm.update(dogForm);

        }
    }

    public static AdoptDogForm getSingleDogForm() {

        return new Select().from(AdoptDogForm.class).executeSingle();

    }

    public JSONObject getJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(COLUMN_LOCATION_ID, mLocationId);
            jsonObject.put(COLUMN_HOME_TYPE, mHomeType);
            jsonObject.put(COLUMN_FAMILY_COUNT, mFamilyCount);
            jsonObject.put(COLUMN_HAS_PET, mHasPet);
            jsonObject.put(COLUMN_HAS_ELDERLY, mHasElderly);
            jsonObject.put(COLUMN_HAS_KIDS, mHasKids);
            jsonObject.put(COLUMN_DOG_GENDER, mDogGender);
            jsonObject.put(COLUMN_DOG_SIZE, mDogSize);
            jsonObject.put(COLUMN_DOG_PERSONALITY, mDogPersonality);

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