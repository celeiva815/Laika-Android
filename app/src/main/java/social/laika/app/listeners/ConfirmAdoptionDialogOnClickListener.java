package social.laika.app.listeners;

import social.laika.app.R;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.ConfirmAdoptionResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmAdoptionDialogOnClickListener implements OnClickListener {

    public static final String TAG = ConfirmAdoptionDialogOnClickListener.class.getSimpleName();

    private int mIdLayout = R.layout.ai_simple_textview_for_dialog;
    private final Dog mDog;
    private final Activity mActivity;
    private final ImageView mPictureImageView;
    public ProgressDialog mProgressDialog;

    public ConfirmAdoptionDialogOnClickListener(Dog mDog, Activity activity,
                                                ProgressDialog mProgressDialog,
                                                ImageView mPictureImageView) {

        this.mDog = mDog;
        this.mActivity = activity;
        this.mProgressDialog = mProgressDialog;
        this.mPictureImageView = mPictureImageView;
    }

    @Override
    public void onClick(View v) {

        if (isGrownUp(v.getContext())) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
            final Context context = v.getContext();
            dialog.setView(getView(context));
            dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    mProgressDialog = ProgressDialog.show(context, "Postulando...",
                            "Enviando notificación de postulación");

                    requestPostulation();
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

        } else {

            Do.showLongToast("¡Lo sentimos! Debes ser mayor de edad para adoptar perritos.",
                    v.getContext());
            Do.showLongToast("Invita a tus padres o familia a Laika para que postulen por la " +
                    "adopción de " + mDog.mName + ".", v.getContext());

        }
    }

    private View getView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mIdLayout, null, false);

        TextView question = (TextView) view.findViewById(R.id.simple_textview);
        question.setText("¿Estás seguro de que deseas postular por la adopción de " + mDog.mName +
                "?");

        return view;
    }

    public boolean isGrownUp(Context context) {

        Owner owner = PrefsManager.getLoggedOwner(context);

        return owner.isGrownUp();
    }

    public void requestPostulation() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Dog.COLUMN_DOG_ID, mDog.mDogId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConfirmAdoptionResponse response = new ConfirmAdoptionResponse(mActivity,
                mProgressDialog, mDog, getDogBitmap());

        Request postulationRequest = Api.postRequest(jsonObject,
                Api.ADDRESS_CONFIRM_POSTULATION, response, response,
                PrefsManager.getUserToken(mActivity.getApplicationContext()));

        VolleyManager.getInstance(mActivity.getApplicationContext())
                .addToRequestQueue(postulationRequest, TAG);
    }

    public Bitmap getDogBitmap() {

        return ((BitmapDrawable) mPictureImageView.getDrawable()).getBitmap();

    }

}