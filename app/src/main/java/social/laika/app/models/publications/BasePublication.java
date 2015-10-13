package social.laika.app.models.publications;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import social.laika.app.interfaces.Picturable;
import social.laika.app.utils.Do;
import social.laika.app.utils.Photographer;

public abstract class BasePublication extends Model implements Picturable {

    public final static String COLUMN_SERVER_ID = "server_id" ;
    public final static String COLUMN_URL = "url" ;
    public final static String COLUMN_URL_IMAGE = "url_image";
    public final static String COLUMN_URL_IMAGE_LOCAL = "url_image_local";
    public final static String COLUMN_IS_PAID = "is_paid";
    public final static String COLUMN_IS_FAVORITE = "is_favorite";
    public final static String COLUMN_NOTIFICATOR_ID = "notificator_id";

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

    @Column(name = COLUMN_NOTIFICATOR_ID)
    public long mNotificatorId;

	public BasePublication(){ }

    public void setIsFavorite(boolean isFavorite) {

        mIsFavorite = isFavorite;
        this.save();
    }

    @Override
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

    private void setNotificatorId(long notificatorId) {
        mNotificatorId = notificatorId;
        save();
    }

    public PublicationNotificator getNotificator() {

        if (mNotificatorId < 1) {

            PublicationNotificator notificator = new PublicationNotificator(getModel(), mServerId);

            notificator.save();
            setNotificatorId(notificator.getId());

            return notificator;

        } else {
            return PublicationNotificator.getSingle(mNotificatorId);
        }
    }

    public abstract String getModel();
}
