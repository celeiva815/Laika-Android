package cl.laikachile.laika.models;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

@Table(name = Owner.TABLE_NAME)
public class Owner extends Model {

    public final static String TABLE_NAME = "owners";
    public final static String COLUMN_OWNER_ID = "owner_id";
    public final static String COLUMN_OWNER_NAME = "owner_name";
    public final static String COLUMN_FIRST_NAME = "first_name";
    public final static String COLUMN_SECOND_NAME = "second_name";
    public final static String COLUMN_FIRST_SURNAME = "first_surname";
    public final static String COLUMN_SECOND_SURNAME = "second_surname";
    public final static String COLUMN_BIRTHDATE = "birth_date";
    public final static String COLUMN_GENDER = "gender";
    public final static String COLUMN_EMAIL = "email";
    public final static String COLUMN_PHONE = "phone";
    public final static String COLUMN_LOCATION_ID = "location_id";

    public final static String API_OWNERS = "owners";
    public final static String API_USER_ID = "user_id";
    public final static String API_LAST_NAME = "last_name";

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_OWNER_NAME) //No va a tener usuario
    public String mOwnerName;

    @Column(name = COLUMN_FIRST_NAME)
    public String mFirstName;

    @Column(name = COLUMN_SECOND_NAME)
    public String mSecondName;

    @Column(name = COLUMN_FIRST_SURNAME)
    public String mFirstSurname;

    @Column(name = COLUMN_SECOND_SURNAME)
    public String mSecondSurname;

    @Column(name = COLUMN_BIRTHDATE)
    public String mBirthDate;

    @Column(name = COLUMN_GENDER)
    public int mGender;

    @Column(name = COLUMN_EMAIL)
    public String mEmail;

    @Column(name = COLUMN_PHONE)
    public String mPhone;

    @Column(name = COLUMN_LOCATION_ID)
    public int mLocationId;


    //Not in the database
    public int mRole;

    public Owner() {
    }

    public Owner(int mOwnerId, String mOwnerName, String mFirstName, String mSecondName,
                 String mFirstSurname, String mSecondSurname, String mBirthDate, int mGender,
                 String mEmail, String mPhone, int mLocationId) {

        this.mOwnerId = mOwnerId;
        this.mOwnerName = mOwnerName;
        this.mFirstName = mFirstName;
        this.mSecondName = mSecondName;
        this.mFirstSurname = mFirstSurname;
        this.mSecondSurname = mSecondSurname;
        this.mBirthDate = mBirthDate;
        this.mGender = mGender;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
        this.mLocationId = mLocationId;
    }

    public Owner(JSONObject jsonObject) {


        try {
            this.mOwnerId = jsonObject.getInt(API_USER_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.mFirstName = jsonObject.getString(COLUMN_FIRST_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mSecondName = ""; //FIXME jsonObject.getString();
        try {
            this.mFirstSurname = jsonObject.getString(API_LAST_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mSecondSurname = ""; //FIXME jsonObject.getString();
        this.mOwnerName = mFirstName + " " + mFirstSurname;
        try {
            this.mBirthDate = jsonObject.getString(COLUMN_BIRTHDATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.mGender = jsonObject.getInt(COLUMN_GENDER);
        } catch (JSONException e) {
            this.mGender = Tag.GENDER_MALE;
            e.printStackTrace();
        }
        try {
            this.mEmail = jsonObject.getString(COLUMN_EMAIL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.mPhone = jsonObject.getString(COLUMN_PHONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.mLocationId = jsonObject.getInt(COLUMN_LOCATION_ID);
        } catch (JSONException e) {
            this.mLocationId = 0;
            e.printStackTrace();
        }

    }

    public String getFullName() {

        return mFirstName + " " + mFirstSurname + " " + mSecondSurname;
    }

    public void addDog(Dog dog, int role) {

        OwnerDog ownerDog = new OwnerDog(this.mOwnerId, dog.mDogId, role);
        ownerDog.save();

    }

    public void createOwnerDog(Context context, Dog dog) {

        int role = Tag.ROLE_EDITOR;

        if (PrefsManager.getUserId(context) == mOwnerId) {

            role = Tag.ROLE_ADMIN;
        }

        OwnerDog ownerDog = new OwnerDog(mOwnerId, dog.mDogId, role);
        ownerDog.createOrUpdate(ownerDog);

    }

    public void update(Owner owner) {

        this.mOwnerId = owner.mOwnerId;
        this.mOwnerName = owner.mOwnerName;
        this.mFirstName = owner.mFirstName;
        this.mSecondName = owner.mSecondName;
        this.mFirstSurname = owner.mFirstSurname;
        this.mSecondSurname = owner.mSecondSurname;
        this.mBirthDate = owner.mBirthDate;
        this.mGender = owner.mGender;
        this.mEmail = owner.mEmail;
        this.mPhone = owner.mPhone;
        this.mLocationId = owner.mLocationId;

        this.save();

    }

    //Data Base

    public static List<Owner> getOwners(List<OwnerDog> ownerDogs) {

        List<Owner> owners = new ArrayList<>();

        for (OwnerDog ownerDog : ownerDogs) {

            Owner owner = getSingleOwner(ownerDog.mOwnerId);

            if (owner != null) {

                owner.mRole = ownerDog.mRole;
                owners.add(owner);
            }
        }

        return owners;
    }


    public static Owner getSingleOwner(int ownerId) {

        String condition = COLUMN_OWNER_ID + DB.EQUALS + Integer.toString(ownerId);
        return new Select().from(Owner.class).where(condition).executeSingle();

    }

    public static void createOrUpdate(Owner owner) {

        Owner oldOwner = Owner.getSingleOwner(owner.mOwnerId);

        if (oldOwner == null) {
            owner.save();

        } else {
            oldOwner.update(owner);

        }
    }

    public static void saveOwners(JSONObject jsonObject, Context context, Dog dog) {

        try {

            JSONArray jsonPublications = jsonObject.getJSONArray(API_OWNERS);

            for (int i = 0; i < jsonPublications.length(); i++) {

                JSONObject jsonPublication = jsonPublications.getJSONObject(i);
                Owner owner = new Owner(jsonPublication);
                createOrUpdate(owner);
                owner.createOwnerDog(context, dog);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
