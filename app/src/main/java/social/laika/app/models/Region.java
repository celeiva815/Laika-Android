package social.laika.app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import social.laika.app.utils.DB;

/**
 * Created by Tito_Leiva on 16-06-15.
 */
@Table(name = Region.TABLE_REGIONS)
public class Region extends Model {

    public final static String TABLE_REGIONS = "regions";
    public final static String COLUMN_COUNTRY_ID = "country_id";
    public final static String COLUMN_REGION_ID = "region_id";
    public final static String COLUMN_NAME = "name";

    @Column(name = COLUMN_COUNTRY_ID)
    public int mCountryId;

    @Column(name = COLUMN_REGION_ID)
    public int mRegionId;

    @Column(name = COLUMN_NAME)
    public String mName;

    public Region() {
    }

    public Region(int mCountryId, int mRegionId, String mName) {
        this.mCountryId = mCountryId;
        this.mRegionId = mRegionId;
        this.mName = mName;
    }

    public Region(JSONObject jsonObject) {

        this.mCountryId =jsonObject.optInt(COLUMN_COUNTRY_ID);
        this.mRegionId = jsonObject.optInt(COLUMN_REGION_ID);
        this.mName = jsonObject.optString(COLUMN_NAME);

    }

    private void update(Region region) {

        this.mCountryId = region.mCountryId;
        this.mRegionId = region.mRegionId;
        this.mName = region.mName;

        this.save();
    }

    public Country getCountry() {

        return Country.getSingleCountry(mCountryId);
    }

    //DATABASE

    public static void saveRegions(JSONObject jsonObject) {

        try {
            JSONArray jsonRegions = jsonObject.getJSONArray(TABLE_REGIONS);

            for (int i = 0; i < jsonRegions.length(); i++) {
                saveRegion(jsonRegions.getJSONObject(i));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Region saveRegion(JSONObject jsonObject) {

        Region region = new Region(jsonObject);
        return createOrUpdate(region);

    }

    public static Region createOrUpdate(Region region) {

        if (!isSaved(region)) {
            region.save();
            return region;

        } else {
            Region oldRegion = getSingleRegion(region.mRegionId);
            oldRegion.update(region);
            return oldRegion;
        }
    }

    public static boolean isSaved(Region region) {

        String condition = COLUMN_REGION_ID + DB.EQUALS + region.mRegionId;
        return new Select().from(Region.class).where(condition).exists();

    }

    public static Region getSingleRegion(int regionId) {

        String condition = COLUMN_REGION_ID + DB.EQUALS + regionId;
        return new Select().from(Region.class).where(condition).executeSingle();

    }

    public static List<Region> getRegions(int countryId) {

        String condition = COLUMN_COUNTRY_ID + DB.EQUALS + countryId;
        return new Select().from(Region.class).where(condition).execute();

    }

    public static void deleteAll() {

        new Delete().from(Region.class).execute();

    }

    public static void deleteRegion(Region region) {

        String condition = COLUMN_REGION_ID + DB.EQUALS + region.mRegionId;
        new Delete().from(Region.class).where(condition).execute();

    }
}
