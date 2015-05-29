package cl.laikachile.laika.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;

import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.CreateStoryOnClickListener;
import cl.laikachile.laika.models.Story;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.CreateStoryResponse;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;

public class CreateStoryActivity extends ActionBarActivity {

    public static final String TAG = CreateStoryActivity.class.getSimpleName();
    public static final int LOCAL_ID = 0;
    
    private int mIdLayout = R.layout.lk_create_story_activity;
    public EditText mTitleEditText;
    public EditText mBodyEditText;
    public Button mCreateButton;
    public Story mStory;

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

        mCreateButton.setOnClickListener(new CreateStoryOnClickListener(this));

        mStory = getSavedStory();

        if (mStory != null) {

            mTitleEditText.setText(mStory.mTitle);
            mBodyEditText.setText(mStory.mBody);
        }
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
        int image = R.drawable.abuela; //FIXME cambiarlo a String cuando se implementen las imagenes

        Story story = new Story(storyId,title,userId,ownerName,date,time,body, image);
        mStory = Story.createOrUpdate(story);

    }

    public Story getSavedStory(){

        return Story.getSingleStory(LOCAL_ID);

    }

}
