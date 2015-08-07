package social.laika.app.listeners;

import social.laika.app.R;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddOwnerDogOnClickListener implements OnClickListener, Requestable {

    private static final String TAG = AddOwnerDogOnClickListener.class.getSimpleName();

    private int mIdLayout = R.layout.lk_add_owner_dialog;
    public Dog mDog;
    private EditText emailEditText;
    private Context mContext;
    private String mEmail;

    public AddOwnerDogOnClickListener(Dog mDog) {

        this.mDog = mDog;
    }

    @Override
    public void onClick(View v) {

        mContext = v.getContext();
        addOwner(mContext);
    }

    private View getView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mIdLayout, null, false);

        emailEditText = (EditText) view.findViewById(R.id.email_add_owners_my_dog_editext);

        return view;

    }

    public void addOwner(final Context context) {

        mContext = context;
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(getView(context));
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String email = emailEditText.getText().toString();

                if (!Do.isNullOrEmpty(email) && Do.isValidEmail(email)) {

                    mEmail = email;
                    request();

                }
            }
        });

        dialog.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        dialog.setTitle(Do.getRString(context, R.string.add_owners_my_dog));
        dialog.show();

    }

    @Override
    public void request() {

        Map<String, String> params = new HashMap<>();

        params.put(Owner.COLUMN_EMAIL, mEmail);
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDog.mDogId));

        JSONObject jsonObject = new JSONObject(params);
        SimpleResponse response = new SimpleResponse(this);
        String address = RequestManager.ADDRESS_ADD_DOG_OWNER;
        String token = PrefsManager.getUserToken(mContext);

        Request request = RequestManager.postRequest(jsonObject,address,response,response,token);
        VolleyManager.getInstance(mContext).addToRequestQueue(request, TAG);

    }

    @Override
    public void onSuccess() {

        String message = Do.getRString(mContext, R.string.sent_email_owners_my_dog) + " " +
                mEmail;

        Do.showLongToast(message, mContext);

    }

    @Override
    public void onFailure() {

    }
}
