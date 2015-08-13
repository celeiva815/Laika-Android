package social.laika.app.network.observers;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.activeandroid.Model;

import social.laika.app.models.Photo;
import social.laika.app.network.sync.SyncUtils;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 10-08-15.
 */
public class PhotoObserver extends ContentObserver {

    public Photo mPhoto;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public PhotoObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri changeUri) {

        Bundle settingsBundle = new Bundle();

        try {

            mPhoto = Model.load(Photo.class,
                    Long.parseLong(changeUri.getLastPathSegment()));

            if (mPhoto != null && mPhoto.mNeedsSync > Tag.FLAG_READED) {

                settingsBundle.putInt(SyncUtils.CODE, SyncUtils.CODE_ALARM_SYNC);
                SyncUtils.requestSync(settingsBundle);
            }

        } catch (NumberFormatException e) {
            Log.i("URI", "Photo deleted");
            return;
        }

    }
}
