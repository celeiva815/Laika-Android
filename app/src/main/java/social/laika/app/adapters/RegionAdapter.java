package social.laika.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import social.laika.app.R;
import social.laika.app.models.Region;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
public class RegionAdapter extends BaseAdapter {

    public List<Region> mRegions;
    public Context mContext;
    public int mIdLayout;
    public int mIdTextview;

    public RegionAdapter(Context context, int resource, int textViewResourceId, List objects) {

        mContext = context;
        mIdLayout = resource;
        mIdTextview = textViewResourceId;
        mRegions = objects;

    }

    public int getPosition(Region region) {

        return mRegions.indexOf(region);
    }

    @Override
    public int getCount() {
        return mRegions.size();
    }

    @Override
    public Object getItem(int position) {
        return mRegions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mRegions.get(position).mRegionId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = (TextView) View.inflate(mContext, android.R.layout.simple_spinner_item, null);
        textView.setText(mRegions.get(position).mName);
        textView.setTextColor(mContext.getResources().getColor(R.color.light_black_font));
        textView.setBackground(mContext.getResources().getDrawable(R.drawable.laikatheme_textfield_default_holo_light));
        return textView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mIdLayout, null);
        }

        ((TextView) convertView).setText(mRegions.get(position).mName);
        return convertView;


    }

}
