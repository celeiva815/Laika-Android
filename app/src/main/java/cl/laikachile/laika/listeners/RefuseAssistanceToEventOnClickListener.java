package cl.laikachile.laika.listeners;

import cl.laikachile.laika.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class RefuseAssistanceToEventOnClickListener implements OnClickListener {
	
	private ImageView acceptImageView;
	private ImageView refuseImageView;

	public RefuseAssistanceToEventOnClickListener(ImageView acceptImageView,
			ImageView refuseImageView) {
	
		this.acceptImageView = acceptImageView;
		this.refuseImageView = refuseImageView;
	}

	@Override
	public void onClick(View v) {
		
		acceptImageView.setImageResource(R.drawable.lk_assistance_ticket);
		refuseImageView.setImageResource(R.drawable.lk_event_refuse);
		
	}

}
