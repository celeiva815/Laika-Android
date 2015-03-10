package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = News.TABLE_NAME)
public class News extends Model {

	public final static String TABLE_NAME = "news";
	public final static String COLUMN_TITLE = "title";
	public final static String COLUMN_BODY = "body";
	public final static String COLUMN_IMAGE = "image";
	public final static String COLUMN_NEWS_ID = "news_id";
	
	
	@Column(name = COLUMN_BODY)
	public String aBody;
	
	@Column(name = COLUMN_TITLE)
	public String aTitle;
	
	@Column(name = COLUMN_IMAGE)
	public int aImage;
	
	@Column(name = COLUMN_NEWS_ID)
	public int aNewsId;

	public News(String aBody, String aTitle, int aImage, int aNewsId) {
	
		this.aBody = aBody;
		this.aTitle = aTitle;
		this.aImage = aImage;
		this.aNewsId = aNewsId;
	}
	
	public News(){}
	
}
