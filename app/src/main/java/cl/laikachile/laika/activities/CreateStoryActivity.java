package cl.laikachile.laika.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.interfaces.ImageHandlerInterface;
import cl.laikachile.laika.listeners.CreateStoryOnClickListener;
import cl.laikachile.laika.listeners.ImageHandler;
import cl.laikachile.laika.models.Story;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.CreateStoryResponse;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;

public class CreateStoryActivity extends ActionBarActivity implements ImageHandlerInterface {

    public static final String TAG = CreateStoryActivity.class.getSimpleName();
    public static final int LOCAL_ID = 0;

    private int mIdLayout = R.layout.lk_create_story_activity;
    public EditText mTitleEditText;
    public EditText mBodyEditText;
    public ImageView mStoryImageView;
    public Button mCreateButton;
    public Story mStory;
    public ImageHandler mImageHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setActivityView();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void setActivityView() {

        mTitleEditText = (EditText) findViewById(R.id.title_create_story_edittext);
        mBodyEditText = (EditText) findViewById(R.id.body_create_story_edittext);
        mCreateButton = (Button) findViewById(R.id.create_story_button);
        mStoryImageView = (ImageView) findViewById(R.id.adoption_story_imageview);
        mImageHandler = new ImageHandler(mStoryImageView);

        mCreateButton.setOnClickListener(new CreateStoryOnClickListener(this));

        mStory = getSavedStory();

        if (mStory != null) {

            mTitleEditText.setText(mStory.mTitle);
            mBodyEditText.setText(mStory.mBody);
        }

        mStoryImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Context context = v.getContext();
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setTitle(R.string.choose_an_option);
                dialog.setItems(mImageHandler.getOptions(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {

                                    case 0:

                                        takePicture();

                                        break;

                                    case 1:

                                        pickImage();

                                        break;

                                    case 2:

                                        beginCrop(mImageHandler.mSourceImage);

                                        break;
                                }
                            }
                        });

                dialog.show();

            }
        });

        mStoryImageView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if (mImageHandler.mSourceImage != null) {

                    beginCrop(mImageHandler.mSourceImage);
                    return true;

                } else {

                    return false;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == ImageHandler.SQUARE_CAMERA_REQUEST_CODE &&
                resultCode == RESULT_OK) {

            if (result != null) {

                beginCrop(result.getData());

            } else if (mImageHandler.mSourceImage != null) {

                beginCrop(mImageHandler.mSourceImage);

            } else {
                Do.showLongToast(R.string.generic_networking_error, getApplicationContext());
            }

            super.onActivityResult(requestCode, resultCode, result);

        }

        if (requestCode == Crop.REQUEST_PICK
                && resultCode == RESULT_OK) {

            beginCrop(result.getData());

        }  else if (requestCode == Crop.REQUEST_CROP) {
            mImageHandler.handleCrop(resultCode, result, this);

        }
    }

    @Override
    public void takePicture() {

       mImageHandler.takePicture(this, getApplicationContext());

    }

    @Override
    public void pickImage() {

        mImageHandler.pickImage(this);
    }

    @Override
    public void beginCrop(Uri source) {

        mImageHandler.beginCrop(source, this);
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

    public void sendStory(String imageUrl) {

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

        Story story = new Story(title, date, time, body, imageUrl);
        requestStory(story, getApplicationContext());

    }


    public void requestStory(Story story, Context context) {

        JSONObject params = story.getJsonObject();

        CreateStoryResponse response = new CreateStoryResponse(this);

        Request storyRequest = RequestManager.postRequest(params, RequestManager.ADDRESS_STORIES,
                response, response, PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(storyRequest, TAG);

    }

    public void saveStory() {

        int storyId = LOCAL_ID;
        String title = mTitleEditText.getText().toString();
        int userId = PrefsManager.getUserId(getApplicationContext());
        String ownerName = PrefsManager.getUserName(getApplicationContext());
        String date = Do.today();
        String time = Do.now();
        String body = mBodyEditText.getText().toString();
        String image = mImageHandler.getStringUri();

        Story story = new Story(storyId, title, userId, ownerName, date, time, body, image);
        mStory = Story.createOrUpdate(story);

    }

    public Story getSavedStory() {

        return Story.getSingleStory(LOCAL_ID);

    }


}

