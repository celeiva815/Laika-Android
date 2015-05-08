package cl.laikachile.laika.models;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;


@Table(name = Dog.TABLE_NAME)
public class Dog extends Model {

    public static int ID = 1;

    public final static String TABLE_NAME = "dogs";
    public final static String COLUMN_DOG_ID = "dog_id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_BIRTHDATE = "birth";
    public final static String COLUMN_BREED_ID = "breed_id";
    public final static String COLUMN_GENDER = "gender";
    public final static String COLUMN_PERSONALITY = "personality";
    public final static String COLUMN_IS_STERILIZED = "is_sterilized";
    public final static String COLUMN_CHIP_CODE = "chip_code";
    public final static String COLUMN_STATUS = "status";
    public final static String COLUMN_WEIGHT = "weight";
    public final static String COLUMN_IS_TRAINED = "is_trained";
    public final static String COLUMN_URL_IMAGE = "url_image";
    public final static String COLUMN_OWNER_ID = "owner_id";

    public final static String API_DOGS = "dogs";

    public final static int ID_NOT_SET = 0;


    @Column(name = COLUMN_DOG_ID)
    public int mDogId;

    @Column(name = COLUMN_NAME)
    public String mName;

    @Column(name = COLUMN_BIRTHDATE)
    public String mBirth;

    @Column(name = COLUMN_BREED_ID)
    public int mBreedId;

    @Column(name = COLUMN_GENDER)
    public int mGender;

    @Column(name = COLUMN_PERSONALITY)
    public int mPersonality;

    @Column(name = COLUMN_IS_STERILIZED)
    public boolean mIsSterilized;

    @Column(name = COLUMN_IS_TRAINED)
    public boolean mIsTrained;

    @Column(name = COLUMN_CHIP_CODE)
    public String mChipCode;

    @Column(name = COLUMN_STATUS)
    public int mStatus;

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    //FIXME cambiar a un string de URL
    @Column(name = COLUMN_URL_IMAGE)
    public int mImage;

    public String mDetail;

    public Dog() {
    }

    public Dog(int mDogId, String mName, String mBirth, int mBreedId, int mGender,
               int mPersonality, boolean mIsSterilized, boolean mIsTrained, String mChipCode,
               int mStatus, int mOwnerId) {

        this.mDogId = mDogId;
        this.mName = mName;
        this.mBirth = mBirth;
        this.mBreedId = mBreedId;
        this.mGender = mGender;
        this.mPersonality = mPersonality;
        this.mIsSterilized = mIsSterilized;
        this.mIsTrained = mIsTrained;
        this.mChipCode = mChipCode;
        this.mStatus = mStatus;
        this.mOwnerId = mOwnerId;
    }

    public Dog(String mName, String mBirth, int mBreedId, int mGender,
               int mPersonality, boolean mIsSterilized, boolean mIsTrained, String mChipCode,
               int mStatus, int mOwnerId) {

        this.mDogId = ID_NOT_SET;
        this.mName = mName;
        this.mBirth = mBirth;
        this.mBreedId = mBreedId;
        this.mGender = mGender;
        this.mPersonality = mPersonality;
        this.mIsSterilized = mIsSterilized;
        this.mIsTrained = mIsTrained;
        this.mChipCode = mChipCode;
        this.mStatus = mStatus;
        this.mOwnerId = mOwnerId;
    }

    public Dog(JSONObject jsonObject, int status) {

        try {

            this.mDogId = jsonObject.getInt(COLUMN_DOG_ID);
            this.mName = jsonObject.getString(COLUMN_NAME);
            this.mBirth = jsonObject.getString(COLUMN_BIRTHDATE);
            this.mBreedId = getBreedIdFromJson(jsonObject.getJSONObject("breed")); //FIXME
            this.mGender = jsonObject.getInt(COLUMN_GENDER);
            this.mPersonality = jsonObject.getInt(COLUMN_PERSONALITY);
            this.mIsSterilized = jsonObject.getBoolean(COLUMN_IS_STERILIZED);
            this.mIsTrained = jsonObject.getBoolean(COLUMN_IS_TRAINED);
            this.mChipCode = Boolean.toString(jsonObject.getBoolean(COLUMN_CHIP_CODE));//jsonObject.getString(COLUMN_CHIP_CODE);
            this.mStatus = jsonObject.getInt(COLUMN_STATUS);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void update(Dog dog) {

        this.mDogId = dog.mDogId;
        this.mName = dog.mName;
        this.mBirth = dog.mBirth;
        this.mBreedId = dog.mBreedId;
        this.mGender = dog.mGender;
        this.mPersonality = dog.mPersonality;
        this.mIsSterilized = dog.mIsSterilized;
        this.mIsTrained = dog.mIsTrained;
        this.mChipCode = dog.mChipCode;
        this.mStatus = dog.mStatus;
        this.mOwnerId = dog.mOwnerId;

        this.save();

    }

    public JSONObject getJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            if (mDogId > ID_NOT_SET) {
                jsonObject.put(COLUMN_DOG_ID, this.mDogId);
            }

            jsonObject.put(COLUMN_NAME, this.mName);
            jsonObject.put(COLUMN_BIRTHDATE, this.mBirth);
            jsonObject.put("breed", getJsonBreed()); //FIXME tiene que quedar solo el breed_id
            jsonObject.put(COLUMN_GENDER, this.mGender);
            jsonObject.put(COLUMN_PERSONALITY, this.mPersonality);
            jsonObject.put(COLUMN_IS_STERILIZED, this.mIsSterilized);
            jsonObject.put(COLUMN_IS_TRAINED, this.mIsTrained);
            jsonObject.put(COLUMN_CHIP_CODE, false); //FIXME tiene que ser un string
            jsonObject.put(COLUMN_STATUS, this.mStatus);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * Este método es un parche, mientras se repara la API
     *
     * @return un objeto breed mal hecho
     */
    private JSONObject getJsonBreed() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("breed_id", mBreedId);
            jsonObject.put("size_id", getSize().mSizeId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    public String getAge() {

        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.setTime(Do.stringToDate(mBirth, Do.DAY_FIRST));

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < dateOfBirth.get(Calendar.MONTH)) {

            age--;

        } else if (today.get(Calendar.MONTH) == dateOfBirth.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) < dateOfBirth.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }

        return Integer.toString(age) + " años";
    }

    public void addOwner(Owner owner, int role) {

        OwnerDog ownerDog = new OwnerDog(owner.mOwnerId, this.mDogId, role);
        ownerDog.save();

    }

    public void setDetail(String detail) {
        this.mDetail = detail;
    }

    public String getGender(Context context) {

        switch (mGender) {

            case Tag.GENDER_MALE:
                return Do.getRString(context, R.string.gender_male);

            case Tag.GENDER_FEMALE:
                return Do.getRString(context, R.string.gender_female);
        }

        return null;
    }

    public String getSterilized(Context context) {

        if (mIsSterilized) {
            return Do.getRString(context, R.string.is_sterilized);

        } else {
            return Do.getRString(context, R.string.is_not_sterilized);

        }
    }


    public String getChip(Context context) {

        if (!Do.isNullOrEmpty(mChipCode)) {
            return Do.getRString(context, R.string.has_chip);

        } else {
            return Do.getRString(context, R.string.has_not_chip);

        }
    }

    public String getTrained(Context context) {

        if (mIsTrained) {
            return Do.getRString(context, R.string.is_trained);

        } else {
            return Do.getRString(context, R.string.is_not_trained);

        }
    }

    public Size getSize() {

        Breed breed = getBreed();
        return Size.getSingleSize(breed.mSizeId);

    }

    public Breed getBreed() {

        return Breed.getSingleBreed(mBreedId);

    }

    public int getBreedIdFromJson(JSONObject jsonObject) {

        int breedId = -1;

        try {
            breedId = jsonObject.getInt(COLUMN_BREED_ID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return breedId;
    }

    public Personality getPersonality() {

        return Personality.getSinglePersonality(mPersonality);
    }

    //DataBase 

    public static void saveDogs(JSONObject jsonObject, int status) {

        try {
            JSONArray jsonDogs = jsonObject.getJSONArray(API_DOGS);

            for (int i = 0; i < jsonDogs.length(); i++) {
                saveDog(jsonDogs.getJSONObject(i), status);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Dog saveDog(JSONObject jsonDog, int status) {

        Dog dog = new Dog(jsonDog, status);
        createOrUpdate(dog);

        return dog;

    }

    public static void createOrUpdate(Dog dog) {

        if (!Dog.isSaved(dog)) {
            dog.save();

        } else {
            Dog oldDog = getSingleDog(dog.mDogId);
            oldDog.update(dog);

        }
    }

    public static boolean isSaved(Dog dog) {

        String condition = COLUMN_DOG_ID + DB.EQUALS + dog.mDogId;
        return new Select().from(Dog.class).where(condition).exists();

    }

    public static Dog getSingleDog(int dogId) {

        String condition = COLUMN_DOG_ID + DB.EQUALS + dogId;
        return new Select().from(Dog.class).where(condition).executeSingle();

    }

    public static List<Dog> getDogs(int process) {

        String condition = COLUMN_STATUS + DB.EQUALS + process;
        return new Select().from(Dog.class).where(condition).execute();

    }

    public static void deleteAll() {

        new Delete().from(Dog.class).execute();

    }


}
