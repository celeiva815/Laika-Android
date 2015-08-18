package social.laika.app.utils;

/**
 * Created by Tito_Leiva on 09-03-15.
 */
public class Tag {

    //Model
    public final static int FLAG_READED = 0;
    public final static int FLAG_CREATED = 1;
    public final static int FLAG_UPDATED = 2;
    public final static int FLAG_DELETED = 3;

    //Dog
    public static final int DOG_FOUNDATION = 1;
    public static final int DOG_OWNED = 2;
    public static final int DOG_LOST = 3;
    public static final int DOG_DEAD = 4;
    public static final int DOG_POSTULATED = 5;
    public static final int DOG_DELETED = 6;

    //Owners
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_BOTH = 3;

    //OwnerDog
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_EDITOR = 2;

    //UserAdoptDog
    public static final int POSTULATION_WAITING = 1;
    public static final int POSTULATION_DISABLED = 2;
    public static final int POSTULATION_ACCEPTED = 3;
    public static final int POSTULATION_REFUSED = 4;
    public static final int POSTULATION_REVOKED = 5;
    public static final int POSTULATION_ADOPTED = 6;


    //Reminders
    public static final int CATEGORY_FOOD = 1;
    public static final int CATEGORY_POO = 2;
    public static final int CATEGORY_WALK = 3;
    public static final int CATEGORY_MEDICINE = 4;
    public static final int CATEGORY_VACCINE = 5;
    public static final int CATEGORY_HYGIENE = 6;
    public static final int CATEGORY_VET = 7;
    public static final int STATUS_NOT_ACTIVATED = 0;
    public static final int STATUS_ACTIVATED = 1;
    public static final int TYPE_CALENDAR = 1;
    public static final int TYPE_ALARM = 2;

    //Events
    public static final int ASSISTANCE_ACCEPT = 1;
    public static final int ASSISTANCE_REFUSE = 2;
    public static final int ASSISTANCE_NOT_ANSWERED = 3;

    //Picker
    public static final String TIME_PICKER = "time_picker";
    public static final String DATE_PICKER = "date_picker";


    //Active Regions
    public static final String REGION_METROPOLITANA = "Región Metropolitana de Santiago";
    public static final String REGION_VALPARAISO = "Valparaíso";

    //Tip
    public static final int TIP_FOOD = 1;
    public static final int TIP_HEALTH = 2;
    public static final int TIP_HYGIENE = 3;
    public static final int TIP_WALK = 4;

    //Size
    public static final int SIZE_SMALLER = 1;
    public static final int SIZE_SMALL = 2;
    public static final int SIZE_MIDDLE = 3;
    public static final int SIZE_BIG = 4;
    public static final int SIZE_BIGGER = 5;

    //Personalities
    public static final int PERSONALITY_OTHER = 0;
    public static final int PERSONALITY_SHY = 1;
    public static final int PERSONALITY_SOCIAL = 2;
    public static final int PERSONALITY_PLAYER = 3;
    public static final int PERSONALITY_KEEPER = 4;

    //Home Space
    public static final int SPACE_APARTMENT_SMALL= 1;
    public static final int SPACE_APARTMENT_MIDDLE= 2;
    public static final int SPACE_APARTMENT_BIG= 3;
    public static final int SPACE_HOUSE_SMALL= 4;
    public static final int SPACE_HOUSE_MIDDLE= 5;
    public static final int SPACE_HOUSE_BIG= 6;
    public static final int SPACE_PLOT_OPEN= 7;
    public static final int SPACE_PLOT_CLOSE= 8;

    //Free-Time
    public static final int TIME_ZERO= 0;
    public static final int TIME_MIN= 1;
    public static final int TIME_NORMAL= 2;
    public static final int TIME_GREAT= 3;

    //MISC
    public static final int NONE = 0;
    public static final int LIMIT = 5;

    //Image Size
    public static final String IMAGE_ORIGINAL = "original";
    public static final String IMAGE_LARGE = "large";
    public static final String IMAGE_MEDIUM = "medium";
    public static final String IMAGE_MEDIUM_S = "medium_s";
    public static final String IMAGE_THUMB = "thumb";

}
