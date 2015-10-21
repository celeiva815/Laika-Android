package social.laika.app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import social.laika.app.utils.DB;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 25-05-15.
 */
@Table(name = UserAdoptDog.TABLE_USER_ADOPT_DOG)
public class UserAdoptDog extends Model {

    public final static String TABLE_USER_ADOPT_DOG = "user_adopt_dog";

    public final static String COLUMN_USER_ADOPT_DOG_ID = "user_adopt_dog_id";
    public final static String COLUMN_FOUNDATION_ID = "foundation_id";
    public final static String COLUMN_FOUNDATION_NAME = "foundation_name";
    public final static String COLUMN_FOUNDATION_EMAIL = "foundation_email";
    public final static String COLUMN_COMPATIBILITY = "compatibility";
    public final static String COLUMN_STATUS = "status";
    public final static String COLUMN_DOG_ID = "dog_id";

    public final static String API_POSTULATIONS = "postulations";
    public final static String API_DOG = "dog";

    @Column(name = COLUMN_USER_ADOPT_DOG_ID)
    public int mUserAdoptDogId;

    @Column(name = COLUMN_FOUNDATION_ID)
    public int mFoundationId;

    @Column(name = COLUMN_FOUNDATION_NAME)
    public String mFoundationName;

    @Column(name = COLUMN_FOUNDATION_EMAIL)
    public String mFoundationEmail;

    @Column(name = COLUMN_COMPATIBILITY)
    public int mCompatibility;

    @Column(name = COLUMN_STATUS)
    public int mStatus;

    @Column(name = COLUMN_DOG_ID)
    public int mDogId;

    public UserAdoptDog() { }

    public UserAdoptDog(int mUserAdoptDogId, int mFoundationId, String mFoundationName,
                        String mFoundationEmail, int mCompatibility, int mStatus, int mDogId) {

        this.mUserAdoptDogId = mUserAdoptDogId;
        this.mFoundationId = mFoundationId;
        this.mFoundationName = mFoundationName;
        this.mFoundationEmail = mFoundationEmail;
        this.mCompatibility = mCompatibility;
        this.mStatus = mStatus;
        this.mDogId = mDogId;
    }

    public UserAdoptDog(JSONObject jsonObject) {
        this.mUserAdoptDogId = jsonObject.optInt(COLUMN_USER_ADOPT_DOG_ID);
        this.mFoundationId = jsonObject.optInt(COLUMN_FOUNDATION_ID);
        this.mFoundationName = jsonObject.optString(COLUMN_FOUNDATION_NAME);
        this.mCompatibility = new Double(jsonObject.optDouble(COLUMN_COMPATIBILITY)).intValue();
        this.mStatus = jsonObject.optInt(COLUMN_STATUS);

        if (jsonObject.has(API_DOG)) {

            int status = mStatus == Tag.POSTULATION_ADOPTED? Tag.DOG_OWNED : Tag.DOG_POSTULATED;

            Dog dog = Dog.saveDog(jsonObject.optJSONObject(API_DOG), status);
            this.mDogId = dog.mDogId;

        }
    }


    public void update(UserAdoptDog userAdoptDog) {

        this.mUserAdoptDogId = userAdoptDog.mUserAdoptDogId;
        this.mFoundationId = userAdoptDog.mFoundationId;
        this.mFoundationName = userAdoptDog.mFoundationName;
        this.mFoundationEmail = userAdoptDog.mFoundationEmail;
        this.mCompatibility = userAdoptDog.mCompatibility;
        this.mStatus = userAdoptDog.mStatus;
        this.mDogId = userAdoptDog.mDogId;

        this.save();

    }



    //DATA BASE

    public static void saveUserAdoptDogs(JSONObject jsonObject) {

        if (jsonObject.has(API_POSTULATIONS)) {
            try {
                JSONArray jsonDogs = jsonObject.getJSONArray(API_POSTULATIONS);

                for (int i = 0; i < jsonDogs.length(); i++) {
                    saveUserAdoptDog(jsonDogs.getJSONObject(i));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static UserAdoptDog saveUserAdoptDog(JSONObject jsonObject) {

        UserAdoptDog userAdoptDog = new UserAdoptDog(jsonObject);
        return createOrUpdate(userAdoptDog);

    }

    public static UserAdoptDog createOrUpdate(UserAdoptDog userAdoptDog) {

        if (!isSaved(userAdoptDog)) {

            userAdoptDog.save();
            return userAdoptDog;

        } else {

            UserAdoptDog oldUserAdoptDog = getSingleUserAdoptDog(userAdoptDog.mUserAdoptDogId);
            oldUserAdoptDog.update(userAdoptDog);
            return oldUserAdoptDog;
        }
    }

    public static boolean isSaved(UserAdoptDog userAdoptDog) {

        String condition = COLUMN_USER_ADOPT_DOG_ID + DB.EQUALS + userAdoptDog.mUserAdoptDogId;
        return new Select().from(UserAdoptDog.class).where(condition).exists();

    }

    public static UserAdoptDog getSingleUserAdoptDog(int userAdoptDogId) {

        String condition = COLUMN_USER_ADOPT_DOG_ID + DB.EQUALS + userAdoptDogId;
        return new Select().from(UserAdoptDog.class).where(condition).executeSingle();

    }

    public static UserAdoptDog getSingleUserAdoptDog(Dog dog) {

        String condition = COLUMN_DOG_ID + DB.EQUALS + dog.mDogId;
        return new Select().from(UserAdoptDog.class).where(condition).executeSingle();

    }

    public static List<UserAdoptDog> getUserAdoptDogs() {

        String order = COLUMN_USER_ADOPT_DOG_ID + DB.DESC;
        return new Select().from(UserAdoptDog.class).orderBy(order).execute();

    }

    public static List<UserAdoptDog> getUserAdoptDogs(List<Dog> dogs) {

        List<UserAdoptDog> userAdoptDogs = new ArrayList<>();

        for (Dog dog : dogs) {

            userAdoptDogs.add(getSingleUserAdoptDog(dog));
        }

        return userAdoptDogs;
    }

    public static void deleteUserAdoptDog(UserAdoptDog userAdoptDog) {

        String condition = COLUMN_USER_ADOPT_DOG_ID + DB.EQUALS + userAdoptDog.mUserAdoptDogId;
        new Delete().from(UserAdoptDog.class).where(condition);

    }

    public static void deleteAll() {

        new Delete().from(UserAdoptDog.class).execute();
    }

}

