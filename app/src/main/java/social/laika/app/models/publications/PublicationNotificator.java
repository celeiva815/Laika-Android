package social.laika.app.models.publications;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Tito_Leiva on 13-10-15.
 */
@Table(name = PublicationNotificator.TABLE_NAME)
public class PublicationNotificator extends Model {

    public static final String TABLE_NAME = "publication_notificator";
    public static final String API_MODEL = "model";
    public static final String API_MODEL_ID = "model_id";
    public static final String API_VIEWS = "vistas";
    public static final String API_CLICKS = "clicks";
    public static final String API_FAVORITES = "favorito";
    public static final String API_SHARES = "share";
    public static final String COLUMN_NEED_SYNC = "need_sync";


    @Column(name = API_MODEL)
    public String mModel;

    @Column(name = API_MODEL_ID)
    public int mModelId;

    @Column(name = API_VIEWS)
    public int mViews;

    @Column(name = API_CLICKS)
    public int mClicks;

    @Column(name = API_FAVORITES)
    public int mFavorites;

    @Column(name = API_SHARES)
    public int mShare;

    @Column(name = COLUMN_NEED_SYNC)
    public boolean mNeedSync;


    public PublicationNotificator(String model, int modelId) {
        mModel = model;
        mModelId = modelId;
        mViews = 0;
        mClicks = 0;
        mFavorites = 0;
        mShare = 0;
        mNeedSync = false;
    }

    public void addClick(){

        mClicks++;
        mNeedSync = true;
        save();
    }

    public void setFavorite(boolean isFavorite){

        mFavorites = isFavorite ? 1 : 0;
        mNeedSync = true;
        save();
    }

    public void addView(){

        mViews++;
        mNeedSync = true;
        save();
    }

    public void addShare(){

        mShare++;
        mNeedSync = true;
        save();
    }

    public static PublicationNotificator getSingle(long id) {

        return Model.load(PublicationNotificator.class, id);
    }

    public void clear() {

        mViews = 0;
        mClicks = 0;
        mShare = 0;
        mFavorites = 0;
        mNeedSync = false;

        save();
    }
}
