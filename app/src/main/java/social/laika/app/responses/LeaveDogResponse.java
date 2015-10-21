package social.laika.app.responses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.fragments.AlbumMyDogFragment;
import social.laika.app.models.Dog;
import social.laika.app.models.Photo;
import social.laika.app.utils.Do;

/**
 * Created by Tito_Leiva on 24-07-15.
 */
public class LeaveDogResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public Dog mDog;
    public Context mContext;
    public Activity mActivity;
    public ProgressDialog mProgressDialog;

    public LeaveDogResponse(Dog mDog, Context context, ProgressDialog progressDialog) {
        this.mDog = mDog;
        this.mActivity = ((Activity) context);
        this.mContext = context;
        this.mProgressDialog = progressDialog;
    }

    @Override
    public void onResponse(JSONObject response) {

        Do.showLongToast("Haz dejado de ser responsable de " + mDog.mName, mContext);
        mProgressDialog.dismiss();
        mActivity.finish();
        mDog.removeDog(mContext);

    }


    @Override
    public void onErrorResponse(VolleyError error) {

        mProgressDialog.dismiss();
        Do.showLongToast("No se ha podido dejar a " + mDog.mName +".\nPor favor, inténtalo más tarde",
                mContext);
    }
}
