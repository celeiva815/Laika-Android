package cl.laikachile.laika.models.indexes;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
public class Space {

    public int mIndex;
    public String mName;

    public Space(int mIndex, String mName) {
        this.mIndex = mIndex;
        this.mName = mName;
    }

    public static List<Space> getSpaces(Context context) {

        List<Space> spaces = new ArrayList<>(5);
        String[] names = context.getResources().getStringArray(R.array.home_size_adopt);

        Space apartmentSmall = new Space(Tag.SPACE_APARTMENT_SMALL, names[Tag.SPACE_APARTMENT_SMALL-1]);
        Space apartmentMiddle = new Space(Tag.SPACE_APARTMENT_MIDDLE, names[Tag.SPACE_APARTMENT_MIDDLE-1]);
        Space apartmentBig = new Space(Tag.SPACE_APARTMENT_BIG, names[Tag.SPACE_APARTMENT_BIG-1]);
        Space houseSmall = new Space(Tag.SPACE_HOUSE_SMALL, names[Tag.SPACE_HOUSE_SMALL-1]);
        Space houseMiddle = new Space(Tag.SPACE_HOUSE_MIDDLE, names[Tag.SPACE_HOUSE_MIDDLE-1]);
        Space houseBig = new Space(Tag.SPACE_HOUSE_BIG, names[Tag.SPACE_HOUSE_BIG-1]);
        Space plotOpen = new Space(Tag.SPACE_PLOT_OPEN, names[Tag.SPACE_PLOT_OPEN-1]);
        Space plotClose = new Space(Tag.SPACE_PLOT_CLOSE, names[Tag.SPACE_PLOT_CLOSE-1]);

        spaces.add(apartmentSmall);
        spaces.add(apartmentMiddle);
        spaces.add(apartmentBig);
        spaces.add(houseSmall);
        spaces.add(houseMiddle);
        spaces.add(houseBig);
        spaces.add(plotOpen);
        spaces.add(plotClose);

        return spaces;

    }
}
