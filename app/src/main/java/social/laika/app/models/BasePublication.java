package social.laika.app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import social.laika.app.utils.DB;

public abstract class BasePublication extends Model {

    public final static String COLUMN_URL = "url" ;
    public final static String COLUMN_URL_IMAGE = "url_image";
    public final static String COLUMN_URL_IMAGE_LOCAL = "url_image_local";
    public final static String COLUMN_IS_PAID = "is_paid";
    public final static String COLUMN_IS_FAVORITE = "is_favorite";

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

    public void setUriLocal(String uriLocal) {
        mUriLocal = uriLocal;
        save();
    }
}
