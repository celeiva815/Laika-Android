package cl.laikachile.laika.models;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.utils.DB;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 23-03-15.
 */
@Table(name = Zone.TABLE_ZONE)
public class Zone extends Model {

    public final static String TABLE_ZONE = "zone";
    public final static String COLUMN_COUNTRY = "country";
    public final static String COLUMN_STATE = "state";
    public final static String COLUMN_CITY = "city";
    public final static String COLUMN_IS_ACTIVE = "is_active";

    @Column(name = COLUMN_COUNTRY)
    public String mCountry;

    @Column(name = COLUMN_STATE)
    public String mRegion;

    @Column(name = COLUMN_CITY)
    public String mCity;

    @Column(name = COLUMN_IS_ACTIVE)
    public boolean mIsActive;

    public Zone() { }

    public Zone(String mCountry, String mRegion, String mCity, boolean mIsActive) {
        this.mCountry = mCountry;
        this.mRegion = mRegion;
        this.mCity = mCity;
        this.mIsActive = mIsActive;
    }

    public static void generateAvailableZones(Context context) {

        //TODO arreglar esto y ver si se hace por API o en cada actualización de la aplicación

        String country = Do.getRString(context, R.string.chilean_country);
        String[] regions = context.getResources().getStringArray(R.array.available_chilean_regions);

        for (String region : regions) {

            if (region.equals(Tag.REGION_METROPOLITANA)) {

                String[] cities = context.getResources().getStringArray(R.array.metropolitana_cities);

                for (String city : cities) {

                    Zone zone = new Zone(country, region, city, true);
                    zone.save();
                }
            }

            if (region.equals(Tag.REGION_VALPARAISO)) {

                String[] cities = context.getResources().getStringArray(R.array.valparaiso_cities);

                for (String city : cities) {

                    Zone zone = new Zone(country, region, city, true);
                    zone.save();
                }
            }
        }
    }

    public static List<Zone> getZones(String region) {

        String clause = COLUMN_STATE + DB._EQUALS_QUESTION;
        List<Zone> zones = new Select().from(Zone.class).where(clause, region).execute();

        return zones;

    }
}

