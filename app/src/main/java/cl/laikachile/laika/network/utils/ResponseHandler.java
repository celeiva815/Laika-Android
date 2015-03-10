package cl.laikachile.laika.network.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.R;
import cl.laikachile.laika.network.VolleyErrorHelper;


/**
 * Created by Tito_Leiva on 05-02-15.
 */
public class ResponseHandler {

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

    public static void successLogin(Context context, JSONObject response) {

        /* FIXME arreglar este método
        if (User.loginUser(context, response)) {
            context.startActivity(new Intent(context, LoadInformationActivity.class));
            ((LoginActivity) context).finish();

        } else {
            String message = Do.getRString(context, R.string.auth_failure_error);
            Do.showToast(message, context);

        } */
    }

}
