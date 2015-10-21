package social.laika.app.listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import social.laika.app.R;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.AddOwnerResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

public class ForgetPasswordOnClickListener implements OnClickListener,
        Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String TAG = ForgetPasswordOnClickListener.class.getSimpleName();

    private int mIdLayout = R.layout.lk_add_owner_dialog;
    private EditText mEmailEditText;
    private Context mContext;
    private String mEmail;

    public ForgetPasswordOnClickListener() { }

    @Override
    public void onClick(View v) {

        mContext = v.getContext();
        recoverPassword(mContext);
    }

    private View getView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mIdLayout, null, false);

        TextView titleTextView = (TextView) view.findViewById(R.id.title_email_add_owners_my_dog_textview);
        mEmailEditText = (EditText) view.findViewById(R.id.email_add_owners_my_dog_editext);

        titleTextView.setText("Ingrese su correo electrónico");

        return view;

    }

    public void recoverPassword(final Context context) {

        mContext = context;
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(getView(context));
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String email = mEmailEditText.getText().toString();

                if (!Do.isNullOrEmpty(email) && Do.isValidEmail(email)) {

                    mEmail = email;
                    request();
                    Do.showShortToast("Por implementar", mContext);

                } else {

                    Do.showLongToast(R.string.not_valid_email_error, context);

                }
            }
        });

        dialog.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        dialog.setTitle("Recuperar contraseña");
        dialog.show();

    }

    public void request() {

        //TODO implementar este método
        Map<String, String> params = new HashMap<>();

        params.put(Owner.COLUMN_EMAIL, mEmail);

        JSONObject jsonObject = new JSONObject(params);
        String address = Api.ADDRESS_ADD_DOG_OWNER;
        String token = PrefsManager.getUserToken(mContext);

        Request request = Api.postRequest(jsonObject, address, this, this, token);
        VolleyManager.getInstance(mContext).addToRequestQueue(request, TAG);

        String message = "Te hemos enviado un correo con las instrucciones para que recuperes tu contraseña";
        Do.showShortToast(message, mContext);

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
