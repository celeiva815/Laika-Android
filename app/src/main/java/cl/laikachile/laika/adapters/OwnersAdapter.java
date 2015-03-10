package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Owner;

public class OwnersAdapter extends ArrayAdapter<Owner> {
	
	private int mIdLayout = R.layout.lk_my_dog_owners_adapter;
	private Context context;
	private List<Owner> owners;

	public OwnersAdapter(Context context, int resource, List<Owner> objects) {
		super(context, resource, objects);
		
		this.context = context;
		this.owners = objects;
	}
	
	@Override
	  public View getView(int position, View view, ViewGroup parent) {
		  
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(mIdLayout, parent, false);
		
	    TextView simpleTextView = (TextView) rowView.findViewById(R.id.name_my_dog_owner_textview);
	    simpleTextView.setText(owners.get(position).getFullName());

	    return rowView;
	    
	  } 
}
