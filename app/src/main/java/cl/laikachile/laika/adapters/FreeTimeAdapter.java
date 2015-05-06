package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.models.indexes.FreeTime;
import cl.laikachile.laika.models.indexes.Size;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
public class FreeTimeAdapter extends BaseAdapter {

    public List<FreeTime> mSizes;
    public Context mContext;
    public int mIdLayout;
    public int mIdTextview;

    public FreeTimeAdapter(Context context, int resource, int textViewResourceId, List objects) {

        mContext = context;
        mIdLayout = resource;
        mIdTextview = textViewResourceId;
        mSizes = objects;

    }

    @Override
    public int getCount() {
        return mSizes.size();
    }

    @Override
    public Object getItem(int position) {
        return mSizes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mSizes.get(position).mIndex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = (TextView) View.inflate(mContext, android.R.layout.simple_spinner_item, null);
        textView.setText(mSizes.get(position).mName);
        return textView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mIdLayout, null);
        }

        ((TextView) convertView).setText(mSizes.get(position).mName);
        return convertView;


    }

}
