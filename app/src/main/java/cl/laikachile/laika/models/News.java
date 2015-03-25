package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = News.TABLE_NAME)
public class News extends Model {

	public final static String TABLE_NAME = "news";
    public final static String COLUMN_NEWS_ID = "news_id";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_SPONSOR = "sponsor";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_BODY = "body";
    public final static String COLUMN_URL_NEWS = "url_news";
    public final static String COLUMN_URL_IMAGE = "url_image";


    @Column(name = COLUMN_TITLE)
    public String mTitle;

    @Column(name = COLUMN_SPONSOR)
    public String mSponsor;

    @Column(name = COLUMN_DATE)
    public String mDate;

	@Column(name = COLUMN_BODY)
	public String mBody;

	@Column(name = COLUMN_URL_IMAGE)
	public int mImage; //FIXME

    @Column(name = COLUMN_URL_NEWS)
    public String mUrlNews;
	
	@Column(name = COLUMN_NEWS_ID)
	public int mNewsId;



	public News(){ }

    public News(String mTitle, String mSponsor, String mDate, String mBody, int mImage, int mNewsId,
                String mUrlNews) {

        this.mTitle = mTitle;
        this.mSponsor = mSponsor;
        this.mDate = mDate;
        this.mBody = mBody;
        this.mImage = mImage;
        this.mUrlNews = mUrlNews;
        this.mNewsId = mNewsId;

    }

    public String getSponsor() {

        //TODO si es que se agregan nuevos idiomas esto tendría que pasar a los strings
        return "por " + mSponsor;

    }
}
