package social.laika.app.models.publications;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import social.laika.app.utils.DB;
import social.laika.app.utils.Do;
import social.laika.app.utils.Photographer;

public abstract class BasePublication extends Model {

    public final static String COLUMN_SERVER_ID = "server_id" ;
    public final static String COLUMN_URL = "url" ;
    public final static String COLUMN_URL_IMAGE = "url_image";
    public final static String COLUMN_URL_IMAGE_LOCAL = "url_image_local";
    public final static String COLUMN_IS_PAID = "is_paid";
    public final static String COLUMN_IS_FAVORITE = "is_favorite";

    @Column(name = COLUMN_SERVER_ID)
    public int mServerId;

	@Column(name = COLUMN_URL_IMAGE)
	public String mUrlImage;

    @Column(name = COLUMN_URL)
    public String mUrl;

    @Column(name = COLUMN_URL_IMAGE_LOCAL)
    public String mUriLocal;

    @Column(name = COLUMN_IS_PAID)
    public boolean mIsPaid;

    @Column(name = COLUMN_IS_FAVORITE)
    public boolean mIsFavorite;

	public BasePublication(){ }

    public void setIsFavorite(boolean isFavorite) {

        mIsFavorite = isFavorite;
        this.save();
    }

    public void setUriLocal(Bitmap bitmap, Context context, String folder) {

        OutputStream fOut = null;
        Uri outputFileUri;

        try {
            File root = new File(Environment.getExternalStorageDirectory()
                    + File.separator + folder + File.separator);
            root.mkdirs();

            String filename = new Photographer().getImageName(context, folder + mServerId);

            File sdImageMainDirectory = new File(root, filename);
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            fOut = new FileOutputStream(sdImageMainDirectory);
            mUriLocal = outputFileUri.toString();

            this.save();

        } catch (Exception e) {

            Do.showShortToast("No se pudo guardar la foto", context);
        }

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

        } catch (Exception e) {
        }

    }
}
