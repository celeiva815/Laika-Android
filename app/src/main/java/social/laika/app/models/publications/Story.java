package social.laika.app.models.publications;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import social.laika.app.utils.DB;

@Table(name = Story.TABLE_NAME)
public class Story extends BasePublication {

    public static int ID = 1;

    public final static String TABLE_NAME = "stories";
    public final static String COLUMN_OWNER_NAME = "owner_name";
    public final static String COLUMN_USER_ID = "user_id";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_BODY = "body";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_TIME = "time";

    public final static String API_STORY_ID = "story_id";
    public final static String API_STORIES = "stories";
    public final static String API_ID = "id";
    public final static String API_LAST_STORY_ID = "last_story_id";
    public final static String API_LIMIT = "limit";

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

    public Story() {
    }

    public Story(int mStoryId, String mTitle, int mUserId, String mOwnerName, String mDate,
                 String mTime, String mBody, String mUrlImage) {
        this.mServerId = mStoryId;
        this.mTitle = mTitle;
        this.mUserId = mUserId;
        this.mOwnerName = mOwnerName;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mBody = mBody;
        this.mUrlImage = mUrlImage;
    }

    public Story(String mTitle, String mDate, String mTime, String mBody, String mUrlImage) {

        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mBody = mBody;
        this.mUrlImage = mUrlImage;
    }

    public Story(JSONObject jsonObject) {

        this.mServerId = jsonObject.optInt(API_STORY_ID);
        this.mTitle = jsonObject.optString(COLUMN_TITLE);
        this.mUserId = jsonObject.optInt(COLUMN_USER_ID);
        this.mOwnerName = jsonObject.optString(COLUMN_OWNER_NAME);
        this.mDate = jsonObject.optString(COLUMN_DATE);
        this.mTime = jsonObject.optString(COLUMN_TIME);
        this.mBody = jsonObject.optString(COLUMN_BODY);
        this.mUrlImage = jsonObject.optString(COLUMN_URL_IMAGE, "http://www.uvhs.org/wp-content/uploads/2012/12/2013_08_AdoptionInformation.jpg");
        this.mIsFavorite = false;

    }

    public String getOwnerName() {

        //MAYBE si es que se agregan nuevos idiomas esto tendría que pasar a los strings
        return "por " + mOwnerName;

    }

    public void update(Story story) {

        this.mServerId = story.mServerId;
        this.mTitle = story.mTitle;
        this.mUserId = story.mUserId;
        this.mOwnerName = story.mOwnerName;
        this.mDate = story.mDate;
        this.mTime = story.mTime;
        this.mBody = story.mBody;
        this.mUrlImage = story.mUrlImage;
        this.mIsFavorite = mIsFavorite || story.mIsFavorite;

        this.save();

    }

    // DataBase Methods

    public static List<Story> getStories() {

        String order = COLUMN_SERVER_ID + DB.DESC;
        return new Select().from(Story.class).orderBy(order).execute();

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

    public static Story createOrUpdate(Story story) {

        if (!Story.isSaved(story)) {
            story.save();

            return story;

        } else {
            Story oldStory = getSingleStory(story);
            oldStory.update(story);

            return oldStory;
        }
    }

    public static Story getSingleStory(Story story) {

        String condition = COLUMN_SERVER_ID + DB.EQUALS + story.mServerId;

        return new Select().from(Story.class).where(condition).executeSingle();

    }

    public static Story getSingleStory(int storyId) {

        String condition = COLUMN_SERVER_ID + DB.EQUALS + storyId;

        return new Select().from(Story.class).where(condition).executeSingle();

    }

    public static boolean isSaved(Story story) {

        String condition = COLUMN_SERVER_ID + DB.EQUALS + story.mServerId;

        return new Select().from(Story.class).where(condition).exists();

    }

    public static void deleteAll() {

    }

    public JSONObject getJsonObject() {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(COLUMN_TITLE, mTitle);
            jsonObject.put(COLUMN_BODY, mBody);
            jsonObject.put(COLUMN_DATE, mDate);
            jsonObject.put(COLUMN_TIME, mTime);


        } catch (JSONException e) {

            e.printStackTrace();
        }

        return jsonObject;

    }

    public static List<Story> getFavoriteStories() {

        String condition = COLUMN_IS_FAVORITE + DB.EQUALS + DB.TRUE;
        return new Select().from(Story.class).where(condition).execute();
    }

    @Override
    public String getFacebookContentTitle() {
        return mTitle;
    }

    @Override
    public String getFacebookContentDescription() {
        return mBody;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public String getOtherShareText() {
        return mTitle + "\n\n" + mBody + "\n\n" + mUrl;
    }
}


