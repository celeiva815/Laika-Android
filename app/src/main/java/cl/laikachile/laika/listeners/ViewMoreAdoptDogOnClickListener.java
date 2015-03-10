package cl.laikachile.laika.listeners;

import cl.laikachile.laika.R;
import cl.laikachile.laika.models.Dog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ViewMoreAdoptDogOnClickListener implements OnClickListener {
	
	private int mIdLayout = R.layout.ai_scrollable_textview_for_dialog;
	private final Dog dog;
	

	public ViewMoreAdoptDogOnClickListener(Dog dog) {

		this.dog = dog;
	}

	@Override
	public void onClick(View v) {
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
		final Context context = v.getContext();
		dialog.setView(getView(context));
		dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.dismiss();
				
			}
		});
		
		dialog.setTitle(dog.mName);
		dialog.show();
	}
	
	private View getView(Context context) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(mIdLayout, null, false);
		
		TextView simpleTextView = (TextView) view.findViewById(R.id.scrollable_textview);
		simpleTextView.setText(dog.getaStory());
		
		return view;	
		
	}

}
