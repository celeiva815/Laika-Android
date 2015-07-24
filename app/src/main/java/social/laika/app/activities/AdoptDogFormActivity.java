package social.laika.app.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.adapters.PersonalityAdapter;
import social.laika.app.adapters.SizeAdapter;
import social.laika.app.models.Personality;
import social.laika.app.models.Size;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.DogForAdoptionResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

public class AdoptDogFormActivity extends ActionBarActivity {

    public static final String TAG = AdoptDogFormActivity.class.getSimpleName();
    public static final String API_LIMIT = "limit";
    public static final String API_DOG_SIZE = "dog_size";
    public static final String API_DOG_GENDER = "dog_gender";
    public static final String API_DOG_PERSONALITY = "dog_personality";


    private int mIdLayout = R.layout.lk_adopt_dog_form_activity;
    public Spinner mSizeSpinner;
    public Spinner mPersonalitySpinner;
    public Button mSearchButton;
    public int mGender;
    public ProgressDialog mProgressDialog;

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

    @Override
    protected void onStart() {
        super.onStart();

        Do.hideKeyboard(this);
    }

    public void setActivityView() {

        //View creation
        mSizeSpinner = (Spinner) findViewById(R.id.size_dog_form_spinner);
        mPersonalitySpinner = (Spinner) findViewById(R.id.personality_dog_form_spinner);
        mSearchButton = (Button) findViewById(R.id.search_dog_form_button);
        mGender = Tag.GENDER_BOTH;

        //Adapters creation
        SizeAdapter sizeAdapter = new SizeAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                getSizes());

        PersonalityAdapter personalityAdapter = new PersonalityAdapter(this.getApplicationContext(),
                R.layout.ai_simple_textview_for_adapter, R.id.simple_textview,
                getPersonalities());

        //Setting the adapters
        mSizeSpinner.setAdapter(sizeAdapter);
        mPersonalitySpinner.setAdapter(personalityAdapter);

        //Setting the listeners
        mSearchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                requestDogsForAdoption();

            }
        });

    }

    public void requestDogsForAdoption() {

        mProgressDialog = ProgressDialog.show(AdoptDogFormActivity.this, "Espere un momento...",
                "Estamos buscando perritos para ti");

        Map<String,String> params = new HashMap<>();
        DogForAdoptionResponse response = new DogForAdoptionResponse(this);
        int dogSize = (int) mSizeSpinner.getSelectedItemId();
        int dogPersonality = (int) mPersonalitySpinner.getSelectedItemId();

        params.put(API_DOG_GENDER, Integer.toString(mGender));
        params.put(API_DOG_SIZE, Integer.toString(dogSize));
        params.put(API_DOG_PERSONALITY, Integer.toString(dogPersonality));
        params.put(API_LIMIT, Integer.toString(10));

        Request adoptDogRequest = RequestManager.getRequest(params,
                RequestManager.ADDRESS_GET_MATCHING_DOGS, response, response,
                PrefsManager.getUserToken(getApplicationContext()));

        VolleyManager.getInstance(getApplicationContext())
                .addToRequestQueue(adoptDogRequest, TAG);

    }

    public void setGenderFormRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        this.mGender = Tag.GENDER_BOTH;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male_dog_form_radiobutton:
                if (checked) {
                    mGender = Tag.GENDER_MALE;
                }
                break;

            case R.id.female_dog_form_radiobutton:
                if (checked) {
                    mGender = Tag.GENDER_FEMALE;
                }
                break;

            case R.id.both_dog_form_radiobutton:
                if (checked) {
                    mGender = Tag.GENDER_BOTH;
                }
                break;
        }
    }

    public List<Size> getSizes() {
        return Size.getSizes();
    }

    public List<Personality> getPersonalities() {
        return Personality.getPersonalities();
    }

    public void showDialog(String title, String message) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());

        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(true);
        dialog.show();

    }

}
