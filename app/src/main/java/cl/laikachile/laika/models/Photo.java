package cl.laikachile.laika.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 13-03-15.
 */
@Table(name = Photo.TABLE_PHOTO)
public class Photo extends Model {


    public static int ID = 0;

    public final static String TABLE_PHOTO = "photos";
    public final static String COLUMN_PHOTO_ID = "photo_id";
    public final static String COLUMN_USER_ID = "user_id";
    public final static String COLUMN_OWNER_NAME = "owner_name";
    public final static String COLUMN_DOG_ID = "dog_id";
    public final static String COLUMN_URL_THUMBNAIL = "url_thumbnail";
    public final static String COLUMN_URL = "url";
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_DETAIL = "detail";

    public static final String API_PHOTO = "photo";
    public static final String API_CONTENT = "content";
    public static final String API_FILE_NAME = "file_name";
    public static final String API_USER = "user";

    @Column(name = COLUMN_PHOTO_ID)
    public int mPhotoId;

    @Column(name = COLUMN_USER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_OWNER_NAME)
    public String mOwnerName;

    @Column(name = COLUMN_DOG_ID)
    public int mDogId;

    @Column(name = COLUMN_URL_THUMBNAIL)
    public String mUrlThumbnail;

    @Column(name = COLUMN_URL)
    public String mUrlImage;

    @Column(name = COLUMN_TIME)
    public String mTime;

    @Column(name = COLUMN_DATE)
    public String mDate;

    @Column(name = COLUMN_DETAIL)
    public String mDetail;

    public Photo() { }


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
                 String mDetail) {

        this.mPhotoId = mPhotoId;
        this.mOwnerId = mOwnerId;
        this.mOwnerName = mOwnerName;
        this.mDogId = mDogId;
        this.mUrlImage = mUrlImage;
        this.mDate = mDate;
        this.mDetail = mDetail;
    }

    public void update(Photo photo) {

        this.mPhotoId = photo.mPhotoId;
        this.mOwnerId = photo.mOwnerId;
        this.mOwnerName = photo.mOwnerName;
        this.mDogId = photo.mDogId;
        this.mUrlImage = photo.mUrlImage;
        this.mDate = photo.mDate;
        this.mDetail = photo.mDetail;

        this.save();

    }

    public Photo(JSONObject jsonObject, Context context, Dog dog) {

        this.mPhotoId = jsonObject.optInt(COLUMN_PHOTO_ID);
        this.mOwnerId = jsonObject.optInt(COLUMN_USER_ID, PrefsManager.getUserId(context));
        this.mOwnerName = jsonObject.optString(API_USER, PrefsManager.getUserName(context));
        this.mDogId = jsonObject.optInt(COLUMN_DOG_ID, dog.mDogId);
        this.mUrlImage = jsonObject.optString(COLUMN_URL);
        this.mDate = jsonObject.optString(COLUMN_DATE);
        this.mTime = jsonObject.optString(COLUMN_TIME);
        this.mDetail = jsonObject.optString(COLUMN_DETAIL, "");
        this.mUrlThumbnail = getImage(Tag.IMAGE_THUMB);
    }

    public static Photo savePhoto(JSONObject jsonObject, Context context, Dog dog) {

        Photo photo = new Photo(jsonObject, context, dog);
        return createOrUpdate(photo);

    }

    public static Photo createOrUpdate(Photo photo) {

        if (!isSaved(photo.mPhotoId)) {
            photo.save();
        }

        return photo;
    }

    public static boolean isSaved(int photoId) {

        String condition = COLUMN_PHOTO_ID + DB.EQUALS + photoId;
        return new Select().from(Photo.class).where(condition).exists();

    }

    public static Photo getPhoto(int photoId) {

        String condition = COLUMN_PHOTO_ID + DB.EQUALS + photoId;
        return new Select().from(Photo.class).where(condition).executeSingle();

    }

    public static List<Photo> getPhotos(int dogId) {

        String condition = COLUMN_DOG_ID + DB.EQUALS + dogId;
        return new Select().from(Photo.class).where(condition).execute();

    }

    public String getImage(String size) {

        String url = mUrlImage.replaceAll("original", size);
        return url;

    }

    public static JSONObject getJsonPhoto(String name, String image) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(API_FILE_NAME, name);
            jsonObject.put(API_CONTENT, image);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}

