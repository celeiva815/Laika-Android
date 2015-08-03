package social.laika.app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import social.laika.app.utils.DB;

/**
 * Created by Tito_Leiva on 09-04-15.
 */
@Table(name = Breed.TABLE_BREED)
public class Breed extends Model {

    public final static String TABLE_BREED = "breeds";

    public final static String COLUMN_BREED_ID = "breed_id";
    public final static String COLUMN_BREED_NUMBER = "breed_number";
    public final static String COLUMN_SIZE_ID = "size_id";
    public final static String COLUMN_NAME = "name";

    @Column(name = COLUMN_BREED_ID)
    public int mBreedId;

    @Column(name = COLUMN_BREED_NUMBER)
    public int mBreedNumber;

    @Column(name = COLUMN_SIZE_ID)
    public int mSizeId;

    @Column(name = COLUMN_NAME)
    public String mName;

    public Breed() { }

    public Breed(int mBreedId, int mBreedNumber, int mSizeId, String mName) {

        this.mBreedId = mBreedId;
        this.mBreedNumber = mBreedNumber;
        this.mSizeId = mSizeId;
        this.mName = mName;

    }

    public Breed(JSONObject jsonObject) {

        this.mBreedId = jsonObject.optInt(COLUMN_BREED_ID);
        this.mBreedNumber = jsonObject.optInt(COLUMN_BREED_NUMBER);
        this.mSizeId = jsonObject.optInt(COLUMN_SIZE_ID);
        this.mName = jsonObject.optString(COLUMN_NAME);

    }

    public String getSizeName() {

        Size size = Size.getSingleSize(mSizeId);
        return size.mName;

    }

    private void update(Breed breed) {

        this.mBreedId = breed.mBreedId;
        this.mBreedNumber = breed.mBreedNumber;
        this.mSizeId = breed.mSizeId;
        this.mName = breed.mName;

        this.save();
    }

    //DATABASE

    public static void saveBreeds(JSONObject jsonObject) {

        if (jsonObject.has(TABLE_BREED)) {

            try {
                JSONArray jsonBreeds = jsonObject.getJSONArray(TABLE_BREED);

                for (int i = 0; i < jsonBreeds.length(); i++) {
                    saveBreed(jsonBreeds.getJSONObject(i));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public static Breed saveBreed(JSONObject jsonObject) {

        Breed breed = new Breed(jsonObject);
        return createOrUpdate(breed);

    }

    public static Breed createOrUpdate(Breed breed) {

        if (!isSaved(breed)) {
            breed.save();
            return breed;

        } else {
            Breed oldBreed = getSingleBreed(breed.mBreedId);
            oldBreed.update(breed);
            return oldBreed;
        }
    }

    public static boolean isSaved(Breed breed) {

        String condition = COLUMN_BREED_ID + DB.EQUALS + breed.mBreedId;
        return new Select().from(Breed.class).where(condition).exists();

    }

    public static Breed getSingleBreed(int breedId) {

        String condition = COLUMN_BREED_ID + DB.EQUALS + breedId;
        return new Select().from(Breed.class).where(condition).executeSingle();

    }

    public static List<Breed> getBreeds(int size) {

        if (size > 0) {

            String condition = COLUMN_SIZE_ID + DB.EQUALS + size;
            return new Select().from(Breed.class).where(condition).execute();

        } else {

            return new Select().from(Breed.class).execute();
        }
    }

    public static void deleteAll() {

        new Delete().from(Breed.class).execute();

    }

    public static void deleteBreed(Breed breed) {

        String condition = COLUMN_BREED_ID + DB.EQUALS + breed.mBreedId;
        new Delete().from(Breed.class).where(condition).execute();

    }

}

