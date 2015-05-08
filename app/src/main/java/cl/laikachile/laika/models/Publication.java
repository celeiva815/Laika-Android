package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.utils.DB;

@Table(name = Publication.TABLE_NAME)
public class Publication extends Model {

	public final static String TABLE_NAME = "publications";
    public final static String COLUMN_PUBLICATION_ID = "publication_id";
    public final static String COLUMN_SPONSOR_ID = "sponsor_id";
    public final static String COLUMN_SPONSOR_NAME = "sponsor_name";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_BODY = "body";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_URL_PUBLICATION = "url_publication" ;
    public final static String COLUMN_URL_IMAGE = "url_image";
    public final static String COLUMN_IS_PAID = "is_paid";
    public final static String COLUMN_IS_FAVORITE = "is_favorite";

    public final static String API_PUBLICATIONS = "publications";
    public final static String API_LAST_PUBLICATION_ID = "last_publication_id";
    public final static String API_LIMIT = "limit";


    @Column(name = COLUMN_PUBLICATION_ID)
    public int mPublicationId;

    @Column(name = COLUMN_SPONSOR_ID)
    public int mSponsorId;

    @Column(name = COLUMN_SPONSOR_NAME)
    public String mSponsor;

    @Column(name = COLUMN_TITLE)
    public String mTitle;

    @Column(name = COLUMN_DATE)
    public String mDate;

	@Column(name = COLUMN_BODY)
	public String mBody;

	@Column(name = COLUMN_URL_IMAGE)
	public int mImage; //FIXME

    @Column(name = COLUMN_URL_PUBLICATION)
    public String mUrlPublication;

    @Column(name = COLUMN_IS_PAID)
    public boolean mIsPaid;

    @Column(name = COLUMN_IS_FAVORITE)
    public boolean mIsFavorite;



	public Publication(){ }

    public Publication(int mPublicationId, int mSponsorId, String mSponsor, String mTitle,
                       String mDate, String mBody, int mImage, String mUrlPublication,
                       boolean mIsPaid, boolean mIsFavorite) {

        this.mPublicationId = mPublicationId;
        this.mSponsorId = mSponsorId;
        this.mSponsor = mSponsor;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mBody = mBody;
        this.mImage = mImage;
        this.mUrlPublication = mUrlPublication;
        this.mIsPaid = mIsPaid;
        this.mIsFavorite = mIsFavorite;
    }

    public Publication(JSONObject jsonObject) {

        try {

            this.mPublicationId = jsonObject.getInt(COLUMN_PUBLICATION_ID);
            this.mSponsorId = jsonObject.getInt(COLUMN_SPONSOR_ID);
            this.mSponsor = jsonObject.getString(COLUMN_SPONSOR_NAME);
            this.mTitle = jsonObject.getString(COLUMN_TITLE);
            this.mDate = jsonObject.getString(COLUMN_DATE);
            this.mBody = jsonObject.getString(COLUMN_BODY);
            this.mImage = R.drawable.lk_news_picture1;//FIXME jsonObject.getString(COLUMN_URL_IMAGE);
            this.mUrlPublication = jsonObject.getString(COLUMN_URL_PUBLICATION);
            this.mIsPaid = jsonObject.getBoolean(COLUMN_IS_PAID);
            this.mIsFavorite = false;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getSponsor() {

        //TODO si es que se agregan nuevos idiomas esto tendría que pasar a los strings
        return "por " + mSponsor;

    }

    public void setIsFavorite(boolean isFavorite) {

        mIsFavorite = isFavorite;
        this.save();
    }

    public void update(Publication publication) {

        this.mPublicationId = publication.mPublicationId;
        this.mSponsorId = publication.mSponsorId;
        this.mSponsor = publication.mSponsor;
        this.mTitle = publication.mTitle;
        this.mDate = publication.mDate;
        this.mBody = publication.mBody;
        this.mImage = publication.mImage;
        this.mUrlPublication = publication.mUrlPublication;
        this.mIsPaid = publication.mIsPaid;
        this.mIsFavorite = publication.mIsFavorite;

        this.save();
    }

    
    // Database 

    public static void savePublications(JSONObject jsonObject) {

        try {

            JSONArray jsonPublications = jsonObject.getJSONArray(API_PUBLICATIONS);

            for (int i = 0; i < jsonPublications.length(); i++) {

                JSONObject jsonPublication = jsonPublications.getJSONObject(i);
                Publication publication = new Publication(jsonPublication);

                createOrUpdate(publication);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void createOrUpdate(Publication publication) {

        if (!Publication.isSaved(publication)) {
            publication.save();

        } else {
            Publication oldPublication = getSinglePublication(publication);
            oldPublication.update(publication);

        }
    }

    public static Publication getSinglePublication(Publication publication) {

        String condition = COLUMN_PUBLICATION_ID + DB.EQUALS + publication.mPublicationId;

        return new Select().from(Publication.class).where(condition).executeSingle();

    }

    public static boolean isSaved(Publication publication) {

        String condition = COLUMN_PUBLICATION_ID + DB.EQUALS + publication.mPublicationId;

        return new Select().from(Publication.class).where(condition).exists();

    }
    

    public static List<Publication> getPublications() {

        return new Select().from(Publication.class).execute();

    }

    public static List<Publication> getFavorites() {

        String condition = COLUMN_IS_FAVORITE + DB.EQUALS + DB.TRUE;
        return new Select().from(Publication.class).where(condition).execute();

    }

}
