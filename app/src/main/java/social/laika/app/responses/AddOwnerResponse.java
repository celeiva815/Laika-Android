package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.R;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.models.OwnerDog;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class AddOwnerResponse implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    public static final String API_SENT_EMAIL = "sent_email";

    public Dog mDog;
    public Context mContext;
    public Requestable mRequest;
    public String mEmail;

    public AddOwnerResponse() {
    }

    public AddOwnerResponse(Dog mDog, String mEmail, Context mContext, Requestable mRequest) {
        this.mDog = mDog;
        this.mContext = mContext;
        this.mRequest = mRequest;
        this.mEmail = mEmail;
    }

    @Override
    public void onResponse(JSONObject response) {

        String message = "";

        if (response.has(Owner.API_OWNERS)) {
            Owner.saveOwners(response, mContext, mDog);

            message = Do.getRString(mContext, R.string.sent_email_owners_my_dog) + " " +
                    mEmail;

        } else if (response.has(API_SENT_EMAIL) && response.optBoolean(API_SENT_EMAIL, false)) {

            message = "El usuario " + mEmail + " no está registrado en Laika, pero lo acabamos de invitar." +
                    "\nAgrégalo como dueño de " + mDog.mName + " una vez que se haya registrado."; //TODO agregar el string

        } else {

            message = "Lo sentimos! No pudimos enviar la invitación. Inténtalo nuevamente más tarde";
        }

        Do.showLongToast(message, mContext);
        mRequest.onSuccess();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        if (mRequest != null)
            mRequest.onFailure();

    }
}