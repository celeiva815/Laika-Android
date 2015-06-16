package cl.laikachile.laika.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cl.laikachile.laika.utils.DB;

/**
 * Created by Tito_Leiva on 23-03-15.
 */
@Table(name = Location.TABLE_LOCATION)
public class Location extends Model {

    public final static String TABLE_LOCATION = "locations";
    public final static String COLUMN_LOCATION_ID = "location_id";
    public final static String COLUMN_COUNTRY_ID = "country_id";
    public final static String COLUMN_REGION_ID = "region_id";
    public final static String COLUMN_CITY_ID = "city_id";
    public final static String COLUMN_CITY_NAME = "name";
    public final static String COLUMN_IS_ACTIVE = "is_active";

    public final static String API_ID = "id";
    public final static String API_CITIES = "cities";

    @Column(name = COLUMN_LOCATION_ID)
    public int mLocationId;

    @Column(name = COLUMN_COUNTRY_ID)
    public int mCountryId;

    @Column(name = COLUMN_REGION_ID)
    public int mRegionId;

    @Column(name = COLUMN_CITY_ID)
    public int mCityId;

    @Column(name = COLUMN_CITY_NAME)
    public String mCityName;

    @Column(name = COLUMN_IS_ACTIVE)
    public boolean mIsActive;

    public Location() { }

    public Location(int mLocationId, int mCountryId, int mRegionId, int mCityId, String mCityName) {

        this.mLocationId = mLocationId;
        this.mCountryId = mCountryId;
        this.mRegionId = mRegionId;
        this.mCityId = mCityId;
        this.mCityName = mCityName;
    }

    public Location(int mRegionId, int mCityId, String mCityName) {

        this.mRegionId = mRegionId;
        this.mCityId = mCityId;
        this.mCityName = mCityName;
    }

    public Location(JSONObject jsonObject) {

        this.mRegionId = jsonObject.optInt(COLUMN_REGION_ID);
        this.mCityId = jsonObject.optInt(API_ID);
        this.mCityName = jsonObject.optString(COLUMN_CITY_NAME);

    }

    public static void setLocation(JSONObject jsonObject) {

        int cityId = jsonObject.optInt(COLUMN_CITY_ID);
        Location location = getSingleLocationsByCity(cityId);

        if (location != null) {
            location.addLocation(jsonObject);
        }
    }

    public void addLocation(JSONObject jsonObject) {

        this.mLocationId = jsonObject.optInt(COLUMN_LOCATION_ID);
        this.mCountryId = jsonObject.optInt(COLUMN_COUNTRY_ID);
        this.save();

    }

    private void update(Location location) {

        this.mRegionId = location.mRegionId;
        this.mCityId = location.mCityId;
        this.mCityName = location.mCityName;

        this.save();
    }

    public Country getCountry() {

        return Country.getSingleCountry(mCountryId);
    }

    public Region getRegion() {

        return Region.getSingleRegion(mRegionId);
    }

    //DATABASE

    public static void saveCities(JSONObject jsonObject) {

        try {
            JSONArray jsonLocations = jsonObject.getJSONArray(API_CITIES);

            for (int i = 0; i < jsonLocations.length(); i++) {
                saveLocation(jsonLocations.getJSONObject(i));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void setLocations(JSONObject jsonObject) {

        try {
            JSONArray jsonLocations = jsonObject.getJSONArray(TABLE_LOCATION);

            for (int i = 0; i < jsonLocations.length(); i++) {
                setLocation(jsonLocations.getJSONObject(i));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Location saveLocation(JSONObject jsonObject) {

        Location location = new Location(jsonObject);
        return createOrUpdate(location);

    }

    public static Location createOrUpdate(Location location) {

        if (!isSaved(location)) {
            location.save();
            return location;

        } else {
            Location oldLocation = getSingleLocation(location.mCityId);
            oldLocation.update(location);
            return oldLocation;
        }
    }

    public static boolean isSaved(Location location) {

        String condition = COLUMN_CITY_ID + DB.EQUALS + location.mCityId;
        return new Select().from(Location.class).where(condition).exists();

    }

    public static Location getSingleLocation(int locationId) {

        String condition = COLUMN_LOCATION_ID + DB.EQUALS + locationId;
        return new Select().from(Location.class).where(condition).executeSingle();

    }

    public static Location getSingleLocationsByCity(int cityId) {

        String condition = COLUMN_CITY_ID + DB.EQUALS + cityId;
        return new Select().from(Location.class).where(condition).executeSingle();

    }

    public static List<Location> getLocationsByCountries(int countryId) {

        String condition = COLUMN_COUNTRY_ID + DB.EQUALS + countryId;
        return new Select().from(Location.class).where(condition).execute();

    }

    public static List<Location> getLocationsByRegions(int regionId) {

        String condition = COLUMN_REGION_ID + DB.EQUALS + regionId;
        return new Select().from(Location.class).where(condition).execute();

    }

    public static void deleteAll() {

        new Delete().from(Location.class).execute();

    }

    public static void deleteLocation(Location location) {

        String condition = COLUMN_LOCATION_ID + DB.EQUALS + location.mLocationId;
        new Delete().from(Location.class).where(condition).execute();

    }


}

