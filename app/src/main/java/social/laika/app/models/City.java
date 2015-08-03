package social.laika.app.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import social.laika.app.utils.DB;

/**
 * Created by Tito_Leiva on 23-03-15.
 */
@Table(name = City.TABLE_CITIES)
public class City extends Model {

    public final static String TABLE_CITIES = "cities";
    public final static String COLUMN_REGION_ID = "region_id";
    public final static String COLUMN_CITY_ID = "city_id";
    public final static String COLUMN_CITY_NAME = "name";
    public final static String COLUMN_IS_ACTIVE = "is_active";

    @Column(name = COLUMN_REGION_ID)
    public int mRegionId;

    @Column(name = COLUMN_CITY_ID)
    public int mCityId;

    @Column(name = COLUMN_CITY_NAME)
    public String mCityName;

    @Column(name = COLUMN_IS_ACTIVE)
    public boolean mIsActive;

    public City() { }

    public City(int mRegionId, int mCityId, String mCityName) {

        this.mRegionId = mRegionId;
        this.mCityId = mCityId;
        this.mCityName = mCityName;
    }

    public City(JSONObject jsonObject) {

        this.mRegionId = jsonObject.optInt(COLUMN_REGION_ID);
        this.mCityId = jsonObject.optInt(COLUMN_CITY_ID);
        this.mCityName = jsonObject.optString(COLUMN_CITY_NAME);

    }

    private void update(City city) {

        this.mRegionId = city.mRegionId;
        this.mCityId = city.mCityId;
        this.mCityName = city.mCityName;

        this.save();
    }

    public Country getCountry() {

        return getRegion().getCountry();
    }

    public Region getRegion() {

        return Region.getSingleRegion(mRegionId);
    }

    //DATABASE

    public static void saveCities(JSONObject jsonObject) {

        if (jsonObject.has(TABLE_CITIES)) {
            try {
                JSONArray jsonCities = jsonObject.getJSONArray(TABLE_CITIES);

                for (int i = 0; i < jsonCities.length(); i++) {
                    saveCity(jsonCities.getJSONObject(i));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static City saveCity(JSONObject jsonObject) {

        City city = new City(jsonObject);
        return createOrUpdate(city);

    }

    public static City createOrUpdate(City city) {

        if (!isSaved(city)) {
            city.save();
            return city;

        } else {
            City oldCity = getSingleLocation(city.mCityId);
            oldCity.update(city);
            return oldCity;
        }
    }

    public static boolean isSaved(City city) {

        String condition = COLUMN_CITY_ID + DB.EQUALS + city.mCityId;
        return new Select().from(City.class).where(condition).exists();

    }

    public static City getSingleLocation(int cityId) {

        String condition = COLUMN_CITY_ID + DB.EQUALS + cityId;
        return new Select().from(City.class).where(condition).executeSingle();

    }

    public static City getSingleCity(int cityId) {

        String condition = COLUMN_CITY_ID + DB.EQUALS + cityId;
        return new Select().from(City.class).where(condition).executeSingle();

    }

    public static List<City> getCitiesByCountries(int countryId) {

        List<City> cities = new ArrayList<>();
        List<Region> regions = Region.getRegions(countryId);

        for (Region region : regions) {

            cities.addAll(getCitiesByRegion(region.mRegionId));
        }

        return cities;

    }

    public static List<City> getCitiesByRegion(int regionId) {

        String condition = COLUMN_REGION_ID + DB.EQUALS + regionId;
        String order = COLUMN_CITY_NAME + DB.ASC;
        return new Select().from(City.class).where(condition).orderBy(order).execute();

    }

    public static void deleteAll() {

        new Delete().from(City.class).execute();

    }

    public static void deleteCity(City city) {

        String condition = COLUMN_CITY_ID + DB.EQUALS + city.mCityId;
        new Delete().from(City.class).where(condition).execute();

    }

    public static List<City> getAll() {

        return new Select().from(City.class).execute();
    }
}

