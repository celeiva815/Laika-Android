package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = Story.TABLE_NAME)
public class Story extends Model {

	public final static String TABLE_NAME = "stories";
	public final static String COLUMN_BODY = "body";
	public final static String COLUMN_DOG = "dog";
	public final static String COLUMN_USER_ID = "user_id";
	
	@Column(name = COLUMN_BODY)
	public String aBody;
	
	@Column(name = COLUMN_DOG)
	public String aDog;
	
	@Column(name = COLUMN_USER_ID)
	public String aUserId;

	public Story(String aBody, String aDog, String aUserId) {
	
		this.aBody = aBody;
		this.aDog = aDog;
		this.aUserId = aUserId;
	}
	
	public Story(){}
	
}
