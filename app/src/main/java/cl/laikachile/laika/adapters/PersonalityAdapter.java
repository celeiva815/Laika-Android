package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Personality;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
public class PersonalityAdapter extends BaseAdapter {

    public List<Personality> mPersonalities;
    public Context mContext;
    public int mIdLayout;
    public int mIdTextview;

    public PersonalityAdapter(Context context, int resource, int textViewResourceId, List<Personality> objects) {

        mContext = context;
        mIdLayout = resource;
        mIdTextview = textViewResourceId;
        mPersonalities = objects;

    }

    public int getPosition(Personality item) {

        return mPersonalities.indexOf(item);
    }

    @Override
    public int getCount() {
        return mPersonalities.size();
    }

    @Override
    public Object getItem(int position) {
        return mPersonalities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mPersonalities.get(position).mPersonalityId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = (TextView) View.inflate(mContext, android.R.layout.simple_spinner_item, null);
        textView.setText(mPersonalities.get(position).mName);
        textView.setTextColor(mContext.getResources().getColor(R.color.light_black_font));
        return textView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mIdLayout, null);
        }

        ((TextView) convertView).setText(mPersonalities.get(position).mName);
        return convertView;


    }

}
