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
 * Created by Tito_Leiva on 16-06-15.
 */
@Table(name = Country.TABLE_COUNTRIES)
public class Country extends Model {

    public final static String TABLE_COUNTRIES = "countries";
    public final static String COLUMN_COUNTRY_ID = "country_id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_ISO = "iso";

    @Column(name = COLUMN_COUNTRY_ID)
    public int mCountryId;

    @Column(name = COLUMN_NAME)
    public String mName;

    @Column(name = COLUMN_ISO)
    public String mIso;

    public Country() {
    }

    public Country(int mCountryId, String mName, String mIso) {
        this.mCountryId = mCountryId;
        this.mName = mName;
        this.mIso = mIso;
    }

    public Country(JSONObject jsonObject) {

        this.mCountryId = jsonObject.optInt(COLUMN_COUNTRY_ID);
        this.mName = jsonObject.optString(COLUMN_NAME);
        this.mIso = jsonObject.optString(COLUMN_ISO);

    }


    private void update(Country country) {

        this.mCountryId = country.mCountryId;
        this.mName = country.mName;
        this.mIso = country.mIso;

        this.save();
    }

    //DATABASE

    public static void saveCountries(JSONObject jsonObject) {

        try {
            JSONArray jsonCountries = jsonObject.getJSONArray(TABLE_COUNTRIES);

            for (int i = 0; i < jsonCountries.length(); i++) {
                saveCountry(jsonCountries.getJSONObject(i));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Country saveCountry(JSONObject jsonObject) {

        Country country = new Country(jsonObject);
        return createOrUpdate(country);

    }

    public static Country createOrUpdate(Country country) {

        if (!isSaved(country)) {
            country.save();
            return country;

        } else {
            Country oldCountry = getSingleCountry(country.mCountryId);
            oldCountry.update(country);
            return oldCountry;
        }
    }

    public static boolean isSaved(Country country) {

        String condition = COLUMN_COUNTRY_ID + DB.EQUALS + country.mCountryId;
        return new Select().from(Country.class).where(condition).exists();

    }

    public static Country getSingleCountry(int countryId) {

        String condition = COLUMN_COUNTRY_ID + DB.EQUALS + countryId;
        return new Select().from(Country.class).where(condition).executeSingle();

    }

    public static Country getSingleCountry(String iso) {

        return new Select().from(Country.class).where(COLUMN_ISO + DB._QUESTION, iso).executeSingle();

    }

    public static List<Country> getCountries() {

        return new Select().from(Country.class).execute();

    }

    public static void deleteAll() {

        new Delete().from(Country.class).execute();

    }

    public static void deleteCountry(Country country) {

        String condition = COLUMN_COUNTRY_ID + DB.EQUALS + country.mCountryId;
        new Delete().from(Country.class).where(condition).execute();

    }

    public static boolean existIso(String iso) {

        return new Select().from(Country.class).where(COLUMN_ISO + DB._QUESTION, iso).exists();

    }
}
