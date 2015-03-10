package cl.laikachile.laika.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Tip;

public class TipsAdapter extends ArrayAdapter<Tip> {
	
	private int mIdLayout = R.layout.lk_tips_adapter;
	private Context context;
	private List<Tip> tips;

	public TipsAdapter(Context context, int resource, List<Tip> objects) {
		super(context, resource, objects);
		
		this.context = context;
		this.tips = objects;
	}
	
	@Override
	  public View getView(int position, View view, ViewGroup parent) {
		  
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(mIdLayout, parent, false);
		TextView bodyTextView  = (TextView) rowView.findViewById(R.id.content_tips_textview);
		ImageView typeImageView  = (ImageView) rowView.findViewById(R.id.type_tips_imageview);
		
	    bodyTextView.setText(tips.get(position).aBody);
	    typeImageView.setImageResource(tips.get(position).getImageResource());
	    
	    return rowView;
	    
	  } 
}
