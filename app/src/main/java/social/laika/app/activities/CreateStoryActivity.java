package social.laika.app.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import social.laika.app.R;
import social.laika.app.interfaces.Photographable;
import social.laika.app.listeners.CreateStoryOnClickListener;
import social.laika.app.listeners.PhotographerListener;
import social.laika.app.models.DogPhoto;
import social.laika.app.utils.Photographer;
import social.laika.app.models.Story;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.CreateStoryResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

public class CreateStoryActivity extends ActionBarActivity implements Photographable {

    public static final String TAG = CreateStoryActivity.class.getSimpleName();
    public static final int LOCAL_ID = 0;

    private int mIdLayout = R.layout.lk_create_story_activity;
    public EditText mTitleEditText;
    public EditText mBodyEditText;
    public ImageView mStoryImageView;
    public Button mCreateButton;
    public Story mStory;
    public Photographer mPhotographer;
    public ProgressDialog mProgressDialog;

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

        Do.hideKeyboard(this);
    }

    public void setActivityView() {

        mTitleEditText = (EditText) findViewById(R.id.title_create_story_edittext);
        mBodyEditText = (EditText) findViewById(R.id.body_create_story_edittext);
        mCreateButton = (Button) findViewById(R.id.create_story_button);
        mStoryImageView = (ImageView) findViewById(R.id.adoption_story_imageview);
        mPhotographer = new Photographer();

        mCreateButton.setOnClickListener(new CreateStoryOnClickListener(this));

        mStory = getSavedStory();

        if (mStory != null) {

            mTitleEditText.setText(mStory.mTitle);
            mBodyEditText.setText(mStory.mBody);
        }

        PhotographerListener listener = new PhotographerListener(mPhotographer, this);

        mStoryImageView.setOnClickListener(listener);
        mStoryImageView.setOnLongClickListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == Photographer.SQUARE_CAMERA_REQUEST_CODE &&
                resultCode == RESULT_OK) {

            if (result != null) {

                cropPhoto(result.getData());

            } else if (mPhotographer.mSourceImage != null) {

                cropPhoto(mPhotographer.mSourceImage);

            } else {
                Do.showLongToast(R.string.generic_networking_error, getApplicationContext());
            }

            super.onActivityResult(requestCode, resultCode, result);

        }

        if (requestCode == Crop.REQUEST_PICK
                && resultCode == RESULT_OK) {

            cropPhoto(result.getData());

        }  else if (requestCode == Crop.REQUEST_CROP) {
            mPhotographer.handleCrop(resultCode, result, this, mStoryImageView);

        }
    }

    @Override
    public void takePhoto() {

       mPhotographer.takePicture(this);

    }

    @Override
    public void pickPhoto() {

        mPhotographer.pickImage(this);
    }

    @Override
    public void cropPhoto(Uri source) {

        mPhotographer.beginCrop(source, this);
    }

    @Override
    public void uploadPhoto() {


    }

    @Override
    public void succeedUpload() {

    }

    @Override
    public void failedUpload() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.create_story_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:

                super.onBackPressed();
                return true;

            case R.id.save_story:

                saveStory();
                Do.showLongToast("La historia ha sido guardada", CreateStoryActivity.this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendStory() {

        mProgressDialog = ProgressDialog.show(CreateStoryActivity.this, "Espere un momento",
                "Subiendo la historia...");

        String title = mTitleEditText.getText().toString();
        String body = mBodyEditText.getText().toString();

        if (TextUtils.isEmpty(title)) {
            mBodyEditText.setError(getString(R.string.field_not_empty_error));
            return;
        }

        if (TextUtils.isEmpty(body)) {
            mBodyEditText.setError(getString(R.string.field_not_empty_error));
            return;

        }

        String date = Do.today();
        String time = Do.now();

        Story story = new Story(title, date, time, body, null);
        postStory(story, getApplicationContext());

    }


    public void postStory(Story story, Context context) {

        try {

            saveStory();

            JSONObject params = story.getJsonObject();
            JSONObject jsonPhoto = mPhotographer.getJsonPhoto(getApplicationContext());
            params.put(DogPhoto.API_PHOTO, jsonPhoto);

            CreateStoryResponse response = new CreateStoryResponse(this);

            Request storyRequest = RequestManager.postRequest(params, RequestManager.ADDRESS_STORIES,
                    response, response, PrefsManager.getUserToken(context));

            storyRequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleyManager.getInstance(context)
                    .addToRequestQueue(storyRequest, TAG);

        } catch (JSONException e) {

            Do.showLongToast("La foto no pudo ser adjuntada", context);
            e.printStackTrace();
        } catch (NullPointerException e) {

            Do.showLongToast("Debes adjuntar una foto antes de enviar la historia", context);
            e.printStackTrace();
        }

    }

    public void saveStory() {

        int storyId = LOCAL_ID;
        String title = mTitleEditText.getText().toString();
        int userId = PrefsManager.getUserId(getApplicationContext());
        String ownerName = PrefsManager.getUserName(getApplicationContext());
        String date = Do.today();
        String time = Do.now();
        String body = mBodyEditText.getText().toString();
        String image = mPhotographer.getStringUri();

        Story story = new Story(storyId, title, userId, ownerName, date, time, body, image);
        mStory = Story.createOrUpdate(story);

    }

    public Story getSavedStory() {

        return Story.getSingleStory(LOCAL_ID);

    }


}

