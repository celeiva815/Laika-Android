package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;

@Table(name = AdoptDogForm.TABLE_ADOPT_DOG_FORM_ID)
public class AdoptDogForm extends Model {

    public final static String TABLE_ADOPT_DOG_FORM_ID = "adopt_dog_form_id";

    public final static String COLUMN_OWNER_ID = "owner_id";
    public final static String COLUMN_REGION = "region";
    public final static String COLUMN_CITY = "city";
    public final static String COLUMN_HOME_TYPE = "home_type";
    public final static String COLUMN_FAMILY_COUNT = "family_count";
    public final static String COLUMN_HAS_PET = "has_pet";
    public final static String COLUMN_HAS_ELDERLY = "has_elderly";
    public final static String COLUMN_HAS_KIDS = "has_kids";
    public final static String COLUMN_DOG_GENDER = "dog_gender";
    public final static String COLUMN_DOG_SIZE = "dog_size";
    public final static String COLUMN_DOG_PERSONALITY = "dog_personality";

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_REGION)
    public String mRegion;

    @Column(name = COLUMN_CITY)
    public String mCity;

    @Column(name = COLUMN_HOME_TYPE)
    public String mHomeType;

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
    public String mDogSize;

    @Column(name = COLUMN_DOG_PERSONALITY)
    public String mDogPersonality;

    public AdoptDogForm() { }

    public AdoptDogForm(int mOwnerId, String mRegion, String mCity, String mHomeType,
                          int mFamilyCount, boolean mHasPet, boolean mHasElderly, boolean mHasKids,
                          int mDogGender, String mDogSize, String mDogPersonality) {

        this.mOwnerId = mOwnerId;
        this.mRegion = mRegion;
        this.mCity = mCity;
        this.mHomeType = mHomeType;
        this.mFamilyCount = mFamilyCount;
        this.mHasPet = mHasPet;
        this.mHasElderly = mHasElderly;
        this.mHasKids = mHasKids;
        this.mDogGender = mDogGender;
        this.mDogSize = mDogSize;
        this.mDogPersonality = mDogPersonality;
    }
}