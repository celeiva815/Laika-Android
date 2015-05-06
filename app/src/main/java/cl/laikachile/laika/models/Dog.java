package cl.laikachile.laika.models;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.indexes.Breed;
import cl.laikachile.laika.models.indexes.Personality;
import cl.laikachile.laika.models.indexes.Size;
import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;


@Table(name = Dog.TABLE_NAME)
public class Dog extends Model {

    public static int ID = 1;

    public final static String TABLE_NAME = "dogs";
    public final static String COLUMN_DOG_ID = "dog_id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_BIRTHDATE = "birthdate";
    public final static String COLUMN_BREED_ID = "breed_id";
    public final static String COLUMN_GENDER = "gender";
    public final static String COLUMN_PERSONALITY_ID = "personality_id";
    public final static String COLUMN_STERILIZED = "sterilized";
    public final static String COLUMN_CHIP_CODE = "chip_code";
    public final static String COLUMN_STATUS = "status";
    public final static String COLUMN_WEIGHT = "weight";
    public final static String COLUMN_TRAINED = "trained";
    public final static String COLUMN_URL_IMAGE = "url_image";
    public final static String COLUMN_OWNER_ID = "owner_id";

    public final static String API_DOGS = "dogs";

    public final static int STATUS_OWN = 1;
    public final static int STATUS_ADOPTED = 2;
    public final static int STATUS_PUBLISH = 3;

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

    @Column(name = COLUMN_PERSONALITY_ID)
    public int mPersonalityId;

    @Column(name = COLUMN_STERILIZED)
    public boolean mSterilized;

    @Column(name = COLUMN_TRAINED)
    public boolean mTrained;

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
               int mPersonalityId, boolean mSterilized, boolean mTrained, String mChipCode,
               int mStatus, int mOwnerId) {

        this.mDogId = mDogId;
        this.mName = mName;
        this.mBirth = mBirth;
        this.mBreedId = mBreedId;
        this.mGender = mGender;
        this.mPersonalityId = mPersonalityId;
        this.mSterilized = mSterilized;
        this.mTrained = mTrained;
        this.mChipCode = mChipCode;
        this.mStatus = mStatus;
        this.mOwnerId = mOwnerId;
    }

    public Dog(JSONObject jsonObject, int status) {

        try {
            this.mDogId = jsonObject.getInt(COLUMN_DOG_ID);
            this.mName = jsonObject.getString(COLUMN_NAME);
            this.mBirth = jsonObject.getString(COLUMN_BIRTHDATE);
            this.mBreedId = jsonObject.getInt(COLUMN_BREED_ID);
            this.mGender = jsonObject.getInt(COLUMN_GENDER);
            this.mPersonalityId = jsonObject.getInt(COLUMN_PERSONALITY_ID);
            this.mSterilized = jsonObject.getBoolean(COLUMN_STERILIZED);
            this.mTrained = jsonObject.getBoolean(COLUMN_TRAINED);
            this.mChipCode = jsonObject.getString(COLUMN_CHIP_CODE);
            this.mStatus = status;

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
        this.mPersonalityId = dog.mPersonalityId;
        this.mSterilized = dog.mSterilized;
        this.mTrained = dog.mTrained;
        this.mChipCode = dog.mChipCode;
        this.mStatus = dog.mStatus;
        this.mOwnerId = dog.mOwnerId;

        this.save();

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

    public String getNameAndStatus() {

        switch (mStatus) {
            case Dog.STATUS_OWN:

                return this.mName;

            case Dog.STATUS_ADOPTED:

                return this.mName + " - (Adoptado)";

            case Dog.STATUS_PUBLISH:

                return this.mName + " - (En adopción)";

            default:

                return this.mName;
        }

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

        if (mSterilized) {
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

        if (mTrained) {
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

    public Personality getPersonality() {

        return Personality.getSinglePersonality(mPersonalityId);
    }

    //DataBase 

    public static void saveDog(JSONObject jsonObject, int status) {

        try {
            JSONArray jsonDogs = jsonObject.getJSONArray(API_DOGS);

            for (int i = 0; i < jsonDogs.length(); i++) {

                JSONObject jsonDog = jsonDogs.getJSONObject(i);
                Dog event = new Dog(jsonDog, status);

                createOrUpdate(event);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        String condition = COLUMN_DOG_ID + DB._EQUALS_ + dog.mDogId;
        return new Select().from(Dog.class).where(condition).exists();

    }

    public static Dog getSingleDog(int dogId) {

        String condition = COLUMN_DOG_ID + DB._EQUALS_ + dogId;
        return new Select().from(Dog.class).where(condition).executeSingle();

    }

    public static List<Dog> getDogs(int process) {

        String condition = COLUMN_STATUS + DB._EQUALS_ + process;
        return new Select().from(Dog.class).where(condition).execute();

    }
}
