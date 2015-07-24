package social.laika.app.listeners;

import social.laika.app.R;
import social.laika.app.activities.AdoptDogUserFormActivity;
import social.laika.app.models.AdoptDogForm;
import social.laika.app.utils.PrefsManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SubmitUserAdoptionFormOnClickListener implements OnClickListener {

    private int mIdLayout = R.layout.ai_simple_textview_for_dialog;
    public AdoptDogUserFormActivity mActivity;
    public ProgressDialog mProgressDialog;


    public SubmitUserAdoptionFormOnClickListener(AdoptDogUserFormActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        final Context context = v.getContext();
        dialog.setView(getView(context));
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                int ownerId = PrefsManager.getUserId(context);
                int locationId = mActivity.mCity.mCityId;
                int homeType = (int) mActivity.mHomeSpinner.getSelectedItemId();
                boolean hasPet = mActivity.mPets;
                boolean hasElderly = mActivity.mElderly;
                boolean hasKids = mActivity.mKids;
                int freeTime = (int) mActivity.mFreeTimeSpinner.getSelectedItemId();
                String phone = mActivity.mPhoneEditText.getText().toString();

                AdoptDogForm adoptDogForm = AdoptDogForm.newInstance(ownerId,locationId, phone,
                        homeType, hasPet, hasElderly,hasKids,freeTime);

                mActivity.requestAdoptionDogForm(adoptDogForm);

            }
        });
        dialog.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private View getView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mIdLayout, null, false);

        TextView question = (TextView) view.findViewById(R.id.simple_textview);
        question.setText("Estimado DogLover:\nLa información de este formulario es valiosa para " +
                "Laika y sus fundaciones.\n ¿Estás seguro de enviar este formulario de adopción?");

        return view;

    }

    public boolean checkField() {


        return true;
    }

}
