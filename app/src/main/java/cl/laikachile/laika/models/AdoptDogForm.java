package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;

@Table(name = AdoptDogForm.TABLE_ADOPT_DOG_FORM)
public class AdoptDogForm extends Model {

    public final static String TABLE_ADOPT_DOG_FORM = "adopt_dog_form";

    public final static String COLUMN_ADOPT_DOG_FORM_ID = "adopt_dog_form_id";
    public final static String COLUMN_OWNER_ID = "owner_id";
    public final static String COLUMN_HOME_TYPE = "home_type";
    public final static String COLUMN_FAMILY_COUNT = "family_count";
    public final static String COLUMN_PET_COUNT = "pet_count";
    public final static String COLUMN_ELDERLY_COUNT = "elderly_count";
    public final static String COLUMN_KIDS_COUNT = "kids_count";
    public final static String COLUMN_DOG_GENDER = "dog_gender";
    public final static String COLUMN_DOG_SIZE = "dog_size";
    public final static String COLUMN_DOG_PERSONALITY = "dog_personality";

    @Column(name = COLUMN_ADOPT_DOG_FORM_ID)
    public int mAdoptDogFormId;

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_HOME_TYPE)
    public int mHomeType;

    @Column(name = COLUMN_FAMILY_COUNT)
    public int mFamilyCount;

    @Column(name = COLUMN_PET_COUNT)
    public int mPetCount;

    @Column(name = COLUMN_ELDERLY_COUNT)
    public int mElderlyCount;

    @Column(name = COLUMN_KIDS_COUNT)
    public int mKidsCount;

    @Column(name = COLUMN_DOG_GENDER)
    public int mDogGender;

    @Column(name = COLUMN_DOG_SIZE)
    public int mDogSize;

    @Column(name = COLUMN_DOG_PERSONALITY)
    public int mDogPersonality;

    public AdoptDogForm() { }

    public AdoptDogForm(int mAdoptDogFormId, int mOwnerId, int mHomeType, int mFamilyCount,
                        int mPetCount, int mElderlyCount, int mKidsCount, int mDogGender,
                        int mDogSize, int mDogPersonality) {

        this.mAdoptDogFormId = mAdoptDogFormId;
        this.mOwnerId = mOwnerId;
        this.mHomeType = mHomeType;
        this.mFamilyCount = mFamilyCount;
        this.mPetCount = mPetCount;
        this.mElderlyCount = mElderlyCount;
        this.mKidsCount = mKidsCount;
        this.mDogGender = mDogGender;
        this.mDogSize = mDogSize;
        this.mDogPersonality = mDogPersonality;
    }
}