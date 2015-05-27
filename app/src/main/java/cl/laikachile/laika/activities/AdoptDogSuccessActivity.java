package cl.laikachile.laika.activities;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.UserAdoptDog;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.ImageResponse;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;

public class AdoptDogSuccessActivity extends ActionBarActivity {

    public static final String TAG = AdoptDogSuccessActivity.class.getSimpleName();

    public int mIdLayout = R.layout.lk_adopt_dog_success_activity;
    public Dog mDog;
    public UserAdoptDog mUserAdoptDog;
    public Bitmap mDogBitmap;
    public ProgressBar mProgressBar;
    public ImageView mPictureImageView;

    public int xDelta;
    public int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mIdLayout);
        setInformation();
        setActivityView();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.laika_red));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mDog.mDogImage == null) {
            requestDogImage(mPictureImageView);

        } else {
            mPictureImageView.setImageBitmap(mDog.mDogImage);

        }
    }

    public void setActivityView() {

        final LinearLayout congratsLinearLayout = (LinearLayout) findViewById(R.id.congrats_adopt_dog_success_linearlayout);
        TextView congratsTextView = (TextView) findViewById(R.id.congrats_adopt_dog_success_textview);
        TextView happyTextView = (TextView) findViewById(R.id.happy_news_adopt_dog_success_textview);
        TextView contactTextView = (TextView) findViewById(R.id.contact_news_adopt_dog_success_textview);
        TextView matchTextView = (TextView) findViewById(R.id.match_adopt_dog_success_textview);
        mPictureImageView = (ImageView) findViewById(R.id.picture_adopt_dog_success_imageview);
        mProgressBar = (ProgressBar) findViewById(R.id.download_image_progressbar);

        congratsTextView.setText(getCongratsMessage());
        happyTextView.setText(getHappyNewsMessage());
        contactTextView.setText(getContactMessage());
        matchTextView.setText(Integer.toString(Do.randomInteger(50, 100)) + "%"); //FIXME que sea el que viene desde el servidor.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.adopt_dog_success_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.back_home:
            case android.R.id.home:
                backToHome();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        backToHome();

    }

    private String getContactMessage() {

        String first = Do.getRString(getApplicationContext(), R.string.contact_news_adopt_dog_success_first);
        String foundation = "Fundación Stuka"; //FIXME agregar la fundación
        String second = Do.getRString(getApplicationContext(), R.string.contact_news_adopt_dog_success_second);

        return first + " " + foundation + " " + second;
    }

    private String getCongratsMessage() {

        String name = PrefsManager.getUserName(getApplicationContext());
        String congrats = Do.getRString(getApplicationContext(), R.string.congrats_adopt_dog_success);

        return congrats + " " + name + "!";
    }

    private String getHappyNewsMessage() {

        String name = mDog.mName;
        String happy = getResources().getString(R.string.happy_news_adopt_dog_success);

        return name + " " + happy;
    }

    public void backToHome() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();

    }

    public void setInformation() {

        Bundle b = getIntent().getExtras();

        int dogId = b.getInt(Dog.COLUMN_DOG_ID, 0);
        this.mDog = Dog.getSingleDog(dogId);

        int userAdoptDogId = b.getInt(UserAdoptDog.COLUMN_USER_ADOPT_DOG_ID, 0);
        this.mUserAdoptDog = UserAdoptDog.getSingleUserAdoptDog(userAdoptDogId);

    }

    public void requestDogImage(ImageView imageView) {

        mProgressBar.setVisibility(View.VISIBLE);
        ImageResponse response = new ImageResponse(this, imageView, mProgressBar);
        Request imageRequest = RequestManager.imageRequest(mDog.mUrlImage, imageView, response,
                response);

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(imageRequest, TAG);

    }

}
