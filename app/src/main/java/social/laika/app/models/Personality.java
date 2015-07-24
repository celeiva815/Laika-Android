package social.laika.app.models;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

import social.laika.app.R;
import social.laika.app.utils.DB;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
@Table(name = Personality.TABLE_PERSONALITY)
public class Personality extends Model {

    public final static String TABLE_PERSONALITY = "personality";
    public final static String COLUMN_PERSONALITY_ID = "personality_id";
    public final static String COLUMN_NAME = "name";

    @Column(name = COLUMN_PERSONALITY_ID)
    public int mPersonalityId;

    @Column(name = COLUMN_NAME)
    public String mName;

    public Personality() { }

    public Personality(int mPersonalityId, String mName) {
        this.mPersonalityId = mPersonalityId;
        this.mName = mName;
    }

    public static void setPersonalities(Context context) {

        if (getPersonalities().isEmpty()) {

            String[] names = context.getResources().getStringArray(R.array.personality_adopt);

            Personality other = new Personality(Tag.PERSONALITY_OTHER, names[Tag.PERSONALITY_OTHER]);
            Personality shy = new Personality(Tag.PERSONALITY_SHY, names[Tag.PERSONALITY_SHY]);
            Personality player = new Personality(Tag.PERSONALITY_PLAYER, names[Tag.PERSONALITY_PLAYER]);
            Personality social = new Personality(Tag.PERSONALITY_SOCIAL, names[Tag.PERSONALITY_SOCIAL]);
            Personality keeper = new Personality(Tag.PERSONALITY_KEEPER, names[Tag.PERSONALITY_KEEPER]);

            shy.save();
            player.save();
            social.save();
            keeper.save();
            other.save();
        }
    }

    public static List<Personality> getPersonalities() {

        return new Select().from(Personality.class).execute();
    }

    public static Personality getSinglePersonality(int personalityId) {

        String condition = COLUMN_PERSONALITY_ID + DB.EQUALS + personalityId;
        return new Select().from(Personality.class).where(condition).executeSingle();
    }
}