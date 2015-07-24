package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.CreateStoryActivity;
import social.laika.app.utils.Do;

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
        Do.showLongToast("¡Gracias por compartir tu historia! Pronto estará disponible en Laika",
                context);

        mActivity.onBackPressed();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity);
        mActivity.saveStory();
        Do.showShortToast("Hubo un error al subir tu historia, pero la hemos guardado!", mActivity);

    }

}
