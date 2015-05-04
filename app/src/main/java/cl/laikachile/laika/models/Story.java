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

@Table(name = Story.TABLE_NAME)
public class Story extends Model {

    public static int ID = 1;

	public final static String TABLE_NAME = "stories";
    public final static String COLUMN_STORY_ID = "story_id";
    public final static String COLUMN_OWNER_NAME = "owner_name";
    public final static String COLUMN_USER_ID = "user_id";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_BODY = "body";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_URL_IMAGE = "url_image";

    public final static String API_STORIES = "stories";
    public final static String API_ID = "id";
    public final static String API_LAST_STORY_ID = "last_story_id";
    public final static String API_LIMIT = "limit";

    @Column(name = COLUMN_STORY_ID)
    public int mStoryId;

    @Column(name = COLUMN_TITLE)
    public String mTitle;

    @Column(name = COLUMN_USER_ID)
    public int mUserId;

    @Column(name = COLUMN_OWNER_NAME)
    public String mOwnerName;

    @Column(name = COLUMN_DATE)
    public String mDate;

    @Column(name = COLUMN_TIME)
    public String mTime;

    @Column(name = COLUMN_BODY)
    public String mBody;

    @Column(name = COLUMN_URL_IMAGE)
    public int mImage;

    public Story() { }

    public Story(int mStoryId, String mTitle, int mUserId, String mOwnerName, String mDate,
                 String mTime, String mBody, int mImage) {
        this.mStoryId = mStoryId;
        this.mTitle = mTitle;
        this.mUserId = mUserId;
        this.mOwnerName = mOwnerName;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mBody = mBody;
        this.mImage = mImage;
    }

    public Story(JSONObject jsonObject) {

        try {
            this.mStoryId = jsonObject.getInt(COLUMN_STORY_ID);
            this.mTitle = jsonObject.getString(COLUMN_TITLE);
            this.mUserId = jsonObject.getInt(COLUMN_USER_ID);
            this.mOwnerName = jsonObject.getString(COLUMN_OWNER_NAME);
            this.mDate = jsonObject.getString(COLUMN_DATE);
            this.mTime = jsonObject.getString(COLUMN_TIME);
            this.mBody = jsonObject.getString(COLUMN_BODY);
            this.mImage = R.drawable.abuelo; //FIXME jsonObject.getString(COLUMN_URL_IMAGE);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getOwnerName() {

        //TODO si es que se agregan nuevos idiomas esto tendría que pasar a los strings
        return "por " + mOwnerName;

    }

    public void update(Story story) {

        this.mStoryId = story.mStoryId;
        this.mTitle = story.mTitle;
        this.mUserId = story.mUserId;
        this.mOwnerName = story.mOwnerName;
        this.mDate = story.mDate;
        this.mTime = story.mTime;
        this.mBody = story.mBody;
        this.mImage = story.mImage;


        this.save();

    }

    // DataBase Methods

    public static List<Story> getStories() {

        return new Select().from(Story.class).execute();

    }

    public static void saveStories(JSONObject jsonObject) {

        try {

            JSONArray jsonStorys = jsonObject.getJSONArray(API_STORIES);

            for (int i = 0; i < jsonStorys.length(); i++) {

                JSONObject jsonStory = jsonStorys.getJSONObject(i);
                Story story = new Story(jsonStory);

                createOrUpdate(story);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void createOrUpdate(Story story) {

        if (!Story.isSaved(story)) {
            story.save();

        } else {
            Story oldStory = getSingleStory(story);
            oldStory.update(story);

        }
    }

    public static Story getSingleStory(Story story) {

        String condition = COLUMN_STORY_ID + DB._EQUALS_ + story.mStoryId;

        return new Select().from(Story.class).where(condition).executeSingle();

    }

    public static boolean isSaved(Story story) {

        String condition = COLUMN_STORY_ID + DB._EQUALS_ + story.mStoryId;

        return new Select().from(Story.class).where(condition).exists();

    }
}


