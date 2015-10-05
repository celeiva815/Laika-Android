package social.laika.app.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Tito_Leiva on 02-10-15.
 */
public interface Picturable {

    void setUriLocal(Bitmap bitmap, Context context, String folder);
}
