package social.laika.app.interfaces;

import android.net.Uri;

/**
 * Created by Tito_Leiva on 02-07-15.
 */
public interface Requestable {

    void request();
    void onSuccess();
    void onFailure();
}
