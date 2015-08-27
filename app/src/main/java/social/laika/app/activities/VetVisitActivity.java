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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.activeandroid.Model;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import social.laika.app.R;
import social.laika.app.interfaces.Photographable;
import social.laika.app.listeners.PhotographerListener;
import social.laika.app.models.Dog;
import social.laika.app.models.Photo;
import social.laika.app.models.VetVisit;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.CreateVetVisitResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.Photographer;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

public class VetVisitActivity extends ActionBarActivity
        implements DatePickerDialog.OnDateSetListener {

    public int mIdLayout = R.layout.lk_vet_visit_activity;

    private static final String TAG = VetVisitActivity.class.getSimpleName();
    public static final String KEY_DOG = "dog_id";
    public static final String KEY_VET_VISIT = "vet_visit";

    public TextView mNameTextView;
    public TextView mDoctorTextView;
    public TextView mReasonTextView;
    public TextView mDetailTextView;
    public TextView mTreatmentTextView;
    public Button mDateButton;
    public String mDate;
    public ProgressDialog mProgressDialog;
    public ProgressBar mProgressBar;
    public Dog mDog;
    public VetVisit mVetVisit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int dogId = getIntent().getExtras().getInt(KEY_DOG);
        int vetVisitId = getIntent().getExtras().getInt(KEY_VET_VISIT);
        mDog = Dog.getSingleDog(dogId);
        mVetVisit = VetVisit.getSingleVetVisit(vetVisitId);

        setContentView(mIdLayout);
        setActivityView();
        setValues();

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

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), false);

        mNameTextView = (TextView) findViewById(R.id.name_vet_visit_textview);
        mDoctorTextView = (TextView) findViewById(R.id.doctor_vet_visit_textview);
        mReasonTextView = (TextView) findViewById(R.id.reason_vet_visit_textview);
        mDetailTextView = (TextView) findViewById(R.id.detail_vet_visit_textview);
        mTreatmentTextView = (TextView) findViewById(R.id.treatment_vet_visit_textview);
        mDateButton = (Button) findViewById(R.id.birthdate_vet_visit_button);
        mProgressBar = (ProgressBar) findViewById(R.id.download_image_progressbar);

        mDate = Do.getToStringDate(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        mDateButton.setText(mDate);
    }

    private void setValues() {

        if (mVetVisit != null) {

            mNameTextView.setText(mVetVisit.mVetName);
            mDoctorTextView.setText(mVetVisit.mVetDoctor);
            mDateButton.setText(mVetVisit.mDate);
            mReasonTextView.setText(mVetVisit.mReason);
            mDetailTextView.setText(mVetVisit.mDetail);
            mTreatmentTextView.setText(mVetVisit.mTreatment);

        }
    }

    @Override
    protected void onPause() {
        VolleyManager.getInstance(getApplicationContext()).cancelPendingRequests(TAG);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (!this.getClass().equals(MainActivity.class))
            getMenuInflater().inflate(R.menu.vet_visit_menu, menu);

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

        } else if (id == R.id.edit_vet_visit) {
            editVetVisit(mVetVisit);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

        mDate = Do.getToStringDate(day, month, year);
        mDateButton.setText(mDate);

    }

    public void editVetVisit(VetVisit vetVisit) {

        Context context = getApplicationContext();
        Intent intent = new Intent(context, CreateVetVisitActivity.class);
        intent.putExtra(CreateVetVisitActivity.KEY_VET_VISIT, vetVisit.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
