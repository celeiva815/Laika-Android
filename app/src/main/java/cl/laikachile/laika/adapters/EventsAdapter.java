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
import cl.laikachile.laika.listeners.AcceptAssistanceToEventOnClickListener;
import cl.laikachile.laika.listeners.RefuseAssistanceToEventOnClickListener;
import cl.laikachile.laika.models.Event;

public class EventsAdapter extends ArrayAdapter<Event> {
	
	private int mIdLayout = R.layout.lk_events_adapter;
	private Context context;
	private List<Event> events;

	public EventsAdapter(Context context, int resource, List<Event> objects) {
		super(context, resource, objects);
		
		this.context = context;
		this.events = objects;
	}
	
	@Override
	  public View getView(int position, View view, ViewGroup parent) {
		  
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(mIdLayout, parent, false);
		TextView nameTextView  = (TextView) rowView.findViewById(R.id.name_events_textview);
		TextView startDateTextView  = (TextView) rowView.findViewById(R.id.start_date_events_textview);
		TextView finishDateTextView  = (TextView) rowView.findViewById(R.id.finish_date_events_textview);
		ImageView typeImageView  = (ImageView) rowView.findViewById(R.id.type_events_imageview);
		ImageView acceptImageView  = (ImageView) rowView.findViewById(R.id.accept_assistance_imageview);
		ImageView refuseImageView  = (ImageView) rowView.findViewById(R.id.refuse_assistance_imageview);
		
		AcceptAssistanceToEventOnClickListener acceptListener = new AcceptAssistanceToEventOnClickListener(acceptImageView, refuseImageView);
		RefuseAssistanceToEventOnClickListener refuseListener = new RefuseAssistanceToEventOnClickListener(acceptImageView, refuseImageView);
		
	    nameTextView.setText(events.get(position).aName);
	    startDateTextView.setText(events.get(position).aStartDate);
	    finishDateTextView.setText(events.get(position).aFinishDate);
	    typeImageView.setImageResource(events.get(position).getImageResource());
	    acceptImageView.setOnClickListener(acceptListener);
	    refuseImageView.setOnClickListener(refuseListener);
	    
	    
	    return rowView;
	    
	  } 
}
