package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = Story.TABLE_NAME)
public class Story extends Model {

    public static int ID = 1;

	public final static String TABLE_NAME = "stories";
    public final static String COLUMN_STORY_ID = "story_id";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_OWNER_NAME = "owner_name";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_BODY = "body";
    public final static String COLUMN_URL_IMAGE = "url_image";

    @Column(name = COLUMN_TITLE)
    public String mTitle;

    @Column(name = COLUMN_OWNER_NAME)
    public String mOwnerName;

    @Column(name = COLUMN_DATE)
    public String mDate;

    @Column(name = COLUMN_BODY)
    public String mBody;

    @Column(name = COLUMN_URL_IMAGE)
    public int mImage;

    @Column(name = COLUMN_STORY_ID)
    public int mStoryId;

    public Story() { }

    public Story(String mTitle, String mOwnerName, String mDate, String mBody, int mImage, int mStoryId) {
        this.mTitle = mTitle;
        this.mOwnerName = mOwnerName;
        this.mDate = mDate;
        this.mBody = mBody;
        this.mImage = mImage;
        this.mStoryId = mStoryId;
    }

    public String getOwnerName() {

        //TODO si es que se agregan nuevos idiomas esto tendría que pasar a los strings
        return "por " + mOwnerName;

    }
}


