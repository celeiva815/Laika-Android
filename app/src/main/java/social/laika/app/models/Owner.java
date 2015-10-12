package social.laika.app.models;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import social.laika.app.R;
import social.laika.app.utils.DB;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

@Table(name = Owner.TABLE_NAME)
public class Owner extends Model {

    public final static String TABLE_NAME = "owners";
    public final static String COLUMN_OWNER_ID = "owner_id";
    public final static String COLUMN_OWNER_NAME = "owner_name";
    public final static String COLUMN_FIRST_NAME = "first_name";
    public final static String COLUMN_LAST_NAME = "last_name";
    public final static String COLUMN_SECOND_LAST_NAME = "second_last_name";
    public final static String COLUMN_RUT = "rut";
    public final static String COLUMN_BIRTH_DATE = "birth_date";
    public final static String COLUMN_GENDER = "gender";
    public final static String COLUMN_PHONE = "phone";
    public final static String COLUMN_EMAIL = "email";
    public final static String COLUMN_CITY_ID = "city_id";
    public final static String COLUMN_URL_IMAGE = "url_image";

    public final static String API_OWNERS = "owners";
    public final static String API_ID = "id";
    public final static String API_USER_ID = "user_id";
    public final static String API_USER = "user";
    public final static String API_LAST_NAME = "last_name";

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_OWNER_NAME) //No va a tener usuario
    public String mOwnerName;

    @Column(name = COLUMN_FIRST_NAME)
    public String mFirstName;

    @Column(name = COLUMN_LAST_NAME)
    public String mLastName;

    @Column(name = COLUMN_SECOND_LAST_NAME)
    public String mSecondLastName;

    @Column(name = COLUMN_RUT)
    public String mRut;

    @Column(name = COLUMN_BIRTH_DATE)
    public String mBirthDate;

    @Column(name = COLUMN_GENDER)
    public int mGender;

    @Column(name = COLUMN_EMAIL)
    public String mEmail;

    @Column(name = COLUMN_PHONE)
    public String mPhone;

    @Column(name = COLUMN_CITY_ID)
    public int mCityId;

    @Column(name = COLUMN_URL_IMAGE)
    public String mUrlImage;

    //Not in the database
    public int mRole;

    public Owner() {
    }

    public Owner(int mOwnerId, String mOwnerName, String mFirstName, String mLastName,
                 String mSecondLastName, String mRut, String mBirthDate, int mGender, String mEmail,
                 String mPhone, int mCityId) {

        this.mOwnerId = mOwnerId;
        this.mOwnerName = mOwnerName;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mSecondLastName = mSecondLastName;
        this.mRut = mRut;
        this.mBirthDate = mBirthDate;
        this.mGender = mGender;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
        this.mCityId = mCityId;
    }

    public Owner(JSONObject jsonObject) {

        if (jsonObject.has(API_ID)) {
            this.mOwnerId = jsonObject.optInt(API_ID);

        } else {
            this.mOwnerId = jsonObject.optInt(API_USER_ID);
        }

        this.mFirstName = jsonObject.optString(COLUMN_FIRST_NAME, "");
        this.mLastName = jsonObject.optString(COLUMN_LAST_NAME, "");
        this.mSecondLastName = jsonObject.optString(COLUMN_SECOND_LAST_NAME, "");
        this.mRut = jsonObject.optString(COLUMN_RUT);
        this.mBirthDate = jsonObject.optString(COLUMN_BIRTH_DATE);
        this.mGender = jsonObject.optInt(COLUMN_GENDER);
        this.mEmail = jsonObject.optString(COLUMN_EMAIL);
        this.mPhone = jsonObject.optString(COLUMN_PHONE);
        this.mCityId = jsonObject.optInt(COLUMN_CITY_ID);
        this.mUrlImage = jsonObject.optString(COLUMN_URL_IMAGE);

    }

    public String getFullName() {

        return mFirstName + " " + mLastName + " " + mSecondLastName;
    }

    public void addDog(Dog dog, int role) {

        OwnerDog ownerDog = new OwnerDog(this.mOwnerId, dog.mDogId, role);
        ownerDog.save();

    }

    public void createOwnerDog(Context context, Dog dog) {

        int role;

        if (mOwnerId == dog.mOwnerId) {
            role = Tag.ROLE_ADMIN;

        } else {
            role = Tag.ROLE_EDITOR;

        }

        OwnerDog ownerDog = new OwnerDog(mOwnerId, dog.mDogId, role);
        ownerDog.createOrUpdate(ownerDog);

    }

    public void update(Owner owner) {

        this.mOwnerId = owner.mOwnerId;
        this.mOwnerName = owner.mOwnerName;
        this.mFirstName = owner.mFirstName;
        this.mLastName = owner.mLastName;
        this.mSecondLastName = owner.mSecondLastName;
        this.mRut = owner.mRut;
        this.mBirthDate = owner.mBirthDate;
        this.mGender = owner.mGender;
        this.mEmail = owner.mEmail;
        this.mPhone = owner.mPhone;
        this.mCityId = owner.mCityId;
        this.mUrlImage = owner.mUrlImage;

        this.save();

    }

    public JSONObject getJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(COLUMN_FIRST_NAME, mFirstName);
            jsonObject.put(COLUMN_LAST_NAME, mLastName);
            jsonObject.put(COLUMN_SECOND_LAST_NAME, mSecondLastName);
            jsonObject.put(COLUMN_BIRTH_DATE, mBirthDate);
            jsonObject.put(COLUMN_GENDER, mGender);
            jsonObject.put(COLUMN_PHONE, mPhone);
            jsonObject.put(COLUMN_CITY_ID, mCityId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
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

    public static void deleteAll() {

        new Delete().from(Owner.class).execute();
    }

    public String getGender(Context context) {

        switch (mGender) {

            case Tag.GENDER_MALE:
                return Do.getRString(context, R.string.gender_human_male);

            case Tag.GENDER_FEMALE:
                return Do.getRString(context, R.string.gender_human_female);
        }

        return "";
    }

    public City getCity() {

        return City.getSingleCity(mCityId);
    }

    public boolean isGrownUp() {

        //TODO mejorar el algoritmo para que sea 18 años bien.
        Date birthDate = Do.stringToDate(mBirthDate, Do.DAY_FIRST);
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        Calendar nowCalendar = Calendar.getInstance();

        int years = nowCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        return years >= 18;
    }

    public void deleteDogInformation(Context context, Dog dog) {

        AlarmReminder.deleteAll(context, dog);
        CalendarReminder.deleteAll(context, dog);
        Photo.deleteAll(dog);
        OwnerDog.deleteOwnerDog(dog);
        Dog.deleteDog(dog);

    }
}
