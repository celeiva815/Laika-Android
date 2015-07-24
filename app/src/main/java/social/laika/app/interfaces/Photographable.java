package social.laika.app.interfaces;

import android.net.Uri;

/**
 * Created by Tito_Leiva on 02-07-15.
 */
public interface Photographable {

    void takePhoto();
    void pickPhoto();
    void cropPhoto(Uri source);
    void uploadPhoto();
    void succeedUpload();
    void failedUpload();
}
