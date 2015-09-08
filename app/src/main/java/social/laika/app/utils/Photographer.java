package social.laika.app.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;

import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import social.laika.app.network.Api;
import social.laika.app.utils.camera.CameraActivity;

/**
 * Created by Tito_Leiva on 01-07-15.
 */
public class Photographer {

    public static final int SQUARE_CAMERA_REQUEST_CODE = 0;
    public static final int TAKE_PICTURE_REQUEST_CODE = 1;

    public Uri mSourceImage;
    public String mCurrentPhotoPath;
    public boolean mImageChange;

    public Photographer() {

        this.mImageChange = false;
    }

    public CharSequence[] getOptions() {


        if (mSourceImage != null) {

            CharSequence[] sequence = {
                    "Tomar una foto",
                    "Elegir de la galería",
                    "Editar foto actual"
            };

            return sequence;

        } else {

            CharSequence[] sequence = {
                    "Tomar una foto",
                    "Elegir de la galería"
            };

            return sequence;
        }

    }

    public void pickImage(Activity activity) {

        Crop.pickImage(activity);

    }

    public void beginCrop(Uri source, Activity activity) {

        this.mSourceImage = source;
        Uri destination = Uri.fromFile(new File(activity.getCacheDir(), "cropped"));
        Crop.of(mSourceImage, destination).asSquare().start(activity);
    }

    public void handleCrop(int resultCode, Intent result, Activity activity, ImageView imageView) {

        if (resultCode == activity.RESULT_OK) {

            imageView.setImageDrawable(null);
            imageView.setImageURI(Crop.getOutput(result));
            mImageChange = true;

        } else if (resultCode == Crop.RESULT_ERROR) {
            Do.showShortToast(Crop.getError(result).getMessage(), activity.getApplicationContext());
        }
    }

    public void takePicture(Activity activity) {

        Context context = activity.getApplicationContext();

        String fileName = getImageName(context) + ".jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        mSourceImage = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mSourceImage);
        Intent startCustomCameraIntent = new Intent(activity, CameraActivity.class);
        activity.startActivityForResult(startCustomCameraIntent, SQUARE_CAMERA_REQUEST_CODE);

    }

    /*
    public void takePicture(Activity activity) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the mPhoto should go
            File photoFile = null;
            try {

                photoFile = createImageFile(activity.getApplicationContext());

            } catch (IOException ex) {
                // Error occurred while creating the File
                Do.showShortToast("Hubo un problema creando la imagen", activity.getApplicationContext());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                activity.startActivityForResult(takePictureIntent, TAKE_PICTURE_REQUEST_CODE);
            }
        }

    } */

    public File createImageFile(Context context) throws IOException {
        // Create an image file name
        mCurrentPhotoPath = "";

        String imageFileName = getImageName(context) + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFileName);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        return image;
    }

    public void galleryAddPic(Activity activity) {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        mSourceImage = Uri.fromFile(f);
        mediaScanIntent.setData(mSourceImage);
        activity.sendBroadcast(mediaScanIntent);
    }

    public String getImageName(Context context) {

        int[] dateArray = Do.dateInArray();
        int[] timeArray = Do.timeInArray();

        String date = "";

        date += dateArray[2] + dateArray[1] + dateArray[0];
        date += timeArray[0] + timeArray[1];

        date += "user" + Integer.toString(PrefsManager.getUserId(context));

        return date;

    }

    public JSONObject getJsonPhoto(Context context) {

        JSONObject jsonPhoto = new JSONObject();
        Bitmap bitmap;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),
                    mSourceImage);

            String encodedImage = encodeImage(bitmap);
            String fileName = getImageName(context);
            jsonPhoto = Api.getJsonPhoto(encodedImage, fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonPhoto;
    }

    public Bitmap setPicture() {

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / 640, photoH / 480);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        bmOptions.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        return bitmap;
    }

    public String encodeImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }

    public String getStringUri() {

        if (mSourceImage != null) {

            return mSourceImage.toString();

        } else {

            return "";
        }
    }

    public boolean hasPhotoChanged() {

        return mSourceImage != null && mImageChange;
    }

    public Uri parseUri(String uriString) {

        mSourceImage = Uri.parse(uriString);

        return mSourceImage;

    }


}
