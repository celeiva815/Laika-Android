package cl.laikachile.laika.network.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Owner;
import cl.laikachile.laika.models.Breed;
import cl.laikachile.laika.models.Personality;
import cl.laikachile.laika.models.Size;
import cl.laikachile.laika.network.RequestManager;
import cl.laikachile.laika.network.VolleyErrorHelper;
import cl.laikachile.laika.network.VolleyManager;
import cl.laikachile.laika.responses.FirstInformationResponse;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.PrefsManager;
import cl.laikachile.laika.utils.Tag;


/**
 * Created by Tito_Leiva on 05-02-15.
 */
public class ResponseHandler {

    public static final String TAG = ResponseHandler.class.getSimpleName();
    public final static int TYPE_SYNC = 1;
    public final static int TYPE_ASYNC = 2;

    public static void error(VolleyError error, Context context) {

        if (error != null && error.networkResponse != null) {
            if (error.networkResponse.statusCode == 400) {
                Toast.makeText(context, R.string.parameters_error,
                        Toast.LENGTH_SHORT).show();
            } else {
                String errorStr = VolleyErrorHelper.getMessage(error,
                        context);
                Toast.makeText(context, errorStr, Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(context, R.string.no_internet_error,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void successLogin(Activity activity, JSONObject response) {

        Context context = activity.getApplicationContext();

        if (!PrefsManager.isUserLoggedIn(context)) {

            String fullName, email, token;
            int userId;

            try {

                fullName = response.getString(RequestManager.FULL_NAME);
                email = response.getString(RequestManager.EMAIL);
                token = response.getString(RequestManager.ACCESS_TOKEN);
                userId = response.getInt(RequestManager.ID);

                PrefsManager.saveUser(context, fullName, email, token, userId);
                Owner userOwner = new Owner(userId, fullName, fullName, "", fullName, "", "",
                        Tag.GENDER_MALE, email, "", 1); //FIXME solicitar los datos correctos

                Owner.createOrUpdate(userOwner);
                restartDataBase(context);
                requestFirstInformation(context, activity);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            String message = Do.getRString(context, R.string.auth_failure_error);
            Do.showShortToast(message, context);

        }
    }

    public static void requestFirstInformation(Context context, Activity activity) {

        String token = PrefsManager.getUserToken(context);
        FirstInformationResponse response = new FirstInformationResponse(activity);

        //FIXME cambiar el address o agregar más requests
        Request firstRequest = RequestManager.getRequest(null, RequestManager.ADDRESS_DOGS,
                response, response, token);

        VolleyManager.getInstance(context)
                .addToRequestQueue(firstRequest, TAG);

    }

    public static void restartDataBase(Context context) {

        Size.setSizes(context);
        Personality.setPersonalities(context);

        //FIXME hacer el metodo de la API
        for (int i = Tag.SIZE_SMALLER, id = 0; i <= Tag.SIZE_BIGGER; i++) {

            String[] breedNames;

            if (i == Tag.SIZE_SMALLER) {
                breedNames = context.getResources().getStringArray(R.array.smaller_breed);

            } else if (i == Tag.SIZE_SMALL) {
                breedNames = context.getResources().getStringArray(R.array.small_breed);

            } else if (i == Tag.SIZE_MIDDLE) {
                breedNames = context.getResources().getStringArray(R.array.middle_breed);

            } else if (i == Tag.SIZE_BIG) {
                breedNames = context.getResources().getStringArray(R.array.big_breed);

            } else {
                breedNames = context.getResources().getStringArray(R.array.bigger_breed);

            }

            for (int j = 0; j < breedNames.length; j++, id++) {

                int number = j;

                if (breedNames[j].equals("Otro")) {
                    number = -1;
                }

                Breed breed = new Breed(id, number, i, breedNames[j]);
                breed.save();

            }
        }
    }
}
