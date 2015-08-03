package social.laika.app.network.requests;

import android.content.Context;

import social.laika.app.interfaces.Requestable;

/**
 * Created by Tito_Leiva on 31-07-15.
 */
public abstract class BaseRequest implements Requestable {

    public Context mContext;

    public BaseRequest(Context mContext) {
        this.mContext = mContext;
    }

}
