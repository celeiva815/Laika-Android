package social.laika.app.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import social.laika.app.utils.DB;
import social.laika.app.utils.Do;
import social.laika.app.utils.Photographer;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 13-03-15.
 */
@Table(name = Photo.TABLE_PHOTOS)
public class Photo extends Model {

    public static int ID = 0;

    public final static String TABLE_PHOTOS = "photos";
    public final static String COLUMN_PHOTO_ID = "photo_id";
    public final static String COLUMN_USER_ID = "user_id";
    public final static String COLUMN_OWNER_NAME = "owner_name";
    public static final String COLUMN_FILE_NAME = "file_name";
    public final static String COLUMN_DOG_ID = "dog_id";
    public final static String COLUMN_URL_THUMB = "thumb_url";
    public final static String COLUMN_URL_SMALL = "small_url";
    public final static String COLUMN_URL_MEDIUM = "medium_url";
    public final static String COLUMN_URL_LARGE = "large_url";
    public final static String COLUMN_URL_ORIGINAL = "original_url";
    public final static String COLUMN_URI_LOCAL = "local_urli";
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_DETAIL = "detail";
    public final static String COLUMN_NEEDS_SYNC = "need_sync";

    public static final String API_PHOTO = "photo";
    public static final String API_CONTENT = "content";
    public static final String API_FILE_NAME = "file_name";
    public static final String API_USER = "user";
    public static final String API_IS_PROFILE = "is_profile";

    @Column(name = COLUMN_PHOTO_ID, unique = true)
    public int mPhotoId;

    @Column(name = COLUMN_USER_ID)
    public int mOwnerId;

    @Column(name = COLUMN_OWNER_NAME)
    public String mOwnerName;

    @Column(name = COLUMN_DOG_ID)
    public int mDogId;

    @Column(name = COLUMN_URL_ORIGINAL)
    public String mUrlOriginal;

    @Column(name = COLUMN_URL_LARGE)
    public String mUrlLarge;

    @Column(name = COLUMN_URL_MEDIUM)
    public String mUrlMedium;

    @Column(name = COLUMN_URL_SMALL)
    public String mUrlSmall;

    @Column(name = COLUMN_URL_THUMB)
    public String mUrlThumbnail;

    @Column(name = COLUMN_URI_LOCAL)
    public String mUriLocal;

    @Column(name = COLUMN_FILE_NAME)
    public String mFileName;

    @Column(name = COLUMN_TIME)
    public String mTime;

    @Column(name = COLUMN_DATE)
    public String mDate;

    @Column(name = COLUMN_DETAIL)
    public String mDetail;

    @Column(name = COLUMN_NEEDS_SYNC)
    public int mNeedsSync;

    public Photo() { }


    public Photo(int mPhotoId, int mOwnerId, String mOwnerName, int mDogId, String mUrlOriginal, String mDate,
                 String mDetail) {

        this.mPhotoId = mPhotoId;
        this.mOwnerId = mOwnerId;
        this.mOwnerName = mOwnerName;
        this.mDogId = mDogId;
        this.mUrlOriginal = mUrlOriginal;
        this.mDate = mDate;
        this.mDetail = mDetail;
    }

    public Photo(JSONObject jsonObject, Context context, Dog dog) {

        this.mPhotoId = jsonObject.optInt(COLUMN_PHOTO_ID);
        this.mOwnerId = jsonObject.optInt(COLUMN_USER_ID, PrefsManager.getUserId(context));
        this.mOwnerName = jsonObject.optString(API_USER, PrefsManager.getUserName(context));
        this.mDogId = jsonObject.optInt(COLUMN_DOG_ID, dog.mDogId);
        this.mUrlOriginal = jsonObject.optString(COLUMN_URL_ORIGINAL);
        this.mUrlLarge = jsonObject.optString(COLUMN_URL_LARGE);
        this.mUrlMedium = jsonObject.optString(COLUMN_URL_MEDIUM);
        this.mUrlSmall = jsonObject.optString(COLUMN_URL_SMALL);
        this.mUrlThumbnail = jsonObject.optString(COLUMN_URL_THUMB);
        this.mFileName = jsonObject.optString(COLUMN_FILE_NAME);
        this.mDate = jsonObject.optString(COLUMN_DATE);
        this.mTime = jsonObject.optString(COLUMN_TIME);
        this.mDetail = jsonObject.optString(COLUMN_DETAIL, "");
        this.mUrlThumbnail = getImage(Tag.IMAGE_THUMB);
        this.mNeedsSync = Tag.FLAG_READED;
    }

    public Photo(JSONObject jsonObject, Context context) {

        this.mPhotoId = jsonObject.optInt(COLUMN_PHOTO_ID);
        this.mOwnerId = jsonObject.optInt(COLUMN_USER_ID, PrefsManager.getUserId(context));
        this.mDogId = jsonObject.optInt(COLUMN_DOG_ID);
        this.mOwnerName = jsonObject.optString(API_USER, PrefsManager.getUserName(context));
        this.mUrlOriginal = jsonObject.optString(COLUMN_URL_ORIGINAL);
        this.mUrlLarge = jsonObject.optString(COLUMN_URL_LARGE);
        this.mUrlMedium = jsonObject.optString(COLUMN_URL_MEDIUM);
        this.mUrlSmall = jsonObject.optString(COLUMN_URL_SMALL);
        this.mUrlThumbnail = jsonObject.optString(COLUMN_URL_THUMB);
        this.mFileName = jsonObject.optString(COLUMN_FILE_NAME);
        this.mDate = jsonObject.optString(COLUMN_DATE);
        this.mTime = jsonObject.optString(COLUMN_TIME);
        this.mDetail = jsonObject.optString(COLUMN_DETAIL, "");
        this.mUrlThumbnail = getImage(Tag.IMAGE_THUMB);
        this.mNeedsSync = Tag.FLAG_READED;
    }

    public void create() {

        this.mNeedsSync = Tag.FLAG_CREATED;
        this.save();
        Log.i("Laika Sync Service", "Photo created. Local ID:" + getId() + ". Need Sync");

    }

    public void refresh() {

        this.mNeedsSync = Tag.FLAG_READED;
        this.save();

        Log.i("Laika Sync Service", "Photo refreshed. Local ID: " + getId() + ". " +
                "Server ID:" + mPhotoId);
    }

    public void update() {

        if (this.mNeedsSync == Tag.FLAG_READED) {
            this.mNeedsSync = Tag.FLAG_UPDATED;
        }
        this.save();
        Log.i("Laika Sync Service", "Photo updated. Local ID: " + getId() + ". " +
                "Server ID:" + mPhotoId + ". Need Sync");
    }

    public void remove() {

        this.mNeedsSync = Tag.FLAG_DELETED;

        if (mNeedsSync == Tag.FLAG_CREATED) {

            Log.i("Laika Sync Service", "Photo deleted. Local ID: " + getId());
            this.delete();
        } else {

            this.save();
        }

        Log.i("Laika Sync Service", "Photo removed. Local ID: " + getId() + ". " +
                "Server ID:" + mPhotoId  + ". Need Sync");
    }

    private void update(Photo photo) {

        this.mPhotoId = photo.mPhotoId;
        this.mOwnerId = photo.mOwnerId;
        this.mOwnerName = photo.mOwnerName;
        this.mDogId = photo.mDogId;
        this.mUrlOriginal = photo.mUrlOriginal;
        this.mDate = photo.mDate;
        this.mDetail = photo.mDetail;
        this.mNeedsSync = photo.mNeedsSync;

        this.save();

    }

    public JSONObject getJsonObject() {

        //FIXME definir bien que es lo que se va a mandar al servidor
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(COLUMN_PHOTO_ID, mPhotoId);
            jsonObject.put(COLUMN_USER_ID, mOwnerId);
            jsonObject.put(COLUMN_OWNER_NAME, mOwnerName);
            jsonObject.put(COLUMN_DOG_ID, mDogId);
            jsonObject.put(COLUMN_URL_THUMB, mUrlThumbnail);
            jsonObject.put(COLUMN_URL_SMALL, mUrlSmall);
            jsonObject.put(COLUMN_URL_MEDIUM, mUrlMedium);
            jsonObject.put(COLUMN_URL_LARGE, mUrlLarge);
            jsonObject.put(COLUMN_URL_ORIGINAL, mUrlOriginal);
            jsonObject.put(COLUMN_TIME, mTime);
            jsonObject.put(COLUMN_DATE, mDate);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return jsonObject;
    }

    public Bitmap getPicture(int imageMaxSize) {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mUrlOriginal, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scale = Math.min(photoW/imageMaxSize, photoH/imageMaxSize);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(mUrlOriginal, bmOptions);

        return bitmap;
    }

    public static void saveDogPhotos(JSONObject jsonObject, Context context, Dog dog) {

        try {
            JSONArray jsonPhotos = jsonObject.getJSONArray(TABLE_PHOTOS);

            for (int i = 0; i < jsonPhotos.length(); i++) {
                saveDogPhoto(jsonPhotos.getJSONObject(i), context, dog);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Photo saveDogPhoto(JSONObject jsonObject, Context context, Dog dog) {

        if (jsonObject.has(API_PHOTO)) {

            JSONObject jsonPhoto = jsonObject.optJSONObject(API_PHOTO);
            Photo photo = new Photo(jsonPhoto, context, dog);
            return createOrUpdate(photo);

        } else {

            Photo photo = new Photo(jsonObject, context, dog);
            return createOrUpdate(photo);

        }

    }

    public static void saveDogPhotos(JSONObject jsonObject, Context context) {

        if (jsonObject.has(TABLE_PHOTOS)) {
            try {
                JSONArray jsonPhotos = jsonObject.getJSONArray(TABLE_PHOTOS);

                for (int i = 0; i < jsonPhotos.length(); i++) {
                    saveDogPhoto(jsonPhotos.getJSONObject(i), context);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public static Photo saveDogPhoto(JSONObject jsonObject, Context context) {

        if (jsonObject.has(API_PHOTO)) {

            JSONObject jsonPhoto = jsonObject.optJSONObject(API_PHOTO);
            Photo photo = new Photo(jsonPhoto, context);
            return createOrUpdate(photo);

        } else {

            Photo photo = new Photo(jsonObject, context);
            return createOrUpdate(photo);

        }

    }

    public static Photo getPhoto(JSONObject jsonObject, Context context) {

        JSONObject jsonPhoto = jsonObject.optJSONObject(API_PHOTO);

        if (jsonPhoto != null) {

            Photo photo = new Photo(jsonPhoto, context);
            return photo;

        }

        return null;

    }

    public static Photo createOrUpdate(Photo photo) {

        photo.mNeedsSync = Tag.FLAG_READED;

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

    public static List<Photo> getNeedSync() {

        String condition = Photo.COLUMN_NEEDS_SYNC + DB.GREATER_THAN + Tag.FLAG_READED;
        return new Select().from(Photo.class).where(condition).execute();
    }

    public String getImage(String size) {

        String url = mUrlOriginal.replaceAll("original", size);
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

    public void setUri(Bitmap bitmap, Context context) {

        OutputStream fOut = null;
        Uri outputFileUri;

        try {
            File root = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "thumbs" + File.separator);
            root.mkdirs();

            String filename = new Photographer().getImageName(context);

            if (!Do.isNullOrEmpty(mFileName)) {

                filename = mFileName;
            }

            File sdImageMainDirectory = new File(root, filename);
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            fOut = new FileOutputStream(sdImageMainDirectory);
            mUriLocal = outputFileUri.toString();

            this.save();

        } catch (Exception e) {

            Do.showShortToast("No se pudo guardar la foto", context);
        }

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

        } catch (Exception e) {
        }

    }

    public static void deleteAll() {

        new Delete().from(Photo.class).execute();
    }

    public static void deleteAll(Dog dog) {

        String condition = AlarmReminder.COLUMN_DOG_ID + DB.EQUALS + dog.mDogId;
        new Delete().from(Photo.class).where(condition).execute();
    }
}

