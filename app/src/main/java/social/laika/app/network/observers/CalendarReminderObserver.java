package social.laika.app.network.observers;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.activeandroid.Model;

import social.laika.app.models.CalendarReminder;
import social.laika.app.network.sync.SyncUtils;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 10-08-15.
 */
public class CalendarReminderObserver extends ContentObserver {
    public static final String TAG = CalendarReminderObserver.class.getSimpleName();
    public CalendarReminder mCalendarReminder;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public CalendarReminderObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri changeUri) {

        Bundle settingsBundle = new Bundle();
        Log.d(TAG, changeUri.toString());

        try {

            mCalendarReminder = Model.load(CalendarReminder.class,
                    Long.parseLong(changeUri.getLastPathSegment()));

            if (mCalendarReminder != null && mCalendarReminder.mNeedsSync > Tag.FLAG_READED) {

                settingsBundle.putInt(SyncUtils.CODE, SyncUtils.CODE_CALENDAR_SYNC);
                SyncUtils.triggerRefresh(settingsBundle);
            }

        } catch (NumberFormatException e) {
            Log.i("URI", "CalendarReminder deleted");
            return;
        }

    }
}
