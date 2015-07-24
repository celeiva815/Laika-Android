package social.laika.app.listeners;

import social.laika.app.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AcceptAssistanceToEventOnClickListener implements OnClickListener {
	
	private ImageView acceptImageView;
	private ImageView refuseImageView;

	public AcceptAssistanceToEventOnClickListener(ImageView acceptImageView,
			ImageView refuseImageView) {
	
		this.acceptImageView = acceptImageView;
		this.refuseImageView = refuseImageView;
	}

	@Override
	public void onClick(View v) {
		
		acceptImageView.setImageResource(R.drawable.lk_event_accept);
		refuseImageView.setImageResource(R.drawable.lk_assistance_cross);
		
	}

}
