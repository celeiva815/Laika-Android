package cl.laikachile.laika.interfaces;

import android.net.Uri;

/**
 * Created by Tito_Leiva on 02-07-15.
 */
public interface ImageHandlerInterface {

    void takePicture();
    void pickImage();
    void beginCrop(Uri source);
}
