package social.laika.app.network.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Tito_Leiva on 30-07-15.
 */
public class LaikaIdListenerService extends InstanceIDListenerService {

    public static final String TAG = LaikaIdListenerService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {

        Intent intent = new Intent(this, LaikaRegistrationIntentService.class);
        startService(intent);
    }
}
