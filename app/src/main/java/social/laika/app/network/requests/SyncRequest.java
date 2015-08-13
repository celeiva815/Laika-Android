package social.laika.app.network.requests;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import social.laika.app.interfaces.Requestable;

/**
 * Created by Tito_Leiva on 31-07-15.
 */
public abstract class SyncRequest {

    public static final String SUCCESS = "success";
    public Context mContext;

    public SyncRequest(Context mContext) {
        this.mContext = mContext;
    }

    public abstract void sync() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException;

    public abstract JSONObject refresh() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException;

    protected abstract JSONObject create() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException;

    protected abstract JSONObject update() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException;

    protected abstract JSONObject delete() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException;



}
