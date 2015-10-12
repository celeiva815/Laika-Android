package social.laika.app.listeners;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import social.laika.app.R;
import social.laika.app.activities.EditUserActivity;
import social.laika.app.activities.UserProfileActivity;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.LeaveDogResponse;
import social.laika.app.responses.TransferOwnershipResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

public class OwnerOptionsDialogOnClickListener implements OnClickListener {

    private static final String TAG = OwnerOptionsDialogOnClickListener.class.getSimpleName();
    private int mIdLayout = R.layout.ai_simple_textview_for_dialog;
    private final Dog mDog;
    private final Owner mOwner;
    private ProgressDialog mProgressDialog;

    public OwnerOptionsDialogOnClickListener(Dog mDog, Owner mOwner) {

        this.mDog = mDog;
        this.mOwner = mOwner;
    }

    @Override
    public void onClick(View v) {

        final Context context = v.getContext();

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle(R.string.choose_an_option);
        dialog.setItems(getOptions(context),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        if (mDog.mOwnerId == PrefsManager.getUserId(context)) {

                            if (mOwner.mOwnerId == PrefsManager.getUserId(context)) {

                                switch (which) {

                                    case 0: // ver mi perfil

                                        viewProfile(context);
                                        break;

                                    case 1: // editar mi perfil

                                        editProfile(context);
                                        break;

                                }

                            } else {

                                switch (which) {

                                    case 0: // ver perfil

                                        viewProfile(context);
                                        break;

                                    case 1: // llamar al dueño

                                        callUser(context);
                                        break;

                                    case 2: // enviar correo

                                        sendEmail(context);
                                        break;

                                    case 3: // transferir dueño

                                        transferOwnership(context);
                                        break;

                                }
                            }

                        } else {

                            if (mOwner.mOwnerId == PrefsManager.getUserId(context)) {

                                switch (which) {

                                    case 0:

                                        viewProfile(context);
                                        break;

                                    case 1:

                                        editProfile(context);
                                        break;

                                    case 2:

                                        leaveDog(context);
                                        break;
                                }

                            } else {

                                switch (which) {

                                    case 0:

                                        viewProfile(context);
                                        break;

                                    case 1:

                                        callUser(context);
                                        break;

                                    case 2:

                                        sendEmail(context);
                                        break;

                                }
                            }
                        }
                    }
                });

        dialog.show();
    }

    public CharSequence[] getOptions(Context context) {

        CharSequence[] sequence;

        if (mDog.mOwnerId == PrefsManager.getUserId(context)) {

            if (mOwner.mOwnerId == PrefsManager.getUserId(context)) {

                sequence = new CharSequence[]
                        {
                                Do.getRString(context, R.string.view_my_profile),
                                Do.getRString(context, R.string.edit_my_profile)
                        };

            } else {

                sequence = new CharSequence[]
                        {
                                Do.getRString(context, R.string.view_profile),
                                Do.getRString(context, R.string.call_owner) + " " + mOwner.mFirstName,
                                Do.getRString(context, R.string.send_email),
                                Do.getRString(context, R.string.delete_owner)
                        };
            }

        } else {

            if (mOwner.mOwnerId == PrefsManager.getUserId(context)) {

                sequence = new CharSequence[]
                        {
                                Do.getRString(context, R.string.view_my_profile),
                                Do.getRString(context, R.string.edit_my_profile),
                                Do.getRString(context, R.string.leave_dog)
                        };

            } else {

                sequence = new CharSequence[]
                        {
                                Do.getRString(context, R.string.view_profile),
                                Do.getRString(context, R.string.call_owner) + " " + mOwner.mFirstName,
                                Do.getRString(context, R.string.send_email)
                        };
            }
        }

        return sequence;
    }

    public void viewProfile(Context context) {

        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.KEY_OWNER_ID, mOwner.mOwnerId);
        context.startActivity(intent);

    }

    public void editProfile(Context context) {

        Do.changeActivity(context, EditUserActivity.class,
                Intent.FLAG_ACTIVITY_NEW_TASK);

    }

    public void callUser(Context context) {

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + mOwner.mPhone));
        context.startActivity(intent);

    }

    public void sendEmail(Context context) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{mOwner.mEmail});
        i.putExtra(Intent.EXTRA_SUBJECT, "");
        i.putExtra(Intent.EXTRA_TEXT, "");
        try {
            context.startActivity(Intent.createChooser(i, Do.getRString(context, R.string.send_email)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.email_error_activity, Toast.LENGTH_SHORT).show();
        }
    }

    public void transferOwnership(final Context context) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        String message = Do.getRString(context, R.string.do_you_want) + " " + mOwner.mFirstName
                + " " + Do.getRString(context, R.string.new_responsible_of) + " " + mDog.mName +
                Do.getRString(context, R.string.question_mark);

        dialog.setTitle(R.string.transfer_ownership);
        dialog.setMessage(message);
        dialog.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                requestTransferDog(context);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void leaveDog(final Context context) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        String message = Do.getRString(context, R.string.leave_responsible_question) + " " +
                mDog.mName + Do.getRString(context, R.string.question_mark);

        dialog.setTitle(R.string.leave_dog);
        dialog.setMessage(message);
        dialog.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                requestLeaveDog(context);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void requestLeaveDog(Context context) {

        mProgressDialog = ProgressDialog.show(context, "Espere un momento", "Estamos actualizando" +
                " la información de tus perros");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Dog.COLUMN_DOG_ID, mDog.mDogId);
            jsonObject.put(Owner.API_USER_ID, mOwner.mOwnerId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

       LeaveDogResponse response = new LeaveDogResponse(context, mDog, mOwner,
                mProgressDialog);

        Request postulationRequest = Api.postRequest(jsonObject,
                Api.ADDRESS_REMOVE_DOG_OWNER, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(postulationRequest, TAG);
    }

    public void requestTransferDog(Context context) {

        mProgressDialog = ProgressDialog.show(context, "Espere un momento", "Estamos transfieriendo" +
                " la responsabilidad del perro al nuevo dueño");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Dog.COLUMN_DOG_ID, mDog.mDogId);
            jsonObject.put(Owner.API_USER_ID, mOwner.mOwnerId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TransferOwnershipResponse response = new TransferOwnershipResponse(context, mDog, mOwner,
                mProgressDialog);

        Request postulationRequest = Api.postRequest(jsonObject,
                Api.ADDRESS_TRANSFER_OWNERSHIP, response, response,
                PrefsManager.getUserToken(context));

        VolleyManager.getInstance(context)
                .addToRequestQueue(postulationRequest, TAG);
    }
}
