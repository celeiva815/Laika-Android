package cl.laikachile.laika.listeners;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.AdoptDogSuccessActivity;
import cl.laikachile.laika.models.Dog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ConfirmAdoptionDialogOnClickListener implements OnClickListener {
	
	private int mIdLayout = R.layout.ai_simple_textview_for_dialog;
	private final Dog dog;
	private final Activity activity;

	public ConfirmAdoptionDialogOnClickListener(Dog dog, Activity activity) {

		this.dog = dog;
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
		final Context context = v.getContext();
		dialog.setView(getView(context));
		dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				// TODO Hacer la l�gica de la API
				
				final ProgressDialog progressDialog = ProgressDialog.show(context, "Mascota Adoptada", "Enviando notificaci�n de adopci�n");
				
				new Handler().postDelayed(new Runnable() {

		            @Override
		            public void run() {
		                
		            	progressDialog.dismiss();
		            	
		            	Intent intent = new Intent(context, AdoptDogSuccessActivity.class);
		        		Bundle b = new Bundle();
		        		dog.save();
		        		b.putLong("DogId", dog.getId()); //Your id
		        		intent.putExtras(b); //Put your id to your next Intent
		        		context.startActivity(intent);
		            	activity.finish();
		            			            	
		            }
		        }, 3000);

				
			}
		});
		dialog.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	private View getView(Context context) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(mIdLayout, null, false);
		
		TextView question = (TextView) view.findViewById(R.id.simple_textview);
		question.setText("�Desea realmente adoptar a " + dog.mName + "?");
		
		return view;	
		
	}

}
