package cl.laikachile.laika.models.indexes;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
@Table(name = Size.TABLE_SIZE)
public class Size extends Model {

    public final static String TABLE_SIZE = "size";

    public final static String COLUMN_SIZE_ID = "size_id";
    public final static String COLUMN_NAME = "name";

    @Column(name = COLUMN_SIZE_ID)
    public int mSizeId;

    @Column(name = COLUMN_NAME)
    public String mName;


    public Size() { }

    public Size(int mSizeId, String mName) {
        this.mSizeId = mSizeId;
        this.mName = mName;
    }

    public static void setSizes(Context context) {

        if (getSizes().isEmpty()) {

            Size smaller = new Size(Tag.SIZE_SMALLER, Do.getRString(context, R.string.smaller_new_dog_register));
            Size small = new Size(Tag.SIZE_SMALL, Do.getRString(context, R.string.small_new_dog_register));
            Size middle = new Size(Tag.SIZE_MIDDLE, Do.getRString(context, R.string.middle_new_dog_register));
            Size big = new Size(Tag.SIZE_BIG, Do.getRString(context, R.string.big_new_dog_register));
            Size bigger = new Size(Tag.SIZE_BIGGER, Do.getRString(context, R.string.bigger_new_dog_register));

            smaller.save();
            small.save();
            middle.save();
            big.save();
            bigger.save();
        }

    }

    public static List<Size> getSizes() {

        return new Select().from(Size.class).execute();

    }

    public static Size getSingleSize(int size) {

        String condition = COLUMN_SIZE_ID + DB.EQUALS + size;
        return new Select().from(Size.class).where(condition).executeSingle();

    }
}
