package cl.laikachile.laika.models;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 13-03-15.
 */
@Table(name = Photo.TABLE_PHOTO)
public class Photo extends Model {

    public static int ID = 0;

    public final static String TABLE_PHOTO = "photos";
    public final static String COLUMN_PHOTO_ID = "photo_id";
    public final static String COLUMN_OWNER_ID = "owner_id"; // TODO Agregar este campo
    public final static String COLUMN_OWNER_NAME = "owner_name";
    public final static String COLUMN_DOG_ID = "dog_id";
    public final static String COLUMN_URL_THUMBNAIL = "url_thumbnail";
    public final static String COLUMN_URL_IMAGE = "url_image";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_DETAIL = "detail";
    public final static String COLUMN_RESOURCE = "resource";

    @Column(name = COLUMN_PHOTO_ID)
    public int mPhotoId;

    @Column(name = COLUMN_OWNER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_OWNER_NAME)
    public String mOwnerName;

    @Column(name = COLUMN_DOG_ID)
    public int mDogId;

    @Column(name = COLUMN_URL_THUMBNAIL)
    public String mUrlThumbnail;

    @Column(name = COLUMN_URL_IMAGE)
    public String mUrlImage;

    @Column(name = COLUMN_DATE)
    public String mDate;

    @Column(name = COLUMN_DETAIL)
    public String mDetail;

    //FIXME después esto no tendría por qué estar.
    @Column(name = COLUMN_RESOURCE)
    public int mResource;

    public Photo() { }

    public Bitmap getThumbnail(Context context) {

        return null;
    }


    public Bitmap getPicture(int imageMaxSize) {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mUrlImage, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scale = Math.min(photoW/imageMaxSize, photoH/imageMaxSize);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(mUrlImage, bmOptions);

        return bitmap;
    }

    public Photo(int mPhotoId, int mOwnerId, String mOwnerName, int mDogId, String mUrlImage, String mDate,
                 String mDetail, int mResource) {

        this.mPhotoId = mPhotoId;
        this.mOwnerId = mOwnerId;
        this.mOwnerName = mOwnerName;
        this.mDogId = mDogId;
        this.mUrlImage = mUrlImage;
        this.mDate = mDate;
        this.mDetail = mDetail;
        this.mResource = mResource;
    }

    public static List<Photo> getPhotos(int dogId) {

        String condition = COLUMN_DOG_ID + DB._EQUALS_ + dogId;
        return new Select().from(Photo.class).where(condition).execute();

    }
}

