package social.laika.app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import social.laika.app.utils.DB;

/**
 * Created by Tito_Leiva on 10-03-15.
 */

@Table(name = OwnerDog.TABLE_OWNER_DOG)
public class OwnerDog extends Model {

    public final static String TABLE_OWNER_DOG = "owner_dog";
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
}




