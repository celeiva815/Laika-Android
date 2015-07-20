package cl.laikachile.laika.activities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.fourmob.datetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.adapters.BreedAdapter;
import cl.laikachile.laika.adapters.PersonalityAdapter;
import cl.laikachile.laika.adapters.SizeAdapter;
import cl.laikachile.laika.listeners.NewDogOnClickListener;
import cl.laikachile.laika.listeners.ChangeDogBreedsOnItemSelectedListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.Breed;
import cl.laikachile.laika.models.Personality;
import cl.laikachile.laika.models.Photo;
import cl.laikachile.laika.models.Size;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.CreateDogResponse;
import cl.laikachile.laika.responses.ImageUploadResponse;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;

public class CreateDogActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = CreateDogActivity.class.getSimpleName();
    public static final String KEY_DOG_ID = "dog_id";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    protected int mIdLayout = R.layout.lk_new_dog_register_activity;

    public Dog mDog;
    public EditText mNameEditText;
    public Spinner mSizeSpinner;
    public Spinner mBreedSpinner;
    public BreedAdapter mBreedAdapter;
    public Button mBirthButton;
    public Spinner mPersonalitySpinner;
    public EditText mChipEditText;
    public Button mAddButton;
    public RadioGroup mGenderRadioGroup;
    public RadioGroup mSterilizedRadioGroup;
    public RadioGroup mChipRadioGroup;
    public ProgressBar mProgressBar;
    public ProgressBar mImageProgressBar;
    public ImageView mPictureImageView;
    public int mGender;
    public boolean mSterilized;
    public boolean mChip;
    public String mChipCode;
    public String mCurrentPhotoPath;

    public String mDate;
    public boolean mIsSizeSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setActivityView();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setActivityView() {

        mNameEditText = (EditText) findViewById(R.id.name_new_dog_register_edittext);
        mGenderRadioGroup = (RadioGroup) findViewById(R.id.gender_new_dog_register_radiogroup);
        mBirthButton = (Button) findViewById(R.id.birth_new_dog_register_button);
        mSizeSpinner = (Spinner) findViewById(R.id.size_new_dog_register_spinner);
        mBreedSpinner = (Spinner) findViewById(R.id.type_new_dog_register_spinner);
        mPersonalitySpinner = (Spinner) findViewById(R.id.personality_new_dog_register_spinner);
        mSterilizedRadioGroup = (RadioGroup) findViewById(R.id.sterilized_new_dog_register_radiogroup);
        mChipEditText = (EditText) findViewById(R.id.chip_code_new_dog_register_edittext);
        mChipRadioGroup = (RadioGroup) findViewById(R.id.chip_new_dog_register_radiogroup);
        mAddButton = (Button) findViewById(R.id.add_dog_new_dog_register_button);
        mPictureImageView = (ImageView) findViewById(R.id.picture_create_dog_imageview);
        mProgressBar = (ProgressBar) findViewById(R.id.new_dog_progressbar);
        mImageProgressBar = (ProgressBar) findViewById(R.id.download_image_progressbar);

        SizeAdapter sizeAdapter = new SizeAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview, getSizeList());
        mBreedAdapter = new BreedAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview, getBreedList(mSizeSpinner));
        PersonalityAdapter personalityAdapter = new PersonalityAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview, getPersonalityList());
        ChangeDogBreedsOnItemSelectedListener breedListener = new ChangeDogBreedsOnItemSelectedListener(this);
        NewDogOnClickListener addListener = new NewDogOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), false);

        mBirthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog.setYearRange(1990, calendar.get(Calendar.YEAR));
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getSupportFragmentManager(), Tag.DATE_PICKER);
            }
        });

        mDate = Do.getToStringDate(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        mBirthButton.setText(mDate);

        mSizeSpinner.setAdapter(sizeAdapter);
        mBreedSpinner.setAdapter(mBreedAdapter);
        mSizeSpinner.setOnItemSelectedListener(breedListener);
        mPersonalitySpinner.setAdapter(personalityAdapter);

        if (mChip) {
            mChipCode = mChipEditText.getText().toString();
        }

        mPictureImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                takePicture();
            }
        });

        mAddButton.setOnClickListener(addListener);

    }

    public void requestNewOrEditDog(Dog dog, String message, int method) {

        enableViews(false);
        JSONObject jsonObject = dog.getJsonObject();

        CreateDogResponse response = new CreateDogResponse(this, message);
        Request loginRequest = RequestManager.defaultRequest(method, jsonObject,
                RequestManager.ADDRESS_DOGS, response, response,
                PrefsManager.getUserToken(getApplicationContext()));

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(loginRequest, TAG);

    }

    public List<Personality> getPersonalityList() {
        return Personality.getPersonalities();
    }


    public List<Size> getSizeList() {
        return Size.getSizes();
    }

    public List<Breed> getBreedList(Spinner sizeSpinner) {

        int sizeId = (int) sizeSpinner.getSelectedItemId();
        return Breed.getBreeds(sizeId);

    }

    public void setGenderRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mGender = Tag.GENDER_MALE;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male_new_dog_register_radiobutton:
                if (checked)
                    mGender = Tag.GENDER_MALE;

                break;
            case R.id.female_new_dog_register_radiobutton:
                if (checked)
                    mGender = Tag.GENDER_FEMALE;

                break;
        }
    }

    public void setSterilizedRadioButton(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mSterilized = false;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.sterilized_new_dog_register_radiobutton:
                if (checked)
                    this.mSterilized = true;

                break;
            case R.id.not_sterilized_new_dog_register_radiobutton:
                if (checked)
                    this.mSterilized = false;

                break;
        }
    }

    public void setChipRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mChip = false;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.chip_new_dog_register_radiobutton:
                if (checked) {
                    mChip = true;
                    mChipEditText.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.not_chip_new_dog_register_radiobutton:
                if (checked) {
                    mChip = false;
                    mChipEditText.setVisibility(View.GONE);
                    mChipEditText.setText("");
                }

                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        mDate = Do.getToStringDate(day, month, year);
        mBirthButton.setText(mDate);

    }

    public void enableViews(boolean enabled) {

        mSizeSpinner.setEnabled(enabled);
        mNameEditText.setEnabled(enabled);
        mPersonalitySpinner.setEnabled(enabled);
        mChipEditText.setEnabled(enabled);
        mBreedSpinner.setEnabled(enabled);
        mAddButton.setEnabled(enabled);
        mBirthButton.setEnabled(enabled);
        mGenderRadioGroup.setEnabled(enabled);
        mSterilizedRadioGroup.setEnabled(enabled);
        mChipRadioGroup.setEnabled(enabled);
        mProgressBar.setEnabled(!enabled);

        if (enabled) {
            mAddButton.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);

        } else {
            mAddButton.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Bitmap imageBitmap = setPicture();
            String imageName = getImageName(PrefsManager.getUserId(getApplicationContext()));
            String encodedImage = encodeImage(imageBitmap);

            postPicture(imageName, encodedImage);

        } else if (resultCode == RESULT_CANCELED) {

            Toast.makeText(this, "La fotografía fue cancelada", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "No se pudo tomar la fotografía", Toast.LENGTH_SHORT).show();
        }
    }

    public void takePicture() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the mPhoto should go
            File photoFile = null;
            try {

                photoFile = createImageFile();

            } catch (IOException ex) {
                // Error occurred while creating the File
                Do.showShortToast("Problem creating the picture", getApplicationContext());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        }

    }

    public File createImageFile() throws IOException {
        // Create an image file name

        Context context = getApplicationContext();

        mCurrentPhotoPath = "";

        String imageFileName = getImageName(PrefsManager.getUserId(context)) + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFileName);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    public String getImageName(int userId) {

        int[] dateArray = Do.dateInArray();
        int[] timeArray = Do.timeInArray();

        String date = "";

        for (int i : dateArray) {

            date += Integer.toString(i);
        }

        for (int i : timeArray) {

            date += Integer.toString(i);
        }

        date += "user" + Integer.toString(userId);

        return date;

    }

    private Bitmap setPicture() {

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

    public void postPicture(String name, String image) {

        Context context = getApplicationContext();

        String token = PrefsManager.getUserToken(context);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Dog.COLUMN_DOG_ID, mDog.mDogId);
            jsonObject.put(Photo.API_PHOTO, Photo.getJsonPhoto(name, image));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageUploadResponse response = new ImageUploadResponse(mDog, context,
                mPictureImageView, mImageProgressBar, ImageUploadResponse.TYPE_PROFILE);

        Request imageRequest = RequestManager.postRequest(jsonObject,
                RequestManager.ADDRESS_USER_DOG_PHOTOS, response, response, token);

        VolleyManager.getInstance(context).addToRequestQueue(imageRequest, TAG);

    }

    public String encodeImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }

}
