package social.laika.app;

import android.util.Log;

import com.activeandroid.app.Application;
import social.laika.app.utils.Flurry;

/**
 * Created by Tito_Leiva on 02-11-15.
 */
public class LaikaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Flurry.setConfigurations(this);
    }
}
