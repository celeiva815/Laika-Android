package cl.laikachile.laika.listeners;

import cl.laikachile.laika.activities.AdoptDogFragmentActivity;
import cl.laikachile.laika.utils.Do;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

public class SearchDogsToAdoptOnClickListener implements OnClickListener {

	@Override
	public void onClick(View v) {

		//TODO implementar la llamada a la API
		
		final Context context = v.getContext();
		final ProgressDialog progressDialog = ProgressDialog.show(v.getContext(), "Adopci�n", "Buscando mascotas que se adec�en a tu perfil...");
		
		new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                
            	progressDialog.dismiss();
            	Do.changeActivity(context, AdoptDogFragmentActivity.class);
            	
            }
        }, 3000);
		
		
	}

}
