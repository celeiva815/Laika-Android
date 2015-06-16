package cl.laikachile.laika.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.CreateStoryActivity;
import cl.laikachile.laika.utils.Do;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class CreateStoryResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public CreateStoryActivity mActivity;

    public CreateStoryResponse(CreateStoryActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        Context context = mActivity.getApplicationContext();
        Do.showShortToast("¡Gracias por compartir tu historia! Pronto estará disponible en nuestra " +
                "aplicación ", context);

        mActivity.onBackPressed();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity);
        Do.showShortToast("Hubo un error", mActivity);

    }

}
