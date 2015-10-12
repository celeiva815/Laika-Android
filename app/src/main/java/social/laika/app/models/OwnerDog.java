package social.laika.app.models;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import social.laika.app.utils.DB;

/**
 * Created by Tito_Leiva on 10-03-15.
 */

@Table(name = OwnerDog.TABLE_OWNER_DOGS)
public class OwnerDog extends Model {

    public final static String TABLE_OWNER_DOGS = "owner_dogs";
    public final static String COLUMN_OWNER_ID = "owner_id";
    public final static String COLUMN_DOG_ID = "dog_id";
    public final static String COLUMN_ROLE = "mRole";

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_DOG_ID)
    public int mDogId;

    @Column(name = COLUMN_ROLE)
    public int mRole;

    public OwnerDog() {}

    public OwnerDog(int mOwnerId, int mDogId, int mRole) {
        this.mOwnerId = mOwnerId;
        this.mDogId = mDogId;
        this.mRole = mRole;
    }

    public void update(OwnerDog ownerDog) {

        this.mOwnerId = ownerDog.mOwnerId;
        this.mDogId = ownerDog.mDogId;
        this.mRole = ownerDog.mRole;

        this.save();
    }

    public static void saveOwnerDogs(JSONObject jsonObject, Context context) {

        if (jsonObject.has(TABLE_OWNER_DOGS)) {
            try {
                JSONArray jsonDogs = jsonObject.getJSONArray(TABLE_OWNER_DOGS);

                for (int i = 0; i < jsonDogs.length(); i++) {

                    int dogId = jsonDogs.getJSONObject(i).getInt(Dog.COLUMN_DOG_ID);
                    Dog dog = Dog.getSingleDog(dogId);

                    if (dog != null) {

                        Owner.saveOwners(jsonDogs.getJSONObject(i), context, dog);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public static List<OwnerDog> getOwnerDogs(Dog dog) {

        String condition = COLUMN_DOG_ID + DB.EQUALS + dog.mDogId;
        return new Select().from(OwnerDog.class).where(condition).execute();
    }

    public static void createOrUpdate(OwnerDog ownerDog) {

        OwnerDog oldOwnerDog = getSingleOwnerDog(ownerDog.mDogId, ownerDog.mOwnerId);

        if (oldOwnerDog == null) {
            ownerDog.save();

        } else {
            oldOwnerDog.update(ownerDog);

        }
    }

    public static OwnerDog getSingleOwnerDog(int dogId, int ownerId) {

        String condition = COLUMN_DOG_ID + DB.EQUALS + dogId;
        condition += DB.AND + COLUMN_OWNER_ID + DB.EQUALS + ownerId;

        return new Select().from(OwnerDog.class).where(condition).executeSingle();

    }

    public static void deleteAll() {

        new Delete().from(OwnerDog.class).execute();
    }

    public static void deleteOwnerDog(Dog dog) {

        String condition = AlarmReminder.COLUMN_DOG_ID + DB.EQUALS + dog.mDogId;
        new Delete().from(OwnerDog.class).where(condition).execute();

    }
}




