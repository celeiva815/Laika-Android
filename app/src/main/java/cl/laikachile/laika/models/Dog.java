package cl.laikachile.laika.models;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import cl.laikachile.laika.R;
import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;


@Table(name = Dog.TABLE_NAME)
public class Dog extends Model {

    public static int ID = 100;

    public final static String TABLE_NAME = "dogs";
    public final static String COLUMN_DOG_ID = "dog_id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_BIRTHDATE = "birthdate";
    public final static String COLUMN_BREED = "breed";
    public final static String COLUMN_GENDER = "gender";
    public final static String COLUMN_SIZE = "size";
    public final static String COLUMN_PERSONALITY = "personality";
    public final static String COLUMN_STERILIZED = "sterilized";
    public final static String COLUMN_CHIP_CODE = "chip_code";
    public final static String COLUMN_STATUS = "status";
    public final static String COLUMN_WEIGHT = "weight";
    public final static String COLUMN_TRAINED = "trained";
    public final static String COLUMN_URL_IMAGE = "url_image";
    public final static String COLUMN_OWNER_ID = "owner_id";

    public final static int STATUS_OWN = 1;
    public final static int STATUS_ADOPTED = 2;
    public final static int STATUS_PUBLISH = 3;

    @Column(name = COLUMN_DOG_ID)
    public int mDogId;

    @Column(name = COLUMN_NAME)
    public String mName;

    @Column(name = COLUMN_BIRTHDATE)
    public String mBirth;

    @Column(name = COLUMN_BREED)
    public String mBreed;

    @Column(name = COLUMN_GENDER)
    public int mGender;

    @Column(name = COLUMN_SIZE)
    public String mSize; //FIXME cambiar por enteros

    @Column(name = COLUMN_PERSONALITY)
    public String mPersonality; //FIXME cambiar por enteros

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

    public Dog(int mDogId, String mName, String mBirth, String mBreed, int mGender, String mSize,
               String mPersonality, boolean mSterilized, boolean mTrained, String mChipCode,
               int mStatus, int mOwnerId) {

        this.mDogId = mDogId;
        this.mName = mName;
        this.mBirth = mBirth;
        this.mBreed = mBreed;
        this.mGender = mGender;
        this.mSize = mSize;
        this.mPersonality = mPersonality;
        this.mSterilized = mSterilized;
        this.mTrained = mTrained;
        this.mChipCode = mChipCode;
        this.mStatus = mStatus;
        this.mOwnerId = mOwnerId;
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

    public static Dog getSingleDog(int dogId) {

        String condition = COLUMN_DOG_ID + DB._EQUALS_ + dogId;
        return new Select().from(Dog.class).where(condition).executeSingle();

    }

    public static List<Dog> getOwnedDogs() {

        String condition = COLUMN_STATUS + DB._EQUALS_ + Tag.PROCESS_OWNED;
        return new Select().from(Dog.class).where(condition).execute();

    }
}
