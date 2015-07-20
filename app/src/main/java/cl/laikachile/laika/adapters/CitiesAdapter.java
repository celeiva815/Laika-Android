package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.City;

public class CitiesAdapter extends BaseAdapter {

	public List<City> mCities;
	public Context mContext;
	public int mIdLayout;
	public int mIdTextview;

	public CitiesAdapter(Context context, int resource, int textViewResourceId, List objects) {

		mContext = context;
		mIdLayout = resource;
		mIdTextview = textViewResourceId;
		mCities = objects;

	}

	public int getPosition(City city) {

		return mCities.indexOf(city);
	}

	@Override
	public int getCount() {
		return mCities.size();
	}

	@Override
	public Object getItem(int position) {
		return mCities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mCities.get(position).mCityId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView textView = (TextView) View.inflate(mContext, android.R.layout.simple_spinner_item, null);
		textView.setText(mCities.get(position).mCityName);
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

		((TextView) convertView).setText(mCities.get(position).mCityName);
		return convertView;


	}
}
