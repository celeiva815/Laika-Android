package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

import cl.laikachile.laika.utils.DB;

/**
 * Created by Tito_Leiva on 09-04-15.
 */
@Table(name = Breed.TABLE_BREED)
public class Breed extends Model {

    public final static String TABLE_BREED = "breed";

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

    public String getSizeName() {

        Size size = Size.getSingleSize(mSizeId);

        return size.mName;

    }

    //DataBase

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

}

