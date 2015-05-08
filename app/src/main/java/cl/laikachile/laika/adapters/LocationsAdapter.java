package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Location;

public class LocationsAdapter extends ArrayAdapter<Location> {

	private int mIdLayout = R.layout.ai_simple_textview_for_adapter;
	private Context context;
	public List<Location> mLocations;

	public LocationsAdapter(Context context, int resource, List<Location> objects) {
		super(context, resource, objects);
		
		this.context = context;
		this.mLocations = objects;
	}

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getView(position, convertView, parent);
    }
	
	@Override
	  public View getView(int position, View containerView, ViewGroup parent) {
		  
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view = inflater.inflate(mIdLayout, parent, false);
		TextView cityTextView  = (TextView) view.findViewById(R.id.simple_textview);
        cityTextView.setText(mLocations.get(position).mCity);

	    return view;
	    
	  } 
}
