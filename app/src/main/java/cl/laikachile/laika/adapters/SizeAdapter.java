package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Size;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
public class SizeAdapter extends BaseAdapter {

    public List<Size> mSizes;
    public Context mContext;
    public int mIdLayout;
    public int mIdTextview;

    public SizeAdapter(Context context, int resource, int textViewResourceId, List<Size> objects) {

        mContext = context;
        mIdLayout = resource;
        mIdTextview = textViewResourceId;
        mSizes = objects;

    }

    public int getPosition(Size item) {
        return mSizes.indexOf(item);
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
        return mSizes.get(position).mSizeId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = (TextView) View.inflate(mContext, android.R.layout.simple_spinner_item, null);
        textView.setText(mSizes.get(position).mName);
        textView.setTextColor(mContext.getResources().getColor(R.color.light_black_font));
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
