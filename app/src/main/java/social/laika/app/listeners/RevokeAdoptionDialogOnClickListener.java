package social.laika.app.listeners;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import social.laika.app.R;
import social.laika.app.activities.MyDogsActivity;
import social.laika.app.activities.PostulatedDogsFragmentActivity;
import social.laika.app.models.Dog;
import social.laika.app.models.UserAdoptDog;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.RevokeAdoptionResponse;
import social.laika.app.utils.Flurry;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

public class RevokeAdoptionDialogOnClickListener implements OnClickListener {

    public static final String TAG = RevokeAdoptionDialogOnClickListener.class.getSimpleName();

    private int mIdLayout = R.layout.ai_simple_textview_for_dialog;
    private final Dog mDog;
    private final UserAdoptDog mUserAdoptDog;
    private final Activity mActivity;
    public ProgressDialog mProgressDialog;

    public RevokeAdoptionDialogOnClickListener(Dog mDog, Activity activity,
                                               ProgressDialog mProgressDialog,
                                               UserAdoptDog mUserAdoptDog) {

        this.mDog = mDog;
        this.mActivity = activity;
        this.mProgressDialog = mProgressDialog;
        this.mUserAdoptDog = mUserAdoptDog;
    }

    @Override
    public void onClick(View view) {

        switch(mUserAdoptDog.mStatus) {

            case Tag.POSTULATION_WAITING:
            case Tag.POSTULATION_ACCEPTED:

                revokePostulation(view.getContext());

                break;
            case Tag.POSTULATION_DISABLED:
            case Tag.POSTULATION_REVOKED:
            case Tag.POSTULATION_REFUSED:


                deletePostulation();

                break;

            case Tag.POSTULATION_ADOPTED:

                goToDog(view.getContext());

                break;

        }


    }

    private View getView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mIdLayout, null, false);

        TextView question = (TextView) view.findViewById(R.id.simple_textview);
        question.setText("¿Estás seguro de que deseas revocar por la adopción de " + mDog.mName + "?");

        return view;

    }

    public void deletePostulation() {

        mDog.mStatus = Tag.DOG_DELETED;
        mDog.save();

        ((PostulatedDogsFragmentActivity) mActivity).updateDogs();

    }

    public void goToDog(Context context) {

        Intent intent = new Intent(context, MyDogsActivity.class);
        intent.putExtra(MyDogsActivity.DOG_ID, mDog.mDogId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    public void revokePostulation(final Context context) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(getView(context));
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                mProgressDialog = ProgressDialog.show(context, "Revocando...",
                        "Enviando la cancelación de la postulación");

                requestRevokePostulation();
                Flurry.logEvent(Flurry.ADOPTION_DOG_REVOCATION);
                dialog.dismiss();
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

    public void requestRevokePostulation() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(UserAdoptDog.COLUMN_USER_ADOPT_DOG_ID, mUserAdoptDog.mUserAdoptDogId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RevokeAdoptionResponse response = new RevokeAdoptionResponse(mActivity,
                mProgressDialog, mDog, mUserAdoptDog);

        Request postulationRequest = Api.postRequest(jsonObject,
                Api.ADDRESS_CANCEL_POSTULATION, response, response,
                PrefsManager.getUserToken(mActivity.getApplicationContext()));

        VolleyManager.getInstance(mActivity.getApplicationContext())
                .addToRequestQueue(postulationRequest, TAG);
    }


}