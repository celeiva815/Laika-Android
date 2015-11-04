package social.laika.app.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.flurry.android.Constants;
import com.flurry.android.FlurryAgent;

import java.util.Map;

import social.laika.app.models.Owner;

/**
 * Created by Tito_Leiva on 03-11-15.
 */
public class Flurry {

    private static final String FLURRY_APIKEY = "MD2D6K5MZSK3SM3FY6T6";

    //Time
    public static final String SESSION_TIME = "session_time";
    public static final String DOG_PROFILE_TIME = "dog_profile_time";
    public static final String DOG_REMINDERS_TIME = "dog_reminders_time";
    public static final String DOG_VET_VISIT_TIME = "dog_vet_visit_time";
    public static final String DOG_ALBUM_TIME = "dog_album_time";
    public static final String LOGIN_TIME = "login_time";
    public static final String FB_LOGIN_TIME = "login_time";
    public static final String NEWS_TIME = "news_time";
    public static final String EVENTS_TIME = "events_time";
    public static final String TIPS_TIME = "tips_time";
    public static final String STORIES_TIME = "stories_time";

    //Events
    public static final String NEWS_VIEW = "news_view";
    public static final String NEWS_CLICK = "news_click";
    public static final String NEWS_SHARE = "news_share";
    public static final String NEWS_FAVORITE = "news_favorite";
    public static final String TIPS_VIEW = "tips_view";
    public static final String TIPS_CLICK = "tips_click";
    public static final String TIPS_SHARE = "tips_share";
    public static final String TIPS_FAVORITE = "tips_favorite";
    public static final String EVENT_VIEW = "event_view";
    public static final String EVENT_CLICK = "event_click";
    public static final String EVENT_SHARE = "event_share";
    public static final String EVENT_FAVORITE = "event_favorite";
    public static final String STORY_VIEW = "story_view";
    public static final String STORY_CLICK = "story_click";
    public static final String STORY_SHARE = "story_share";
    public static final String STORY_FAVORITE = "story_favorite";


    public static void logEvent(String eventId) {

        FlurryAgent.logEvent(eventId);
        Log.i("Flurry", "starting event: " + eventId);
    }

    public static void logEvent(String eventId, Map<String, String> map) {

        FlurryAgent.logEvent(eventId, map);
        Log.i("Flurry", "starting event: " + eventId + ". Parameters: " + map.toString());
    }

    public static void logTimedEvent(String eventId) {

        FlurryAgent.logEvent(eventId, true);
        Log.i("Flurry", "starting timed event: " + eventId);
    }

    public static void logTimedEvent(String eventId, Map<String, String> map) {

        FlurryAgent.logEvent(eventId, map, true);
        Log.i("Flurry", "starting timed event: " + eventId + ". Parameters: " + map.toString());
    }

    public static void endTimedEvent(String eventId) {

        FlurryAgent.endTimedEvent(eventId);
        Log.i("Flurry", "ending timed event:" + eventId);
    }

    public static void endTimedEvent(String eventId, Map<String, String> map) {

        FlurryAgent.endTimedEvent(eventId, map);
        Log.i("Flurry", "ending timed event:" + eventId + ". Parameters: " + map.toString());
    }

    public static void setConfigurations(Context context) {

        // configure Flurry
        FlurryAgent.setLogEnabled(false);
        FlurryAgent.setLogEvents(true);
        FlurryAgent.setVersionName(Do.getVersionName(context));

        Owner owner = PrefsManager.getLoggedOwner(context);
        if (owner != null) {

            FlurryAgent.setUserId(Integer.toString(owner.mOwnerId));
            FlurryAgent.setGender(owner.mGender == Tag.GENDER_MALE ?
                    Constants.MALE : Constants.FEMALE);
            FlurryAgent.setAge(owner.getAge());
        }

        LocationManager manager = Do.getLocationManager(context);

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            FlurryAgent.setLocation((float) location.getLatitude(), (float) location.getLongitude());

        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            FlurryAgent.setLocation((float) location.getLatitude(), (float) location.getLongitude());

        }

        // init Flurry
        FlurryAgent.init(context, FLURRY_APIKEY);
    }


}