package social.laika.app;

import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.Logger;

import social.laika.app.utils.Flurry;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class LaikaApplication extends MultiDexApplication {

    protected static final String TAG_APP = "LaikaApp";

    @Override
    public void onCreate() {
        super.onCreate();

        // Init Logger
        initLogger();

        // Default font
        initCalligraphy();

        Flurry.setConfigurations(this);
    }

    protected void initLogger() {
        Logger
                .init(TAG_APP)
                        //.setMethodCount(0)
                .hideThreadInfo();
    }

    protected void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig
                .Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath).build());
    }

}
