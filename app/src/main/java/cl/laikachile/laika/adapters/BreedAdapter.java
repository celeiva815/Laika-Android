package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.models.Breed;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
public class BreedAdapter extends BaseAdapter {

    public List<Breed> mBreeds;
    public Context mContext;
    public int mIdLayout;
    public int mIdTextview;

    public BreedAdapter(Context context, int resource, int textViewResourceId, List<Breed> objects) {

        mContext = context;
        mIdLayout = resource;
        mIdTextview = textViewResourceId;
        mBreeds = objects;

    }

    public int getPosition(Breed item) {
        return mBreeds.indexOf(item);
    }

    @Override
    public int getCount() {
        return mBreeds.size();
    }

    @Override
    public Object getItem(int position) {
        return mBreeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mBreeds.get(position).mBreedId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = (TextView) View.inflate(mContext, android.R.layout.simple_spinner_item, null);
        textView.setText(mBreeds.get(position).mName);
        return textView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mIdLayout, null);
        }

        ((TextView) convertView).setText(mBreeds.get(position).mName);
        return convertView;
    }



}
