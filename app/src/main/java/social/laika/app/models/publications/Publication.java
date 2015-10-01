package social.laika.app.models.publications;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import social.laika.app.utils.DB;

@Table(name = Publication.TABLE_NAME)
public class Publication extends BasePublication {

	public final static String TABLE_NAME = "publications";
    public final static String COLUMN_SPONSOR_ID = "sponsor_id";
    public final static String COLUMN_SPONSOR_NAME = "sponsor_name";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_BODY = "body";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_TIME = "time";

    public final static String API_PUBLICATION_ID = "publication_id";
    public final static String API_PUBLICATIONS = "publications";
    public final static String API_URL_PUBLICATION = "url_publication" ;
    public final static String API_LAST_PUBLICATION_ID = "last_publication_id";
    public final static String API_LIMIT = "limit";


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


	public Publication() { }

    public Publication(int mPublicationId, int mSponsorId, String mSponsor, String mTitle,
                       String mDate, String mBody, String mUrlImage, String mUrlPublication,
                       boolean mIsPaid, boolean mIsFavorite) {

        this.mServerId = mPublicationId;
        this.mSponsorId = mSponsorId;
        this.mSponsor = mSponsor;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mBody = mBody;
        this.mUrlImage = mUrlImage;
        this.mUrl = mUrlPublication;
        this.mIsPaid = mIsPaid;
        this.mIsFavorite = mIsFavorite;
    }

    public Publication(JSONObject jsonObject) {

        try {

            this.mServerId = jsonObject.getInt(API_PUBLICATION_ID);
            this.mSponsorId = jsonObject.getInt(COLUMN_SPONSOR_ID);
            this.mSponsor = jsonObject.getString(COLUMN_SPONSOR_NAME);
            this.mTitle = jsonObject.getString(COLUMN_TITLE);
            this.mDate = jsonObject.getString(COLUMN_DATE);
            this.mBody = jsonObject.getString(COLUMN_BODY);
            this.mUrlImage = jsonObject.getString(COLUMN_URL_IMAGE);
            this.mUrl = jsonObject.getString(API_URL_PUBLICATION);
            this.mIsPaid = jsonObject.getBoolean(COLUMN_IS_PAID);
            this.mIsFavorite = false;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getSponsor() {

        //MAYBE si es que se agregan nuevos idiomas esto tendría que pasar a los strings
        return "por " + mSponsor;

    }

    public void update(Publication publication) {

        this.mServerId = publication.mServerId;
        this.mSponsorId = publication.mSponsorId;
        this.mSponsor = publication.mSponsor;
        this.mTitle = publication.mTitle;
        this.mDate = publication.mDate;
        this.mBody = publication.mBody;
        this.mUrlImage = publication.mUrlImage;
        this.mUrl = publication.mUrl;
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

        String condition = COLUMN_SERVER_ID + DB.EQUALS + publication.mServerId;

        return new Select().from(Publication.class).where(condition).executeSingle();

    }

    public static boolean isSaved(Publication publication) {

        String condition = COLUMN_SERVER_ID + DB.EQUALS + publication.mServerId;

        return new Select().from(Publication.class).where(condition).exists();

    }
    

    public static List<Publication> getPublications() {

        String order = COLUMN_SERVER_ID + DB.DESC;
        return new Select().from(Publication.class).orderBy(order).execute();

    }

    public static List<Publication> getFavorites() {

        String condition = COLUMN_IS_FAVORITE + DB.EQUALS + DB.TRUE;
        return new Select().from(Publication.class).where(condition).execute();

    }

    public static void deleteAll() {

        String condition = COLUMN_IS_FAVORITE + DB.EQUALS + DB.FALSE;
        new Delete().from(Publication.class).where(condition).execute();
    }
}
